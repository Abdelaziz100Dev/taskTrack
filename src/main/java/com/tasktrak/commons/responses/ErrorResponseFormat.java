package com.tasktrak.commons.responses;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Component
public class ErrorResponseFormat {

    private  LocalDateTime timestamp;
    private  String message;
    private  String path;
    private  Map<String, List<String>> details;

}