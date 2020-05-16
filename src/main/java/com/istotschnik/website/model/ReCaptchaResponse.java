package com.istotschnik.website.model;

import lombok.Data;

@Data
public class ReCaptchaResponse {
    private boolean success;
    private String challenge;
    private String hostName;
}
