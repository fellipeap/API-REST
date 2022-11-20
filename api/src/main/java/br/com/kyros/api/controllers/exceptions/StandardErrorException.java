package br.com.kyros.api.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StandardErrorException {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;
}
