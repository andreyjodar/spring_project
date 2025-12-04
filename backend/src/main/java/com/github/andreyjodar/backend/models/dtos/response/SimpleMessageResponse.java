package com.github.andreyjodar.backend.models.dtos.response;

import lombok.Data;

@Data
public class SimpleMessageResponse {
    private Long id;
    private String event;

    public SimpleMessageResponse(Long id, String event) {
        this.id = id;
        this.event = event;
    }
}
