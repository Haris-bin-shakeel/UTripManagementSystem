package model;

import lifecycle.TripStatus;

public class Teacher extends User {
    private String department;
    private String employeeId;

    public Teacher(String userId, String name, String email, String password, String department, String employeeId) {
        super(userId, name, email, password, "Teacher");
        this.department = department;
        this.employeeId = employeeId;
    }

    public TripRequest createTripRequest(String tripId, String destination, String purpose, String date, String time, int estimatedCount, double budgetEstimate) {
        return new TripRequest(tripId, destination, purpose, date, time, estimatedCount, TripStatus.CREATED, budgetEstimate, this.getUserId());
    }

    public void submitExpense(String tripId, double amount) {
        System.out.println("Expense of " + amount + " submitted for trip " + tripId);
    }

    // Getters and Setters
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
}
