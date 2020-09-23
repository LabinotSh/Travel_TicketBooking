package com.fiek.travelGuide.config;

import com.paypal.api.payments.FuturePayment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan("com.fiek.travelGuide.service")
public class PayPalConfig {

    @Value("${paypal.client.app}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;

    @Bean
    public Map<String,String> paypalSdkConfig(){
        Map<String,String> configMap = new HashMap<>();
        configMap.put("mode",mode);
        return configMap;
    }

    @Bean
    public OAuthTokenCredential oAuthTokenCredential(){
        return new OAuthTokenCredential(clientId,clientSecret,paypalSdkConfig());
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        @SuppressWarnings("deprecation")
        APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
//        String refreshToken = FuturePayment.fetchRefreshToken(context,oAuthTokenCredential());
        context.setConfigurationMap(paypalSdkConfig());
        return context;
    }
}
