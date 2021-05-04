package me.tungexplorer.james_generate_data.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.context.annotation.Bean;

public class WebAdminClientConfig {

    @Autowired
    private ObjectMapper json;

    @Bean
    public RequestInterceptor feignRequestInterceptorDefault() {
        return getRequestInterceptorDefault();
    }

    @Bean
    public Decoder feignDecoderDefault() {
        return new ResponseEntityDecoder(new JacksonDecoder(json));
    }

    @Bean
    public Encoder feignEncoderDefault() {
        return new JacksonEncoder(json);
    }


    protected RequestInterceptor getRequestInterceptorDefault() {
        return template -> {
            template.header("Content-Type", "application/json; charset=utf-8");
            template.header("cache", "false");
        };
    }
}
