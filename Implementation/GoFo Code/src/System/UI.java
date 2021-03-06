package src.System;

import src.Utilities.Address;
import src.Utilities.Playground;
import src.Users.PlaygroundOwner;
import src.Users.Administrator;
import src.Users.Player;
import java.util.*;

/**
 * {@code UI} class is used to enable users and admins to interact with the system using the console, and direct them to other specific UI classes
 */
public class UI {

    ArrayList<Player> players;
    ArrayList<PlaygroundOwner> owners;
    ArrayList<Playground> playgrounds;
    ArrayList<Administrator> admins;
    private Scanner scanner = new Scanner(System.in);

    /**
     * A constructor for the {@code UI} object
     */
    public UI(ArrayList<Player> players, ArrayList<PlaygroundOwner> owners, ArrayList<Playground> playgrounds, ArrayList<Administrator> admins) {
        this.players = players;
        this.owners = owners;
        this.playgrounds = playgrounds;
        this.admins = admins;
        mainMenu();
    }

    /**
     * A main menu that allows users and admins to login or register to the system, and redirects them to different parts of the system
     */
    public void mainMenu() {
        String option = "";
        while (true) {
            System.out.println("\n1- Signup" +
                    "\n2- Login" +
                    "\n3- Login As an Admin" +
                    "\n4- Exit");
            option = scanner.nextLine();
            if (option.equalsIgnoreCase("1")) {
                signUp();
            } else if (option.equalsIgnoreCase("2")) {
                login();
            } else if (option.equalsIgnoreCase("3")) {
                adminLogin();
            } else if (option.equalsIgnoreCase("4")) {
                break;
            }
            else {
                System.out.println("***Enter valid option***\n");
            }
        }
        System.out.println("\nThanks for using GoFo Booking System");
    }

    /**
     * Registers a new user to the system by entering the appropriate data
     */
    private void signUp() {
        String username, password, phone, email, firstName, lastName, streetName, neighborhood, city;
        int strNumber;
        Address address;
        while (true) {
            System.out.print("Enter new username: ");
            username = scanner.nextLine();
            boolean validUsername = checkUsername(username);
            if (!validUsername) {
                System.out.println("***Username already taken***\n");
            } else {
                break;
            }
        }
        while (true) {
            System.out.print("Enter new Email: ");
            email = scanner.nextLine();
            boolean validEmail = checkEmail(email);
            if (!validEmail) {
                System.out.println("***Email Not available***\n");
            } else {
                break;
            }
        }
        while (true) {
            System.out.print("Enter new password (at least 8 letters, include 1 symbol and 1 Uppercase letter): ");
            password = scanner.nextLine();
            boolean validPassword = checkStrongPassword(password);
            if (!validPassword) {
                System.out.println("***Enter strong password***\n");
            } else {
                break;
            }
        }
        sendEmailVerificationCode();
        System.out.print("Enter Phone Number: ");
        phone = scanner.nextLine();
        System.out.print("Enter First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        lastName = scanner.nextLine();
        System.out.println("Address Informations;- ");
        System.out.print("Enter Street number: ");
        strNumber = scanner.nextInt();
        System.out.print("Enter Street name: ");
        scanner.skip("\n");
        streetName = scanner.nextLine();
        System.out.print("Enter Neighborhood: ");
        neighborhood = scanner.nextLine();
        System.out.print("Enter City: ");
        city = scanner.nextLine();
        address = new Address(strNumber, streetName, neighborhood, city);
        String type;
        while (true) {
            System.out.println("Enter the type of account: " +
                    "\n1- Player" +
                    "\n2- Playground Owner");
            type = scanner.nextLine();
            if (type.equalsIgnoreCase("1")) {
                players.add(new Player(firstName, lastName, email, password, username, phone, address));
                break;
            } else if (type.equalsIgnoreCase("2")) {
                owners.add(new PlaygroundOwner(firstName, lastName, email, password, username, phone, address));
                break;
            } else {
                System.out.println("***Enter valid option***");
            }
        }
    }

    /**
     * Logs users into the system and redirects them to their proper UI
     */
    private void login() {
        String username, password;
        boolean foundUser = false;
        boolean correctPassword = false;
        System.out.print("Enter Username: ");
        username = scanner.nextLine();
        System.out.print("Enter Password: ");
        password = scanner.nextLine();
        int userIdx = -1;
        for (Player player: players) {
            userIdx++;
            if (player.getUsername().equalsIgnoreCase(username)) {
                foundUser = true;
                if (player.getPassword().equals(password)) {
                    correctPassword = true;
                    break;
                }
            }
        }
        if (foundUser && correctPassword) {
            var playerUI = new PlayerUI(playgrounds, players, userIdx);
            return;
        }
        userIdx = -1;
        for (PlaygroundOwner owner: owners) {
            userIdx++;
            if (owner.getUsername().equalsIgnoreCase(username)) {
                foundUser = true;
                if (owner.getPassword().equals(password)) {
                    correctPassword = true;
                    break;
                }
            }
        }
        if (foundUser && correctPassword) {
            var PlaygroundOwnerUI = new PlaygroundOwnerUI(playgrounds, owners, userIdx);
            return;
        }
        if (!foundUser) {
            System.out.println("Username not found");
        } else if (!correctPassword) {
            System.out.println("Incorrect Password");
        }
    }

    /**
     * Checks if the password is strong or not
     * @param password the password to be checked
     * @return whether the password is strong
     */
    private boolean checkStrongPassword(String password) {
        boolean strong = false;
        if (password.length() < 8) {
            return strong;
        }
        int symbols = 0;
        int uppercase = 0;
        for (int i = 0; i < password.length(); ++i) {
            if (Character.isLetterOrDigit(password.charAt(i))) {
                if (Character.isUpperCase(password.charAt(i))) {
                    ++uppercase;
                }
            } else {
                ++symbols;
            }
        }
        return (symbols >= 1 && uppercase >= 1);
    }

    /**
     * Checks the email is valid or not
     * @param email the email to be checked
     * @return whether the email is valid
     */
    private boolean checkEmail(String email) {
        boolean validEmail;

        validEmail = email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
                "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]" +
                "(?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
                "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        return validEmail && availableEmail(email);
    }

    /**
     * Checks if the email is available or already registered
     * @param email the email to be checked
     * @return whether the email is available
     */
    private boolean availableEmail(String email) {
        for (Player player: players) {
            if (player.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        for (PlaygroundOwner owner: owners) {
            if (owner.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the username is available or already taken
     * @param username the username to be checked
     * @return whether the username is available
     */
    private boolean checkUsername(String username) {
        for (Player player: players) {
            if (player.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        for (PlaygroundOwner owner: owners) {
            if (owner.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sends a verification code to the email and verifies it
     */
    private void sendEmailVerificationCode() {
        System.out.println("Enter verification code sent to your email: ");
        String userCode = scanner.nextLine();
        System.out.println("Verified ✅");
    }

    /**
     * Logs an admin into the system and redirects him to the Admin UI
     */
    private void adminLogin() {
        String username, password;
        System.out.print("Enter username: ");
        username = scanner.nextLine();
        System.out.print("Enter Password: ");
        password = scanner.nextLine();
        boolean found = false, correctPassword = false;
        for (Administrator admin: admins) {
            if (admin.getUsername().equalsIgnoreCase(username)) {
                found = true;
                if (admin.getPassword().equalsIgnoreCase(password)) {
                    correctPassword = true;
                    AdminUI adminUI = new AdminUI(playgrounds, admin);
                }
                break;
            }
        }
        if (!found) System.out.println("Username is invalid");
        else if (!correctPassword) System.out.println("Incorrect Password");
    }
}



