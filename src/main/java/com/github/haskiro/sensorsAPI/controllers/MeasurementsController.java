package com.github.haskiro.sensorsAPI.controllers;

import com.github.haskiro.sensorsAPI.dto.MeasurementDTO;
import com.github.haskiro.sensorsAPI.models.Measurement;
import com.github.haskiro.sensorsAPI.services.MeasurementsService;
import com.github.haskiro.sensorsAPI.util.ErrorResponse;
import com.github.haskiro.sensorsAPI.util.MeasurementNotCreatedException;
import com.github.haskiro.sensorsAPI.util.MeasurementValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.haskiro.sensorsAPI.util.ErrorUtil.returnErrorsAsString;

@Controller
@RequestMapping("/measurements")
@RestController
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDaysCount() {
        return measurementsService.getRainyDaysCount();
    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements() {
        return measurementsService.getMeasurements().stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {
        if (measurementDTO.getSensor() != null)
            measurementValidator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            String errorMessage = returnErrorsAsString(bindingResult);

            throw new MeasurementNotCreatedException(errorMessage);
        }

        measurementsService.saveMeasurement(convertToMeasurement(measurementDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MeasurementNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    public Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }
}
