package ru.raisbex.springcourse.FirstRestApp.execptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SensorNotFoundException extends RuntimeException {
    public SensorNotFoundException(String sensorName) {
        super("Сенсор с именем " + sensorName + " не найден.");
    }
}

