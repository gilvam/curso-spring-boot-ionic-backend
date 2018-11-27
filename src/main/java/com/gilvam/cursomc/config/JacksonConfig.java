package com.gilvam.cursomc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gilvam.cursomc.domain.PaymentBankSlip;
import com.gilvam.cursomc.domain.PaymentCreditCard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {
    //dicurssão da criação dessa classe:
    // https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-of-interfaceclass-without-hinting-the-pare
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {

            public void configure(ObjectMapper objectMapper) {
                objectMapper.registerSubtypes(PaymentCreditCard.class); //class registrada
                objectMapper.registerSubtypes(PaymentBankSlip.class);   //class registrada
                super.configure(objectMapper);
            }
        };
        return builder;
    }
}