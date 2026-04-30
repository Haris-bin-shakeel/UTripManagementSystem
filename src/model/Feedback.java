package model;

public class Feedback {
    private String feedbackId;
    private String tripId;
    private String userId;
    private int rating;
    private String comment;
    private String date;

    public Feedback(String feedbackId, String tripId, String userId, int rating, String comment, String date) {
        this.feedbackId = feedbackId;
        this.tripId = tripId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public void submit() {
        System.out.println("Feedback submitted for trip " + tripId + " by user " + userId);
    }

    // Getters and Setters
    public String getFeedbackId() { return feedbackId; }
    public void setFeedbackId(String feedbackId) { this.feedbackId = feedbackId; }

    public String getTripId() { return tripId; }
    public void setTripId(String tripId) { this.tripId = tripId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
