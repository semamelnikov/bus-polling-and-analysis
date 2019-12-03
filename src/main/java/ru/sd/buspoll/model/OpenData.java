package ru.sd.buspoll.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenData {
    private Bus data;
    @JsonProperty(value = "updated_at")
    private String updatedAt;

    public OpenData() {
    }

    public OpenData(Bus data, String updatedAt) {
        this.data = data;
        this.updatedAt = updatedAt;
    }

    public Bus getData() {
        return data;
    }

    public void setData(Bus data) {
        this.data = data;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
