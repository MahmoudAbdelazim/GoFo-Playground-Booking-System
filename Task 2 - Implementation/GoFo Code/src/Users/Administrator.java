package src.Users;

import src.Utilities.Playground;

public class Administrator {
    private String username;
    private String password;

    public Administrator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void activatePlayground(Playground playground) {
        playground.setActivated(true);
    }
}
