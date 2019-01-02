package com.example.demo.security.config;

import com.example.demo.security.GsonSerializerOAuth2AccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

@Configuration
public class SecurityConfigurationGson {

    @Bean
    @Scope("prototype")
    Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(OAuth2AccessToken.class, new GsonSerializerOAuth2AccessToken())
                .create();
    }
}
