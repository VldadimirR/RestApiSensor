package ru.raisbex.springcourse.FirstRestApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.raisbex.springcourse.FirstRestApp.dto.MeasurementDTO;
import ru.raisbex.springcourse.FirstRestApp.services.MeasurementService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementService measurementService; // Сервис для работы с измерениями
    private final ModelMapper modelMapper; // Объект для преобразования между сущностями и DTO

    public MeasurementsController(MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }

    // Получить все измерения
    @GetMapping
    public ResponseEntity<List<MeasurementDTO>> getAllMeasurement() {
        List<MeasurementDTO> measurementDTOList = measurementService.getAll(); // Получение списка измерений
        return new ResponseEntity<>(measurementDTOList, HttpStatus.OK); // Возвращение списка с кодом ответа OK
    }

    // Получить количество дождливых дней
    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Integer> getRainyDays() {
        int countRainy = measurementService.getRainyDays(); // Получение количества дождливых дней
        return new ResponseEntity<>(countRainy, HttpStatus.OK); // Возвращение количества с кодом ответа OK
    }

    // Добавить новое измерение
    @PostMapping("/add")
    public ResponseEntity<?> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO) {
        MeasurementDTO measurementDTOResponse = measurementService.add(measurementDTO); // Добавление нового измерения
        return new ResponseEntity<MeasurementDTO>(measurementDTOResponse, HttpStatus.OK); // Возвращение нового измерения с кодом ответа OK
    }
}

