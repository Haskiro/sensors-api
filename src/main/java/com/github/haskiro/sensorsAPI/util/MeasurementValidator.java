package com.github.haskiro.sensorsAPI.util;

import com.github.haskiro.sensorsAPI.dto.MeasurementDTO;
import com.github.haskiro.sensorsAPI.models.Sensor;
import com.github.haskiro.sensorsAPI.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class MeasurementValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;
        Optional<Sensor> existingSensor = sensorsService.getSensorByName(measurementDTO.getSensor().getName());

        if (existingSensor.isEmpty()) {
            errors.rejectValue("sensor", "", "Sensor with this name does not exist");
        }
    }
}
