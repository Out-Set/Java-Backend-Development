package com.pk.development.app.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.system.SystemProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertyFilePatternRegisteringListener	implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
	
	public static final String PROPERTY_FILE_PREFIX = "application";
	private static final String FILE_SUFFIX = ".properties";
	private static final String PROPERTY_FILE_PATTERN = "classpath*:product-config/*.properties";
	private static final String EXTERNAL_PROPERTY_FILE = "external.property.file";
	private static final String PROPERTY_SOURCE_RESOURCE_FILE = "applicationProperties";
	private static final String PROPERTY_SOURCE_DATABASE = "databaseProperties";
	private static final StringEncryptor encryptor = new CustomStringEncryptor();

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		/*
		ConfigurableEnvironment environment = event.getEnvironment();
		MutablePropertySources propertySources = environment.getPropertySources();
		Properties properties = new Properties();
		log.debug("Start loading property files...");
		List<Resource> resources = loadResourcesFromClasspathAndJars(PROPERTY_FILE_PATTERN);
		// Sort resources to load JAR resources first
		resources.sort(jarFirstComparator());
		loadExternalProperties(resources);
		// loadProfileProperties(environment);
		// loadPlainProperties(environment);
		loadPropertiesFromResource(resources, properties);	

		MapPropertySource databasePropertySource = new MapPropertySource(PROPERTY_SOURCE_DATABASE, loadPropertiesFromDatabase());
		PropertySource<?> propertiesPropertySource = new PropertiesPropertySource(PROPERTY_SOURCE_RESOURCE_FILE, properties);
		// Add the properties to the environment
		propertySources.addFirst(databasePropertySource);
		propertySources.addLast(propertiesPropertySource);
        */
	}

	private List<Resource> loadResourcesFromClasspathAndJars(String pattern) {
		try {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources(pattern);
			List<Resource> filteredResources = new ArrayList<>();
			for (Resource resource : resources) {
				if (isClasspathResource(resource) || isJarResource(resource)) {
					filteredResources.add(resource);
				}
			}
			return filteredResources;
		} catch (IOException ex) {
			throw new IllegalStateException("Unable to load configuration files", ex);
		}
	}
	
    private void loadExternalProperties(List<Resource> resources) {
		String propertyFilePath = SystemProperties.get(EXTERNAL_PROPERTY_FILE);
        log.debug("External property file: {}", propertyFilePath);
        if (StringUtils.hasText(propertyFilePath)) {
            Resource fileSystemResource = new FileSystemResource(propertyFilePath);
            resources.add(fileSystemResource);
        }
    }

	private void loadPropertiesFromResource(List<Resource> resources, Properties properties) {
		try {
			for (Resource resource : resources) {
				String propertyFileName = resource.getFilename();
				log.debug("Processing resource file from location: {} with file name: {}", resource, propertyFileName);
				Properties resourceProperties = PropertiesLoaderUtils.loadProperties(resource);
				decryptPropertyValues(resourceProperties);
				properties.putAll(resourceProperties);
			}
		} catch (IOException ex) {
			throw new IllegalStateException("Unable to load configuration files", ex);
		}
	}

	private void decryptPropertyValues(Properties properties) {
		// Decrypt the property values using the StringEncryptor

		for (String propertyName : properties.stringPropertyNames()) {
			String propertyValue = properties.getProperty(propertyName);
			if (isEncryptedValue(propertyValue)) {
				try {
					log.debug("Propert_Key: {}, Encrypted value : {}", propertyName, propertyValue);
					String decryptedValue = encryptor.decrypt(getEncryptedValue(propertyValue));
					properties.setProperty(propertyName, decryptedValue);
				} catch (EncryptionOperationNotPossibleException ex) {
					throw new IllegalStateException("Error occurred while decrypting property: " + propertyName, ex);
				}
			}
		}
	}


	private boolean isClasspathResource(Resource resource) throws IOException {
		return ResourceUtils.isFileURL(resource.getURL());
	}

	private boolean isJarResource(Resource resource) throws IOException {
		return ResourceUtils.isJarURL(resource.getURL());
	}
	
	private boolean isEncryptedValue(String value) {
		return value != null && value.startsWith("ENC(") && value.endsWith(")");
	}

	private String getEncryptedValue(String value) {
		return value.substring(4, value.length() - 1);
	}

	private void loadProfileProperties(ConfigurableEnvironment environment) throws IOException {
		String[] activeProfiles = environment.getActiveProfiles();
		if (activeProfiles != null && activeProfiles.length > 0)
			loadProfileProperties(environment, activeProfiles);
		else
			loadProfileProperties(environment, environment.getDefaultProfiles());
	}

	private void loadProfileProperties(ConfigurableEnvironment environment, String[] profiles) throws IOException {
		for (String activeProfile : profiles) {
			addFileToEnvironment(environment, PROPERTY_FILE_PREFIX + "-" + activeProfile + FILE_SUFFIX);
		}
	}

	private void loadPlainProperties(ConfigurableEnvironment environment) throws IOException {
		addFileToEnvironment(environment, PROPERTY_FILE_PREFIX + FILE_SUFFIX);
	}

	private void addFileToEnvironment(ConfigurableEnvironment environment, String file) throws IOException {

		log.debug("Loading file: {}", file);
		ClassPathResource classPathResource = new ClassPathResource(file);
		if (classPathResource.exists()) {
			environment.getPropertySources().addLast(new ResourcePropertySource(classPathResource));
		}
	}
	
	private Comparator<Resource> jarFirstComparator() {
	    return (resource1, resource2) -> {
	      try {
	        boolean isResource1Jar = isJarResource(resource1);
	        boolean isResource2Jar = isJarResource(resource2);

	        // Sort JAR resources before classpath resources
	        if (isResource1Jar && !isResource2Jar) {
	          return -1;
	        } else if (!isResource1Jar &&isResource2Jar) {
	          return 1;
	        }

	        // If both resources are JAR or both are classpath, use default ordering
	        return resource1.getURL().toString().compareTo(resource2.getURL().toString());
	      } catch (IOException ex) {
	        throw new IllegalStateException("Error occurred while comparing JAR resources", ex);
	      }
	    };
	  }
	
	private Map<String, Object> loadPropertiesFromDatabase() {
		log.info("Loading database properties....");
	    Map<String, Object> propertySource = new HashMap<>();
	    String url = SystemProperties.get("spring.datasource.url");
	    String username = SystemProperties.get("spring.datasource.username");
	    String password = SystemProperties.get("spring.datasource.password");
	    String query = "select key, value from system_properties_mst";

	    try (Connection connection = DriverManager.getConnection(url, username, password);
	         PreparedStatement preparedStatement = connection.prepareStatement(query);
	         ResultSet rs = preparedStatement.executeQuery()) {

	        while (rs.next()) {
	            String propName = rs.getString("key");
	            String propValue = rs.getString("value");
	            propertySource.put(propName, propValue);
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException("Error occurred while processing database properties", e);
	    }
	    log.info("Properties loaded from Database successfully.");
	    return propertySource;
	}
}
