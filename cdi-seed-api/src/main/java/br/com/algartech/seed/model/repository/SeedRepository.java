package br.com.algartech.seed.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.algartech.seed.model.domain.Seed;

@Repository
public interface SeedRepository extends JpaRepository<Seed, Long> {
	Optional<Seed> findByEmail(String email);
}
