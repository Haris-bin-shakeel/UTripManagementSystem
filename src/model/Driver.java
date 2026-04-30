package model;

public class Driver extends User {
    private String licenseNo;
    private String licenseExpiry;

    public Driver(String userId, String name, String email, String password, String licenseNo, String licenseExpiry) {
        super(userId, name, email, password, "Driver");
        this.licenseNo = licenseNo;
        this.licenseExpiry = licenseExpiry;
    }

    public void reportIssue(Vehicle vehicle, String issue) {
        vehicle.setMaintenanceNote(issue);
        vehicle.setAvailable(false);
    }

    public void viewSchedule() {
        System.out.println("Viewing schedule for driver: " + getName());
    }

    // Getters and Setters
    public String getLicenseNo() { return licenseNo; }
    public void setLicenseNo(String licenseNo) { this.licenseNo = licenseNo; }

    public String getLicenseExpiry() { return licenseExpiry; }
    public void setLicenseExpiry(String licenseExpiry) { this.licenseExpiry = licenseExpiry; }
}
