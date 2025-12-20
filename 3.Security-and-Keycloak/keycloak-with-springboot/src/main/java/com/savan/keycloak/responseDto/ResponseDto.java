package com.savan.keycloak.responseDto;

public class ResponseDto {

    private Object integrationResponse;
    private Object keycloakResponse;

    public ResponseDto(Object integrationResponse, Object keycloakResponse) {
        this.integrationResponse = integrationResponse;
        this.keycloakResponse = keycloakResponse;
    }

    public Object getIntegrationResponse() {
        return integrationResponse;
    }

    public void setIntegrationResponse(Object integrationResponse) {
        this.integrationResponse = integrationResponse;
    }

    public Object getKeycloakResponse() {
        return keycloakResponse;
    }

    public void setKeycloakResponse(Object keycloakResponse) {
        this.keycloakResponse = keycloakResponse;
    }
}
