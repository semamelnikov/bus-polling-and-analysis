package ru.sd.buspoll.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.sd.busanalysis.model.Coordinates;

public class Bus {
    @JsonProperty("GaragNumb")
    private String garagNumb;
    @JsonProperty("Marsh")
    private String marsh;
    @JsonProperty("Graph")
    private String graph;
    @JsonProperty("Smena")
    private String smena;
    @JsonProperty("TimeNav")
    private String timeNav;
    @JsonProperty("Latitude")
    private String latitude;
    @JsonProperty("Longitude")
    private String longitude;
    @JsonProperty("Speed")
    private String speed;
    @JsonProperty("Azimuth")
    private String azimuth;

    public Bus() {
    }

    public Bus(String garagNumb, String marsh, String graph, String smena, String timeNav, String latitude, String longitude, String speed, String azimuth) {
        this.garagNumb = garagNumb;
        this.marsh = marsh;
        this.graph = graph;
        this.smena = smena;
        this.timeNav = timeNav;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.azimuth = azimuth;
    }

    public String getGaragNumb() {
        return garagNumb;
    }

    public void setGaragNumb(String garagNumb) {
        this.garagNumb = garagNumb;
    }

    public String getMarsh() {
        return marsh;
    }

    public void setMarsh(String marsh) {
        this.marsh = marsh;
    }

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public String getSmena() {
        return smena;
    }

    public void setSmena(String smena) {
        this.smena = smena;
    }

    public String getTimeNav() {
        return timeNav;
    }

    public void setTimeNav(String timeNav) {
        this.timeNav = timeNav;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(String azimuth) {
        this.azimuth = azimuth;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "garagNumb='" + garagNumb + '\'' +
                ", marsh='" + marsh + '\'' +
                ", graph='" + graph + '\'' +
                ", smena='" + smena + '\'' +
                ", timeNav='" + timeNav + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", speed='" + speed + '\'' +
                ", azimuth='" + azimuth + '\'' +
                '}';
    }

    public Coordinates getCoordinates() {
        return new Coordinates(getLatitude(), getLongitude());
    }
}
