package ru.raisbex.springcourse.FirstRestApp.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.raisbex.springcourse.FirstRestApp.dto.MeasurementDTO;
import ru.raisbex.springcourse.FirstRestApp.execptions.SensorNotFoundException;
import ru.raisbex.springcourse.FirstRestApp.models.Measurement;
import ru.raisbex.springcourse.FirstRestApp.models.Sensor;
import ru.raisbex.springcourse.FirstRestApp.repositories.MeasurementRepository;
import ru.raisbex.springcourse.FirstRestApp.repositories.SensorRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    private final SensorRepository sensorRepository;

    private final ModelMapper modelMapper;

    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository, ModelMapper modelMapper) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
        this.modelMapper = modelMapper;


        // Добавьте кастомный маппинг
        modelMapper.addMappings(new PropertyMap<Measurement, MeasurementDTO>() {
            @Override
            protected void configure() {
                map().setSensorName(source.getOwner().getName());
            }
        });
    }

    @Transactional
    public MeasurementDTO add(MeasurementDTO measurementsDTO) {
        Measurement measurements = modelMapper.map(measurementsDTO, Measurement.class);

        // Преобразование SensorDTO в объект Sensor
        Sensor sensor = modelMapper.map(measurementsDTO.getSensor(), Sensor.class);

        // Проверка существования сенсора в базе данных
        Optional<Sensor> existingSensor = sensorRepository.findByName(sensor.getName());

        if (existingSensor.isPresent()) {
            // Если сенсор с таким именем существует, используем его
            measurements.setOwner(existingSensor.get());
        } else {
            // Обработка предупреждения: сенсор не существует
            throw new SensorNotFoundException(sensor.getName());
        }

        // Сохранение измерений в базе данных
        Measurement savedMeasurement = measurementRepository.save(measurements);

        return modelMapper.map(savedMeasurement, MeasurementDTO.class);

    }

    public List<MeasurementDTO> getAll(){
        List<Measurement> measurementList = measurementRepository.findAll();
        return modelMapper.map(measurementList, new TypeToken<List<MeasurementDTO>>() {}.getType());
    }

    public int getRainyDays(){
        return measurementRepository.countByRaining(true);
    }

}
