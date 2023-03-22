package com.github.haskiro.sensorsAPI.util;

import com.github.haskiro.sensorsAPI.dto.SensorDTO;
import com.github.haskiro.sensorsAPI.models.Sensor;
import com.github.haskiro.sensorsAPI.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class SensorValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;
        Optional<Sensor> existingSensor = sensorsService.getSensorByName(sensorDTO.getName());

        if (existingSensor.isPresent()) {
            errors.rejectValue("name", "", "Sensor with this name already exists");
        }
    }

}