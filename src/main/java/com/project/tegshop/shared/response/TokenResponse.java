package com.project.tegshop.shared.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class TokenResponse {
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("access_token")
    private String accessToken;

    public TokenResponse(String accessToken) {
        this.tokenType = "Bearer";
        this.accessToken = accessToken;
    }
}
