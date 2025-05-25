package com.example.tubes.utils;
import java.io.Serializable;

public class ApiResponse<T> implements Serializable {
    private int status;
    private String message;
    private String error;
    private T data;

    public ApiResponse() {}

    public ApiResponse(int status, String message, String error, T data) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(200, message, null, data);
    }

    public static <T> ApiResponse<T> success(T data, String message, int status) {
        return new ApiResponse<>(status, message, null, data);
    }

    public static <T> ApiResponse<T> error(String errorMessage, int status) {
        return new ApiResponse<>(status, "Failed", errorMessage, null);
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
