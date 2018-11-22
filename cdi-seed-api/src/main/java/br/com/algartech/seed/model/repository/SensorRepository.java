package br.com.algartech.seed.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.algartech.seed.model.domain.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

}
