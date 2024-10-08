package com.weathermonitor.weather_monitor.controller;

import com.weathermonitor.weather_monitor.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @PostMapping("/registration")
    public ResponseEntity<String> registerSensor(@RequestParam String name) {
        String key = sensorService.registerSensor(name);
        return ResponseEntity.ok("{\"key\":\"" + key + "\"}");
    }

    @PostMapping("/{key}/measurements")
    public ResponseEntity<String> addMeasurement(
            @PathVariable String key,
            @RequestParam double value,
            @RequestParam boolean raining) {
        sensorService.addMeasurement(key, value, raining);
        return ResponseEntity.ok("Measurement added");
    }
}
