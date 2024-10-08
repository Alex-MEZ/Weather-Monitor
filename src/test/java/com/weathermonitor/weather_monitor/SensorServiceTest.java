package com.weathermonitor.weather_monitor.service;

import com.weathermonitor.weather_monitor.entity.Sensor;
import com.weathermonitor.weather_monitor.entity.Measurement;
import com.weathermonitor.weather_monitor.repository.SensorRepository;
import com.weathermonitor.weather_monitor.repository.MeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SensorServiceTest {

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private MeasurementRepository measurementRepository;

    @InjectMocks
    private SensorService sensorService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterSensorSuccess() {
        when(sensorRepository.existsByName("Test Sensor")).thenReturn(false);
        String sensorKey = sensorService.registerSensor("Test Sensor");
        assertNotNull(sensorKey);
        verify(sensorRepository, times(1)).save(any(Sensor.class));
    }

    @Test
    public void testRegisterSensorFailure() {
        when(sensorRepository.existsByName("Test Sensor")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> {
            sensorService.registerSensor("Test Sensor");
        });
        verify(sensorRepository, never()).save(any(Sensor.class));
    }

    @Test
    public void testAddMeasurementSuccess() {
        Sensor sensor = new Sensor();
        when(sensorRepository.findByKey("valid_key")).thenReturn(Optional.of(sensor));
        sensorService.addMeasurement("valid_key", 25.0, false);
        verify(measurementRepository, times(1)).save(any(Measurement.class));
    }

    @Test
    public void testAddMeasurementSensorNotFound() {
        when(sensorRepository.findByKey("invalid_key")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> {
            sensorService.addMeasurement("invalid_key", 25.0, false);
        });
        verify(measurementRepository, never()).save(any(Measurement.class));
    }
}
