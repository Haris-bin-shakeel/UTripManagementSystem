package util;

import model.*;
import lifecycle.TripStatus;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static List<User> users = new ArrayList<>();
    public static List<TripRequest> trips = new ArrayList<>();
    public static List<Vehicle> vehicles = new ArrayList<>();
    public static List<Enrollment> enrollments = new ArrayList<>();
    public static List<Notification> notifications = new ArrayList<>();
    public static List<Feedback> feedbackList = new ArrayList<>();

    public static void initSampleData() {
        // 1 Admin
        users.add(new Admin("A001", "Main Admin", "admin@utms.edu", "admin123", "SuperAdmin"));

        // 1 Teacher
        users.add(new Teacher("T001", "Dr. Smith", "smith@utms.edu", "teacher123", "Computer Science", "EMP001"));

        // 2 Students
        users.add(new Student("S001", "Muhammad Haris", "haris@utms.edu", "student123", "24P-0638", "BSCS-4D", "0300-1234567"));
        users.add(new Student("S002", "Arham Ali", "arham@utms.edu", "student123", "24P-0748", "BSCS-4D", "0311-7654321"));

        // 1 Driver
        users.add(new Driver("D001", "John Doe", "john@utms.edu", "driver123", "LIC-998877", "2030-12-31"));

        // 2 Vehicles
        vehicles.add(new Vehicle("V001", "ABC-123", "Bus", 40, true));
        vehicles.add(new Vehicle("V002", "XYZ-789", "Hiace", 15, true));

        // 1 sample TripRequest with status PENDING
        TripRequest sampleTrip = new TripRequest("TR001", "Northern Areas", "Educational Trip", "2026-06-15", "08:00 AM", 25, TripStatus.PENDING, 50000.0, "T001");
        trips.add(sampleTrip);
    }
}
