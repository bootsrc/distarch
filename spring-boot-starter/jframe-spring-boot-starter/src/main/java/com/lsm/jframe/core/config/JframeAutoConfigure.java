package com.lsm.jframe.core.config;

import com.lsm.jframe.core.api.JframeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(JframeService.class)
@EnableConfigurationProperties(JframeServiceProperties.class)
public class JframeAutoConfigure {

    private final JframeServiceProperties properties;

    @Autowired
    public JframeAutoConfigure(JframeServiceProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "jframe.service", value = "enabled",havingValue = "true")
    JframeService jframeService (){
        return  new JframeService(properties.getPrefix(), properties.getSuffix());
    }

}
