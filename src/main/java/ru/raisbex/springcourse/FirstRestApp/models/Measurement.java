package ru.raisbex.springcourse.FirstRestApp.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @NotNull(message = "value not be empty")
    @Min(value = -100, message = "temperature must be at least -100")
    @Max(value = 100, message = "temperature must be at most 100")
    private float value;

    @Column(name = "raining")
    @NotNull(message = "raining cant be empty")
    private boolean raining;

    @Valid
    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    @NotNull(message = "sensor cant be empty")
    private Sensor owner;

    @Column(name = "date")
    private LocalDateTime date;

    @PrePersist
    protected void onCreate() {
        date = LocalDateTime.now();; // Устанавливаем текущую дату и время при создании записи
    }

    public Measurement() {

    }

    public Measurement(float value, boolean raining, Sensor owner) {
        this.value = value;
        this.raining = raining;
        this.owner = owner;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Sensor getOwner() {
        return owner;
    }

    public void setOwner(Sensor owner) {
        this.owner = owner;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measurement that = (Measurement) o;
        return id == that.id && Float.compare(that.value, value) == 0 && raining == that.raining && Objects.equals(owner, that.owner) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, raining, owner, date);
    }

    @Override
    public String toString() {
        return "Measurements{" +
                "id=" + id +
                ", value=" + value +
                ", raining=" + raining +
                ", owner=" + owner +
                ", date=" + date +
                '}';
    }
}
