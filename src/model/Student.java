package model;

import java.util.UUID;

public class Student extends User {
    private String rollNo;
    private String batch;
    private String guardianContact;

    public Student(String userId, String name, String email, String password, String rollNo, String batch, String guardianContact) {
        super(userId, name, email, password, "Student");
        this.rollNo = rollNo;
        this.batch = batch;
        this.guardianContact = guardianContact;
    }

    public Enrollment enrollTrip(TripRequest trip) {
        String enrollId = "ENR-" + UUID.randomUUID().toString().substring(0, 8);
        return new Enrollment(enrollId, this.getUserId(), trip.getTripId(), "2026-04-30", false);
    }

    public Feedback submitFeedback(String tripId, int rating, String comment) {
        String feedbackId = "FB-" + UUID.randomUUID().toString().substring(0, 8);
        return new Feedback(feedbackId, tripId, this.getUserId(), rating, comment, "2026-04-30");
    }

    // Getters and Setters
    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }

    public String getGuardianContact() { return guardianContact; }
    public void setGuardianContact(String guardianContact) { this.guardianContact = guardianContact; }
}
