package com.github.andreyjodar.backend.models.dtos.response;

import lombok.Data;

@Data
public class SimpleTextDTO {
    private String response;

    public SimpleTextDTO(String response) {
        this.response = response;
    }
}
