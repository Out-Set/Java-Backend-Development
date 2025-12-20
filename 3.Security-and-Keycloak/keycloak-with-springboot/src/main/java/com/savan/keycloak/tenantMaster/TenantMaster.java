package com.savan.keycloak.tenantMaster;

import com.savan.keycloak.channelApis.ChannelApis;
import com.savan.keycloak.serviceIdentifier.ServiceIdentifier;
import com.savan.keycloak.tenantMstApis.TenantMstApis;
import com.savan.keycloak.channel.Channel;
import jakarta.persistence.*;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ToString
@Entity
@DynamicUpdate
@DynamicInsert
@Cacheable
@Getter
@Setter
@Table(name="tenant_mst")
public class TenantMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String code;
	private String description;
	
	// For DashBoard
	@OneToMany(mappedBy = "tenantMaster", cascade = CascadeType.ALL)
	private List<Channel> channels;
	
	@OneToMany(mappedBy = "tenantMaster", cascade = CascadeType.ALL)
	private List<TenantMstApis> tenantMstApis;

	@OneToMany(mappedBy = "tenantMaster", cascade = CascadeType.ALL)
	private List<ChannelApis> channelApis;

	@OneToMany(mappedBy = "tenantMaster", cascade = CascadeType.ALL)
	private List<ServiceIdentifier> serviceIdentifiers;

	// ----------- Getter & Setters ------------ //

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

	public List<TenantMstApis> getTenantMstApis() {
		return tenantMstApis;
	}

	public void setTenantMstApis(List<TenantMstApis> tenantMstApis) {
		this.tenantMstApis = tenantMstApis;
	}

	public List<ChannelApis> getChannelApis() {
		return channelApis;
	}

	public void setChannelApis(List<ChannelApis> channelApis) {
		this.channelApis = channelApis;
	}

	public List<ServiceIdentifier> getServiceIdentifiers() {
		return serviceIdentifiers;
	}

	public void setServiceIdentifiers(List<ServiceIdentifier> serviceIdentifiers) {
		this.serviceIdentifiers = serviceIdentifiers;
	}
}
