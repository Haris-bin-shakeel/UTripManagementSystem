package controller;

import model.*;
import util.DataStore;
import lifecycle.TripStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TripController {
    public TripRequest createTrip(Teacher teacher, String destination, String purpose, String date, String time, int count, double budget) {
        if (destination == null || destination.trim().isEmpty() || purpose == null || purpose.trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error: Destination and Purpose are required!", "Creation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (count <= 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error: Participant count must be greater than zero!", "Creation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (budget <= 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error: Budget estimate must be a positive number!", "Creation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return null;
        }

        String tripId = "TR-" + UUID.randomUUID().toString().substring(0, 8);
        TripRequest trip = teacher.createTripRequest(tripId, destination, purpose, date, time, count, budget);
        trip.submit(); // Sets status to PENDING
        DataStore.trips.add(trip);

        // Notify Admins
        for (User user : DataStore.users) {
            if (user instanceof Admin) {
                Notification notif = new Notification(UUID.randomUUID().toString().substring(0, 8), user.getUserId(), "New trip request: " + tripId, "TRIP_REQUEST", "2026-04-30", false);
                DataStore.notifications.add(notif);
                notif.send();
            }
        }
        return trip;
    }

    public boolean approveTrip(Admin admin, String tripId, String remark) {
        TripRequest trip = getTripById(tripId);
        if (trip != null) {
            admin.approveTrip(trip);
            // Notify Teacher
            Notification notif = new Notification(UUID.randomUUID().toString().substring(0, 8), trip.getTeacherId(), "Your trip " + tripId + " has been approved.", "APPROVAL", "2026-04-30", false);
            DataStore.notifications.add(notif);
            notif.send();
            return true;
        }
        return false;
    }

    public boolean rejectTrip(Admin admin, String tripId, String remark) {
        TripRequest trip = getTripById(tripId);
        if (trip != null) {
            admin.rejectTrip(trip, remark);
            // Notify Teacher
            Notification notif = new Notification(UUID.randomUUID().toString().substring(0, 8), trip.getTeacherId(), "Your trip " + tripId + " was rejected: " + remark, "REJECTION", "2026-04-30", false);
            DataStore.notifications.add(notif);
            notif.send();
            return true;
        }
        return false;
    }

    public boolean cancelTrip(String tripId, String reason) {
        TripRequest trip = getTripById(tripId);
        if (trip != null) {
            trip.cancel(reason);
            // Notify Enrolled Students
            for (Enrollment enr : DataStore.enrollments) {
                if (enr.getTripId().equals(tripId)) {
                    Notification notif = new Notification(UUID.randomUUID().toString().substring(0, 8), enr.getStudentId(), "Trip " + tripId + " has been cancelled.", "CANCELLATION", "2026-04-30", false);
                    DataStore.notifications.add(notif);
                    notif.send();
                }
            }
            return true;
        }
        return false;
    }

    public List<TripRequest> getAllTrips() {
        return DataStore.trips;
    }

    public List<TripRequest> getTripsByStatus(TripStatus status) {
        List<TripRequest> filtered = new ArrayList<>();
        for (TripRequest t : DataStore.trips) {
            if (t.getStatus() == status) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    public TripRequest getTripById(String tripId) {
        for (TripRequest t : DataStore.trips) {
            if (t.getTripId().equals(tripId)) {
                return t;
            }
        }
        return null;
    }
}
