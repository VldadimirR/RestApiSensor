package ru.raisbex.springcourse.FirstRestApp.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.raisbex.springcourse.FirstRestApp.dto.SensorDTO;
import ru.raisbex.springcourse.FirstRestApp.models.Sensor;
import ru.raisbex.springcourse.FirstRestApp.repositories.SensorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    private final ModelMapper modelMapper;

    public SensorService(SensorRepository sensorRepository, ModelMapper modelMapper) {
        this.sensorRepository = sensorRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<Sensor> getSensorByName(String name){
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void registerSensor(Sensor sensor){
        sensorRepository.save(sensor);
    }

    public SensorDTO convertToDto(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }

    public Sensor convertToEntity(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    public List<String> getErrorMessages(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        return ((FieldError) error).getDefaultMessage();
                    } else {
                        return error.getDefaultMessage();
                    }
                })
                .collect(Collectors.toList());
    }

}
