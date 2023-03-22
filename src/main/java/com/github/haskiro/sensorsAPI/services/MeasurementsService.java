package com.github.haskiro.sensorsAPI.services;

import com.github.haskiro.sensorsAPI.models.Measurement;
import com.github.haskiro.sensorsAPI.models.Sensor;
import com.github.haskiro.sensorsAPI.repositories.MeasurementsRepository;
import com.github.haskiro.sensorsAPI.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsRepository sensorsRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsRepository sensorsRepository) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsRepository = sensorsRepository;
    }

    public int getRainyDaysCount() {
        return measurementsRepository.findByRaining(true).size();
    }

    public List<Measurement> getMeasurements() {
        return measurementsRepository.findAll();
    }

    @Transactional
    public void saveMeasurement(Measurement measurement) {
        enrichMeasurement(measurement);
        Sensor sensor = sensorsRepository.findByName(measurement.getSensor().getName()).get();
        measurement.setSensor(sensor);

        measurementsRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());
    }
}
