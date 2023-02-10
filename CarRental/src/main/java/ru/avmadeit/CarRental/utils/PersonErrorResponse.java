package ru.avmadeit.CarRental.utils;

public class PersonErrorResponse {

    private String message;
    private long timestamp;

    public PersonErrorResponse(String message, long currentTimeMillis) {
        this.message = message;
        this.timestamp = currentTimeMillis;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
