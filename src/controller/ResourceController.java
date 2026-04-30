package controller;

import model.*;
import util.DataStore;
import lifecycle.TripStatus;
import java.util.ArrayList;
import java.util.List;

public class ResourceController {
    public boolean assignVehicle(Admin admin, String tripId, String vehicleId) {
        TripRequest trip = null;
        for (TripRequest t : DataStore.trips) {
            if (t.getTripId().equals(tripId)) {
                trip = t;
                break;
            }
        }

        Vehicle vehicle = null;
        for (Vehicle v : DataStore.vehicles) {
            if (v.getVehicleId().equals(vehicleId)) {
                vehicle = v;
                break;
            }
        }

        if (trip == null) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error: Trip not found!", "Assignment Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (vehicle == null) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error: Vehicle not found!", "Assignment Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!vehicle.isAvailable()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error: Vehicle " + vehicleId + " is currently unavailable!", "Resource Conflict", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }

        admin.assignVehicle(trip, vehicle);
        return true;
    }

    public boolean assignDriver(Admin admin, String tripId, String driverId) {
        TripRequest trip = null;
        for (TripRequest t : DataStore.trips) {
            if (t.getTripId().equals(tripId)) {
                trip = t;
                break;
            }
        }

        Driver driver = null;
        for (User u : DataStore.users) {
            if (u.getUserId().equals(driverId) && u instanceof Driver) {
                driver = (Driver) u;
                break;
            }
        }

        if (trip == null || driver == null) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error: Invalid Trip or Driver selected!", "Assignment Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Simple overlap check: check if driver is already assigned to another trip on the same date
        for (TripRequest t : DataStore.trips) {
            if (driverId.equals(t.getDriverId()) && t.getDate().equals(trip.getDate()) && !t.getTripId().equals(tripId)) {
                javax.swing.JOptionPane.showMessageDialog(null, "Resource Conflict: Driver " + driver.getName() + " is already assigned to another trip on " + trip.getDate(), "Conflict Detected", javax.swing.JOptionPane.WARNING_MESSAGE);
                return false; // Driver busy
            }
        }
        admin.assignDriver(trip, driver);
        trip.setStatus(TripStatus.RESOURCE_ASSIGNED);
        return true;
    }

    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> avail = new ArrayList<>();
        for (Vehicle v : DataStore.vehicles) {
            if (v.isAvailable()) avail.add(v);
        }
        return avail;
    }

    public boolean flagVehicle(String vehicleId, String issue) {
        for (Vehicle v : DataStore.vehicles) {
            if (v.getVehicleId().equals(vehicleId)) {
                v.flagForMaintenance(issue);
                return true;
            }
        }
        return false;
    }
}
