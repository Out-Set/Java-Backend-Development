package com.savan.keycloak.serviceIdentifier;

import java.util.List;

import com.savan.keycloak.channelApis.ChannelApis;
import com.savan.keycloak.tenantMaster.TenantMaster;
import com.savan.keycloak.tenantMstApis.TenantMstApis;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Entity
@DynamicUpdate
@DynamicInsert
@Cacheable
@Getter
@Setter
@Table(name="service_identifier_mst")
public class ServiceIdentifier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String code;
	private String description;
	private String serviceType;
	private Boolean dynamicRoute;
	
	private Boolean genericService;
	private Boolean active;
	
	// For DashBoard
	private Integer perHitPrice;
	
	@OneToMany(mappedBy = "serviceIdentifier", cascade = CascadeType.ALL)
	private List<ChannelApis> channelApis;
	
	@OneToMany(mappedBy = "serviceIdentifier", cascade = CascadeType.ALL)
	private List<TenantMstApis> tenantMstApis;

	@ManyToOne
	@JoinColumn(name = "tenant_mst_id")
	private TenantMaster tenantMaster;

	// ----------- Getter & Setters ------------ //

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Boolean getDynamicRoute() {
		return dynamicRoute;
	}

	public void setDynamicRoute(Boolean dynamicRoute) {
		this.dynamicRoute = dynamicRoute;
	}

	public Boolean getGenericService() {
		return genericService;
	}

	public void setGenericService(Boolean genericService) {
		this.genericService = genericService;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getPerHitPrice() {
		return perHitPrice;
	}

	public void setPerHitPrice(Integer perHitPrice) {
		this.perHitPrice = perHitPrice;
	}

	public List<ChannelApis> getChannelApis() {
		return channelApis;
	}

	public void setChannelApis(List<ChannelApis> channelApis) {
		this.channelApis = channelApis;
	}

	public List<TenantMstApis> getTenantMstApis() {
		return tenantMstApis;
	}

	public void setTenantMstApis(List<TenantMstApis> tenantMstApis) {
		this.tenantMstApis = tenantMstApis;
	}
}
