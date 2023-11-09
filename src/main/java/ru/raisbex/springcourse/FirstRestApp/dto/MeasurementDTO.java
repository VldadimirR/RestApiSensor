package ru.raisbex.springcourse.FirstRestApp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL) // Аннотация, указывающая, что в JSON должны быть включены только поля, значения которых не равны null
public class MeasurementDTO {
    @NotNull(message = "value not be empty") // Аннотация, указывающая, что значение не должно быть пустым
    @Min(value = -100, message = "temperature must be at least -100") // Аннотация, указывающая, что значение должно быть не меньше -100
    @Max(value = 100, message = "temperature must be at most 100") // Аннотация, указывающая, что значение должно быть не больше 100
    private float value;
    @NotNull(message = "raining cannot be null") // Аннотация, указывающая, что значение не должно быть null
    private boolean raining;

    @Valid // Аннотация, указывающая, что значение должно быть валидировано
    private SensorDTO sensor; // Объект датчика, который должен быть валидирован

    private String sensorName;

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementDTO that = (MeasurementDTO) o;
        return Float.compare(that.value, value) == 0 && raining == that.raining && Objects.equals(sensor, that.sensor) && Objects.equals(sensorName, that.sensorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, raining, sensor, sensorName);
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
                "value=" + value +
                ", raining=" + raining +
                ", sensorName='" + sensorName + '\'' +
                '}';
    }
}
