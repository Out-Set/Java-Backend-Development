package com.savan.keycloak.channelApis;

import com.savan.keycloak.serviceIdentifier.ServiceIdentifier;
import com.savan.keycloak.tenantMaster.TenantMaster;
import com.savan.keycloak.channel.Channel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
public class ChannelApis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceIdentifier serviceIdentifier;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @ManyToOne
    @JoinColumn(name = "tenant_mst_id")
    private TenantMaster tenantMaster;

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

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public TenantMaster getTenantMaster() {
        return tenantMaster;
    }

    public void setTenantMaster(TenantMaster tenantMaster) {
        this.tenantMaster = tenantMaster;
    }

    public int getConsumedHitsOfApi() {
        return consumedHitsOfApi;
    }

    public void setConsumedHitsOfApi(int consumedHitsOfApi) {
        this.consumedHitsOfApi = consumedHitsOfApi;
    }
}



