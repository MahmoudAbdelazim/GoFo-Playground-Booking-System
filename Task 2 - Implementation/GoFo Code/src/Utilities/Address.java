package src.Utilities;

import java.util.Scanner;

public class Address {
    private int streetNumber;
    private String streetName;
    private String neighborhood;
    private String city;

    public Address(int streetNumber, String streetName, String neighborhood, String city) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.neighborhood = neighborhood;
        this.city = city;
    }

    @Override

    public String toString() {
        return streetNumber + " " + streetName + ", " + neighborhood + ", " + city;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
