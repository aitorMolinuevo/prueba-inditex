package com.inditex.pricing.infrastructure.in.rest.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApiError {
    private Integer status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private String path;
}
