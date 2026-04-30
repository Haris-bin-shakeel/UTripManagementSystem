package model;

public class Vehicle {
    private String vehicleId;
    private String regNo;
    private String type;
    private int capacity;
    private boolean available;
    private String maintenanceNote;

    public Vehicle(String vehicleId, String regNo, String type, int capacity, boolean available) {
        this.vehicleId = vehicleId;
        this.regNo = regNo;
        this.type = type;
        this.capacity = capacity;
        this.available = available;
    }

    public void flagForMaintenance(String note) {
        this.maintenanceNote = note;
        this.available = false;
    }

    public boolean isAvailable() {
        return available;
    }

    // Getters and Setters
    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public void setAvailable(boolean available) { this.available = available; }

    public String getMaintenanceNote() { return maintenanceNote; }
    public void setMaintenanceNote(String maintenanceNote) { this.maintenanceNote = maintenanceNote; }
}
