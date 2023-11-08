package ru.raisbex.springcourse.FirstRestApp.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.raisbex.springcourse.FirstRestApp.models.Sensor;
import ru.raisbex.springcourse.FirstRestApp.repositories.SensorRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Optional<Sensor> getSensorByName(String name){
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void registerSensor(Sensor sensor){

        sensorRepository.save(sensor);
    }
}
