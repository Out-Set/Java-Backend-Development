package com.savan.keycloak.tenantMstApis;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.savan.keycloak.channel.Channel;
import com.savan.keycloak.serviceIdentifier.ServiceIdentifier;
import com.savan.keycloak.tenantMaster.TenantMaster;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TenantMstApis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceIdentifier serviceIdentifier;

    @ManyToOne
    @JoinColumn(name = "tenant_mst_id")
    private TenantMaster tenantMaster;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    private int remainingHitsOfApi;
    private int consumedHitsOfApi;

    // ----------- Getter & Setters ------------ //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceIdentifier getServiceIdentifier() {
        return serviceIdentifier;
    }

    public void setServiceIdentifier(ServiceIdentifier serviceIdentifier) {
        this.serviceIdentifier = serviceIdentifier;
    }

    public TenantMaster getTenantMaster() {
        return tenantMaster;
    }

    public void setTenantMaster(TenantMaster tenantMaster) {
        this.tenantMaster = tenantMaster;
    }

    public int getRemainingHitsOfApi() {
        return remainingHitsOfApi;
    }

    public void setRemainingHitsOfApi(int remainingHitsOfApi) {
        this.remainingHitsOfApi = remainingHitsOfApi;
    }

    public int getConsumedHitsOfApi() {
        return consumedHitsOfApi;
    }

    public void setConsumedHitsOfApi(int consumedHitsOfApi) {
        this.consumedHitsOfApi = consumedHitsOfApi;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
