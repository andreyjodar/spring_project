package com.github.andreyjodar.backend.models.dtos.response;

import lombok.Data;

@Data
public class SimpleResponseDTO {
    private String response;

    public SimpleResponseDTO(String response) {
        this.response = response;
    }
}
