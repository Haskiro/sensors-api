package com.github.haskiro.sensorsAPI.dto;

import com.github.haskiro.sensorsAPI.models.Sensor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class MeasurementDTO {
    private int id;

    @NotNull(message = "Value must not be null")
    @Min(value = -100, message = "Value must be more than -100")
    @Max(value = 100, message = "Value must be less than 100")
    private Double value;

    @NotNull(message = "Raining must not be null")
    private Boolean raining;

    @NotNull(message = "Sensor name must not be null")
    private SensorDTO sensor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
