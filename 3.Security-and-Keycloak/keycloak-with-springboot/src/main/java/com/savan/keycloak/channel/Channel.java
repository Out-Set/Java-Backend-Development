package com.savan.keycloak.channel;

import com.savan.keycloak.channelApis.ChannelApis;
import com.savan.keycloak.tenantMaster.TenantMaster;
import com.savan.keycloak.tenantMstApis.TenantMstApis;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="channel")
public class Channel {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @NotBlank(message = "Username is required")
    private String username;
    
    @Email(message = "Invalid email address")
    private String email;
    
    private String isEmailVerified;
    
    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Scope is required")
    private String scope;
    
    @ManyToOne
    @JoinColumn(name = "tenant_mst_id")
    private TenantMaster tenantMaster;
    
    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
	private List<ChannelApis> channelApis;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<TenantMstApis> tenantMstApis;

    // ----------- Getter & Setters ------------ //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank(message = "Username is required") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username is required") String username) {
        this.username = username;
    }

    public @Email(message = "Invalid email address") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid email address") String email) {
        this.email = email;
    }

    public String getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(String isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public @NotBlank(message = "Password is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Scope is required") String getScope() {
        return scope;
    }

    public void setScope(@NotBlank(message = "Scope is required") String scope) {
        this.scope = scope;
    }

    public TenantMaster getTenantMaster() {
        return tenantMaster;
    }

    public void setTenantMaster(TenantMaster tenantMaster) {
        this.tenantMaster = tenantMaster;
    }

    public List<ChannelApis> getChannelApis() {
        return channelApis;
    }

    public void setChannelApis(List<ChannelApis> channelApis) {
        this.channelApis = channelApis;
    }
}

