package model;

public class Notification {
    private String notifId;
    private String userId;
    private String message;
    private String type;
    private String timestamp;
    private boolean isRead;

    public Notification(String notifId, String userId, String message, String type, String timestamp, boolean isRead) {
        this.notifId = notifId;
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    public void send() {
        System.out.println("Notification sent to " + userId + ": " + message);
    }

    public void markRead() {
        this.isRead = true;
    }

    // Getters and Setters
    public String getNotifId() { return notifId; }
    public void setNotifId(String notifId) { this.notifId = notifId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
}
