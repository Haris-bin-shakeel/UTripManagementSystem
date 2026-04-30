package controller;

import model.User;
import util.DataStore;

public class AuthController {
    public User login(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error: Credentials cannot be empty!", "Authentication Failed", javax.swing.JOptionPane.ERROR_MESSAGE);
            return null;
        }
        for (User user : DataStore.users) {
            if (user.login(email, password)) {
                return user;
            }
        }
        return null;
    }

    public void logout(User user) {
        if (user != null) {
            user.logout();
        }
    }

    public User getUserByEmail(String email) {
        for (User user : DataStore.users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }
}
