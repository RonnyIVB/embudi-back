package com.satgy.embudi.dto;

public class AuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer ";
    private String authorization;
    private String uuid;

    public AuthResponseDto(String accessToken, String userId) {
        this.accessToken = accessToken;
        this.authorization = tokenType + accessToken;
        this.uuid = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
