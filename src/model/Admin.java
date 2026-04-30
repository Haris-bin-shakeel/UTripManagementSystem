package model;

import lifecycle.TripStatus;

public class Admin extends User {
    private String adminLevel;

    public Admin(String userId, String name, String email, String password, String adminLevel) {
        super(userId, name, email, password, "Admin");
        this.adminLevel = adminLevel;
    }

    public void approveTrip(TripRequest trip) {
        trip.setStatus(TripStatus.APPROVED);
    }

    public void rejectTrip(TripRequest trip, String remark) {
        trip.setStatus(TripStatus.REJECTED);
        trip.setRejectionRemark(remark);
    }

    public void assignVehicle(TripRequest trip, Vehicle vehicle) {
        trip.setVehicleId(vehicle.getVehicleId());
        vehicle.setAvailable(false);
    }

    public void assignDriver(TripRequest trip, Driver driver) {
        trip.setDriverId(driver.getUserId());
    }

    // Getters and Setters
    public String getAdminLevel() { return adminLevel; }
    public void setAdminLevel(String adminLevel) { this.adminLevel = adminLevel; }
}
