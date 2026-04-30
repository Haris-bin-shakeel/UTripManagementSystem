package controller;

import model.*;
import util.DataStore;
import lifecycle.TripStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EnrollmentController {
    public Enrollment enrollStudent(Student student, String tripId) {
        TripRequest trip = null;
        for (TripRequest t : DataStore.trips) {
            if (t.getTripId().equals(tripId)) {
                trip = t;
                break;
            }
        }

        if (trip == null) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error: Trip not found!", "Enrollment Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (trip.getStatus() != TripStatus.ENROLLMENT_OPEN && trip.getStatus() != TripStatus.RESOURCE_ASSIGNED) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error: Enrollment is not open for this trip. Current status: " + trip.getStatus(), "Enrollment Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Check seat count
        int count = 0;
        for (Enrollment e : DataStore.enrollments) {
            if (e.getTripId().equals(tripId)) count++;
        }

        if (count >= trip.getEstimatedCount()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error: Trip is full! Max capacity: " + trip.getEstimatedCount(), "Enrollment Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Enrollment enr = student.enrollTrip(trip);
        DataStore.enrollments.add(enr);
        enr.confirm();
        
        Notification notif = new Notification(UUID.randomUUID().toString().substring(0, 8), student.getUserId(), "You enrolled in trip: " + tripId, "ENROLLMENT", "2026-04-30", false);
        DataStore.notifications.add(notif);
        notif.send();
        
        return enr;
    }

    public boolean cancelEnrollment(String enrollId) {
        for (int i = 0; i < DataStore.enrollments.size(); i++) {
            Enrollment e = DataStore.enrollments.get(i);
            if (e.getEnrollId().equals(enrollId)) {
                e.cancel();
                DataStore.enrollments.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Enrollment> getEnrollmentsForTrip(String tripId) {
        List<Enrollment> list = new ArrayList<>();
        for (Enrollment e : DataStore.enrollments) {
            if (e.getTripId().equals(tripId)) list.add(e);
        }
        return list;
    }

    public List<Enrollment> getEnrollmentsForStudent(String studentId) {
        List<Enrollment> list = new ArrayList<>();
        for (Enrollment e : DataStore.enrollments) {
            if (e.getStudentId().equals(studentId)) list.add(e);
        }
        return list;
    }

    public boolean markAttendance(String enrollId) {
        for (Enrollment e : DataStore.enrollments) {
            if (e.getEnrollId().equals(enrollId)) {
                e.markAttendance();
                return true;
            }
        }
        return false;
    }
}
