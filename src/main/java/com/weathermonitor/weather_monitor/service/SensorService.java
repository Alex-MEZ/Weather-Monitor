package com.weathermonitor.weather_monitor.service;

import com.weathermonitor.weather_monitor.entity.Sensor;
import com.weathermonitor.weather_monitor.entity.Measurement;
import com.weathermonitor.weather_monitor.repository.SensorRepository;
import com.weathermonitor.weather_monitor.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    public String registerSensor(String name) {
        if (sensorRepository.existsByName(name)) {
            throw new IllegalArgumentException("Sensor with this name already exists");
        }
        Sensor sensor = new Sensor();
        sensor.setName(name);
        sensorRepository.save(sensor);
        return sensor.getKey();
    }

    public void addMeasurement(String key, double value, boolean raining) {
        Optional<Sensor> sensorOptional = sensorRepository.findByKey(key);
        if (sensorOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid sensor key");
        }
        Sensor sensor = sensorOptional.get();
        Measurement measurement = new Measurement();
        measurement.setSensor(sensor);
        measurement.setValue(value);
        measurement.setRaining(raining);
        measurementRepository.save(measurement);

        sensor.setActive(true);
        sensorRepository.save(sensor);
    }

    @Scheduled(fixedRate = 60000)
    public void checkSensorActivity() {
        List<Sensor> sensors = sensorRepository.findAll();
        for (Sensor sensor : sensors) {
            if (Duration.between(sensor.getLastActivity(), LocalDateTime.now()).toMinutes() > 1) {
                sensor.setActive(false);
                sensorRepository.save(sensor);
            }
        }
    }
}
