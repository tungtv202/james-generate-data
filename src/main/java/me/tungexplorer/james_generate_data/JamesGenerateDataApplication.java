package me.tungexplorer.james_generate_data;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.HashSet;

@SpringBootApplication
@EnableFeignClients
public class JamesGenerateDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(JamesGenerateDataApplication.class, args);
    }


    @Bean(name = {"json"})
    @Primary
    public ObjectMapper main() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new StdDateFormat());
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new StringDeserializer());
        SimpleFilterProvider filters = new SimpleFilterProvider();
        filters.addFilter("empty", SimpleBeanPropertyFilter.serializeAllExcept(new HashSet<>()));
        filters.addFilter("field", SimpleBeanPropertyFilter.serializeAllExcept(new HashSet<>()));
        objectMapper.setFilterProvider(filters);
        objectMapper.registerModule(module);
        return objectMapper;
    }

}
