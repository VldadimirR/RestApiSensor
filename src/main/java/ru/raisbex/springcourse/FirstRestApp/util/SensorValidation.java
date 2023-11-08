package ru.raisbex.springcourse.FirstRestApp.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.raisbex.springcourse.FirstRestApp.models.Sensor;
import ru.raisbex.springcourse.FirstRestApp.services.SensorService;

@Component
public class SensorValidation implements Validator {

    private final SensorService sensorService;

    public SensorValidation(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Sensor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Sensor sensor = (Sensor) o;

        if (sensorService.getSensorByName(sensor.getName()).isPresent()){
            errors.rejectValue("name","","Такой сенсор уже существует");
        }
    }
}
