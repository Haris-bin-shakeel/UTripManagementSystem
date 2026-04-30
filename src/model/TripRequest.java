package model;

import lifecycle.TripStatus;

public class TripRequest {
    private String tripId;
    private String destination;
    private String purpose;
    private String date;
    private String time;
    private int estimatedCount;
    private TripStatus status;
    private double budgetEstimate;
    private String teacherId;
    private String vehicleId;
    private String driverId;
    private String rejectionRemark;

    public TripRequest(String tripId, String destination, String purpose, String date, String time, int estimatedCount, TripStatus status, double budgetEstimate, String teacherId) {
        this.tripId = tripId;
        this.destination = destination;
        this.purpose = purpose;
        this.date = date;
        this.time = time;
        this.estimatedCount = estimatedCount;
        this.status = status;
        this.budgetEstimate = budgetEstimate;
        this.teacherId = teacherId;
    }

    public void submit() {
        this.status = TripStatus.PENDING;
    }

    public void cancel(String reason) {
        this.status = TripStatus.CANCELLED;
        this.rejectionRemark = reason;
    }

    public String getDetails() {
        return "Trip [" + tripId + "] to " + destination + " (Status: " + status + ")";
    }

    // Getters and Setters
    public String getTripId() { return tripId; }
    public void setTripId(String tripId) { this.tripId = tripId; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getEstimatedCount() { return estimatedCount; }
    public void setEstimatedCount(int estimatedCount) { this.estimatedCount = estimatedCount; }

    public TripStatus getStatus() { return status; }
    public void setStatus(TripStatus status) { this.status = status; }

    public double getBudgetEstimate() { return budgetEstimate; }
    public void setBudgetEstimate(double budgetEstimate) { this.budgetEstimate = budgetEstimate; }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getRejectionRemark() { return rejectionRemark; }
    public void setRejectionRemark(String rejectionRemark) { this.rejectionRemark = rejectionRemark; }
}
