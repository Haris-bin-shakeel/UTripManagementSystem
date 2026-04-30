package model;

public class Budget {
    private String budgetId;
    private String tripId;
    private double estimated;
    private double approved;
    private double actual;

    public Budget(String budgetId, String tripId, double estimated, double approved, double actual) {
        this.budgetId = budgetId;
        this.tripId = tripId;
        this.estimated = estimated;
        this.approved = approved;
        this.actual = actual;
    }

    public void requestFunds(double amount) {
        this.estimated = amount;
    }

    public void recordExpense(double amount) {
        this.actual += amount;
    }

    public String audit() {
        return "Budget Summary: Estimated=" + estimated + ", Approved=" + approved + ", Actual=" + actual;
    }

    // Getters and Setters
    public String getBudgetId() { return budgetId; }
    public void setBudgetId(String budgetId) { this.budgetId = budgetId; }

    public String getTripId() { return tripId; }
    public void setTripId(String tripId) { this.tripId = tripId; }

    public double getEstimated() { return estimated; }
    public void setEstimated(double estimated) { this.estimated = estimated; }

    public double getApproved() { return approved; }
    public void setApproved(double approved) { this.approved = approved; }

    public double getActual() { return actual; }
    public void setActual(double actual) { this.actual = actual; }
}
