package com.github.haskiro.sensorsAPI.services;

import com.github.haskiro.sensorsAPI.models.Sensor;
import com.github.haskiro.sensorsAPI.repositories.SensorsRepository;
import com.github.haskiro.sensorsAPI.util.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public Sensor getSingleSensor(int id) {
        Optional<Sensor> sensor = sensorsRepository.findById(id);

        return sensor.orElseThrow(SensorNotFoundException::new);
    }

    public Optional<Sensor> getSensorByName(String name) {
        return sensorsRepository.findByName(name);
    }

    @Transactional
    public void saveSensor(Sensor sensor) {
        enrichSensor(sensor);
        sensorsRepository.save(sensor);
    }

    private void enrichSensor(Sensor sensor) {
        sensor.setCreatedAt(LocalDateTime.now());
    }

}
