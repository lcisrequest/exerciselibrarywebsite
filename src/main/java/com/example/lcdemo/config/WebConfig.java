
package com.example.lcdemo.config;

import com.example.lcdemo.config.properties.HiguProperties;
import com.example.lcdemo.config.properties.RestProperties;
import com.example.lcdemo.modular.auth.filter.AuthFilter;
import com.example.lcdemo.modular.auth.security.DataSecurityAction;
import com.example.lcdemo.modular.auth.security.impl.Base64SecurityAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * web配置
 *
 */

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    HiguProperties higuProperties;

    @Bean
    @ConditionalOnProperty(prefix = RestProperties.REST_PREFIX, name = "auth-open", havingValue = "true", matchIfMissing = true)
//    @Order(Integer.)
    public AuthFilter jwtAuthenticationTokenFilter() {
        return new AuthFilter();
    }

    @Bean
    public DataSecurityAction dataSecurityAction() {
        return new Base64SecurityAction();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(higuProperties.getImgUrlPath()+"/**").addResourceLocations("file:" + higuProperties.getFileUploadPath());
        super.addResourceHandlers(registry);
    }
}

