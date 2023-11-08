package ru.raisbex.springcourse.FirstRestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.raisbex.springcourse.FirstRestApp.models.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {

    int countByRaining(boolean raining);
}
