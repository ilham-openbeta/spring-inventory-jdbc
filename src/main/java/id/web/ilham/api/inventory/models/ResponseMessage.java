package id.web.ilham.api.inventory.models;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public class ResponseMessage<T> {

    private int code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    private ResponseMessage(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ResponseMessage<T> success(T data) {
        return new ResponseMessage<>(HttpStatus.OK.value(), "success", data);
    }

    public static <T> ResponseMessage<T> error(int code, String message, T data) {
        return new ResponseMessage<>(code, message, data);
    }

    public static ResponseMessage<Object> error(int code, String message) {
        return error(code, message, null);
    }

    public String toJson(){
        return new StringJoiner(", ", "{", "}")
                .add("\"code\": " + code)
                .add("\"message\": \"" + message + "\"")
                .add("\"data\": \"" + data + "\"")
                .add("\"timestamp\": \"" + timestamp + "\"").toString();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
