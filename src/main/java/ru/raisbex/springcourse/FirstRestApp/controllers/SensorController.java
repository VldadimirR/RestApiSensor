package ru.raisbex.springcourse.FirstRestApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.raisbex.springcourse.FirstRestApp.dto.SensorDTO;
import ru.raisbex.springcourse.FirstRestApp.models.Sensor;
import ru.raisbex.springcourse.FirstRestApp.services.SensorService;
import ru.raisbex.springcourse.FirstRestApp.util.SensorValidation;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidation sensorValidation;

    public SensorController(SensorService sensorService, ModelMapper modelMapper, SensorValidation sensorValidation) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidation = sensorValidation;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registrationSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {

        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);

        sensorValidation.validate(sensor, bindingResult);

        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(getErrorMessages(bindingResult), HttpStatus.BAD_REQUEST);
        }
        sensorService.registerSensor(sensor);

        SensorDTO sensorDTOResponse = modelMapper.map(sensor, SensorDTO.class);

        return new ResponseEntity<SensorDTO>(sensorDTOResponse, HttpStatus.OK);
    }

    private List<String> getErrorMessages(BindingResult bindingResult) {
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


