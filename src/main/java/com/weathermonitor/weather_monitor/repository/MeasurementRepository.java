package com.weathermonitor.weather_monitor.repository;

import com.weathermonitor.weather_monitor.entity.Measurement;
import com.weathermonitor.weather_monitor.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findTop20BySensorOrderByTimestampDesc(Sensor sensor);
}
