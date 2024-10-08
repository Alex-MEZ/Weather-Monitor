package com.weathermonitor.weather_monitor.repository;

import com.weathermonitor.weather_monitor.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Optional<Sensor> findByKey(String key);
    boolean existsByName(String name);
}
