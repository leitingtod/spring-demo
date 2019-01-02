package com.example.demo.security;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Set;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class GsonSerializerOAuth2AccessToken implements JsonSerializer<OAuth2AccessToken> {

    @Override
    public JsonElement serialize(
            OAuth2AccessToken src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(OAuth2AccessToken.ACCESS_TOKEN, src.getValue());
        // back compatibility for dashboard
        jsonObject.addProperty("value", src.getValue());

        jsonObject.addProperty(OAuth2AccessToken.TOKEN_TYPE, src.getTokenType());

        OAuth2RefreshToken refreshToken = src.getRefreshToken();
        if (refreshToken != null) {
            jsonObject.addProperty(OAuth2AccessToken.REFRESH_TOKEN, refreshToken.getValue());
        }
        Date expiration = src.getExpiration();
        if (expiration != null) {
            long now = System.currentTimeMillis();
            jsonObject.add(
                    OAuth2AccessToken.EXPIRES_IN,
                    new JsonPrimitive((expiration.getTime() - now) / 1000));
        }

        Set<String> scope = src.getScope();

        if (scope != null && !scope.isEmpty()) {
            StringBuilder scopes = new StringBuilder();
            for (String s : scope) {
                Assert.hasLength(s, "Scopes cannot be null or empty. Got " + scope + "");
                scopes.append(s);
                scopes.append(" ");
            }

            jsonObject.addProperty(
                    OAuth2AccessToken.SCOPE, scopes.substring(0, scopes.length() - 1));
        }

        return jsonObject;
    }
}
