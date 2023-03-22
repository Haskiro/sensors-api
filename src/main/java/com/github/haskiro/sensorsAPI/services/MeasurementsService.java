package com.github.haskiro.sensorsAPI.services;

import com.github.haskiro.sensorsAPI.models.Measurement;
import com.github.haskiro.sensorsAPI.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository) {
        this.measurementsRepository = measurementsRepository;
    }

    public int getRainyDaysCount() {
        return measurementsRepository.findByRaining(true).size();
    }

    public List<Measurement> getMeasurements() {
        return measurementsRepository.findAll();
    }
}
