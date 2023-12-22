package com.tasktrak.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

@org.springframework.context.annotation.Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }
//    public <S, T> T mapToDto(S source, Class<T> targetClass) {
//        return Objects.isNull(source) ? null : modelMapper().map(source, targetClass);
//    }


}
