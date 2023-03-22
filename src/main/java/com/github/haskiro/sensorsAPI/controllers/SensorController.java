package com.github.haskiro.sensorsAPI.controllers;

import com.github.haskiro.sensorsAPI.dto.SensorDTO;
import com.github.haskiro.sensorsAPI.models.Sensor;
import com.github.haskiro.sensorsAPI.services.SensorsService;
import com.github.haskiro.sensorsAPI.util.SensorErrorResponse;
import com.github.haskiro.sensorsAPI.util.SensorNotCreatedException;
import com.github.haskiro.sensorsAPI.util.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/sensors")
public class SensorController {
    private final SensorsService sensorsService;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorsService sensorsService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registerSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                     BindingResult bindingResult) {
            sensorValidator.validate(sensorDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                List<FieldError> errors = bindingResult.getFieldErrors();
                StringBuilder errorMessage = new StringBuilder();

                errors.forEach(fieldError -> {
                    errorMessage.append(fieldError.getField())
                            .append(" - ").append(fieldError.getDefaultMessage())
                            .append(";");
                });

                throw new SensorNotCreatedException(errorMessage.toString());
            }
            sensorsService.saveSensor(modelMapper.map(sensorDTO, Sensor.class));

            return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
