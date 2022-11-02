package com.pocu.catalog.web.dto;

public abstract class BaseDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseDto{" +
                "id=" + id +
                '}';
    }
}
