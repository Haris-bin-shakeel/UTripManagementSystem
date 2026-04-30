package model;

public class Enrollment {
    private String enrollId;
    private String studentId;
    private String tripId;
    private String enrollDate;
    private boolean attendanceMarked;

    public Enrollment(String enrollId, String studentId, String tripId, String enrollDate, boolean attendanceMarked) {
        this.enrollId = enrollId;
        this.studentId = studentId;
        this.tripId = tripId;
        this.enrollDate = enrollDate;
        this.attendanceMarked = attendanceMarked;
    }

    public void confirm() {
        System.out.println("Enrollment " + enrollId + " confirmed.");
    }

    public void cancel() {
        System.out.println("Enrollment " + enrollId + " cancelled.");
    }

    public void markAttendance() {
        this.attendanceMarked = true;
    }

    // Getters and Setters
    public String getEnrollId() { return enrollId; }
    public void setEnrollId(String enrollId) { this.enrollId = enrollId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getTripId() { return tripId; }
    public void setTripId(String tripId) { this.tripId = tripId; }

    public String getEnrollDate() { return enrollDate; }
    public void setEnrollDate(String enrollDate) { this.enrollDate = enrollDate; }

    public boolean isAttendanceMarked() { return attendanceMarked; }
    public void setAttendanceMarked(boolean attendanceMarked) { this.attendanceMarked = attendanceMarked; }
}
