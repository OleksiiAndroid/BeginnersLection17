package ua.com.webacademy.beginnerslection17;

public class Message {
    private String message;
    private long time;

    public Message() {
    }

    public Message(String message, long time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
