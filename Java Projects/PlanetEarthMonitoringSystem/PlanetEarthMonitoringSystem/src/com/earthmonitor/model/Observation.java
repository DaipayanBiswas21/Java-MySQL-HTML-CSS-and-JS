package com.earthmonitor.model;

import java.time.LocalDateTime;

public class Observation {
    private int id;
    private String location;
    private Double latitude;
    private Double longitude;
    private Integer aqi;
    private Integer wqi;
    private Double temperature;
    private Double humidity;
    private Double noiseLevel;
    private LocalDateTime observedAt;
    private Integer enteredBy;

    public Observation() {}

    public Observation(int id, String location, Double latitude, Double longitude,
                       Integer aqi, Integer wqi, Double temperature, Double humidity, Double noiseLevel,
                       LocalDateTime observedAt, Integer enteredBy) {
        this.id = id;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.aqi = aqi;
        this.wqi = wqi;
        this.temperature = temperature;
        this.humidity = humidity;
        this.noiseLevel = noiseLevel;
        this.observedAt = observedAt;
        this.enteredBy = enteredBy;
    }

    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public Integer getAqi() { return aqi; }
    public void setAqi(Integer aqi) { this.aqi = aqi; }
    public Integer getWqi() { return wqi; }
    public void setWqi(Integer wqi) { this.wqi = wqi; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }
    public Double getNoiseLevel() { return noiseLevel; }
    public void setNoiseLevel(Double noiseLevel) { this.noiseLevel = noiseLevel; }
    public LocalDateTime getObservedAt() { return observedAt; }
    public void setObservedAt(LocalDateTime observedAt) { this.observedAt = observedAt; }
    public Integer getEnteredBy() { return enteredBy; }
    public void setEnteredBy(Integer enteredBy) { this.enteredBy = enteredBy; }
}
