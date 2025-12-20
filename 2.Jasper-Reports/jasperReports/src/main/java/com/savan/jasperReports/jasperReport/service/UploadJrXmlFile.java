package com.savan.jasperReports.jasperReport.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Objects;

@Service
public class UploadJrXmlFile {

    @Value("${jasper.jrXml.upload.directoryName}")
    private String jrXmlDirectoryName;

    // Upload .jrXml file to pre-defined directory
    public String uploadJrXmlFile(MultipartFile file) {

        Path uploadDirectory = Paths.get(jrXmlDirectoryName);
        try {
            // Create the directory if it doesn't exist
            Files.createDirectories(uploadDirectory);

            // Resolve the file path and write the file
            Path filePath = uploadDirectory.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.write(filePath, file.getBytes());

            return "File Upload successfully at: "+jrXmlDirectoryName;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "An error occurred while saving at: "+jrXmlDirectoryName;
        }
    }

}
