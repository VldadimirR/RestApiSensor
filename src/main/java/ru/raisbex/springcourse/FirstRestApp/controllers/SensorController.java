package ru.raisbex.springcourse.FirstRestApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.raisbex.springcourse.FirstRestApp.dto.SensorDTO;
import ru.raisbex.springcourse.FirstRestApp.models.Sensor;
import ru.raisbex.springcourse.FirstRestApp.services.SensorService;
import ru.raisbex.springcourse.FirstRestApp.util.SensorValidation;

import javax.validation.Valid;

@RestController // Аннотация, указывающая, что этот класс является контроллером, который обрабатывает HTTP-запросы
@RequestMapping("/sensors") // Аннотация, указывающая, что все методы в этом классе будут обрабатывать запросы, начинающиеся с "/sensors"
public class SensorController {

    private final SensorService sensorService; // Сервис для работы с датчиками
    private final SensorValidation sensorValidation; // Сервис для валидации данных датчиков

    public SensorController(SensorService sensorService, SensorValidation sensorValidation) {
        this.sensorService = sensorService;
        this.sensorValidation = sensorValidation;
    }

    // Метод, который обрабатывает POST-запросы на "/sensors/registration"
    @PostMapping("/registration")
    public ResponseEntity<?> registrationSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                BindingResult bindingResult) {

        // Преобразуем DTO в сущность датчика
        Sensor sensor = sensorService.convertToEntity(sensorDTO);

        // Валидируем данные датчика
        sensorValidation.validate(sensor, bindingResult);

        // Если возникли ошибки валидации, то возвращаем их
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(sensorService.getErrorMessages(bindingResult),
                    HttpStatus.BAD_REQUEST);
        }

        // Регистрируем датчик
        sensorService.registerSensor(sensor);

        // Преобразуем сущность датчика в DTO
        SensorDTO sensorDTOResponse = sensorService.convertToDto(sensor);

        // Возвращаем DTO в ответе
        return new ResponseEntity<SensorDTO>(sensorDTOResponse, HttpStatus.OK);
    }

}


