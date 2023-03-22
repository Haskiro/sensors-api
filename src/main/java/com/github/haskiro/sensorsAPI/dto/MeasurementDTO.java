package com.github.haskiro.sensorsAPI.dto;

import com.github.haskiro.sensorsAPI.models.Sensor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class MeasurementDTO {
    private int id;

    @NotEmpty(message = "Value must not be empty")
    @Min(value = -100, message = "Value must be more than -100")
    @Max(value = 100, message = "Value must be less than 100")
    private double value;

    @NotEmpty(message = "Raining must not be empty")
    private boolean raining;
    private Sensor sensor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
