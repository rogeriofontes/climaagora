package br.com.algartech.seed.model.service;

import java.util.Optional;

import br.com.algartech.seed.model.domain.Seed;

public interface SeedService extends CommonService<Seed, Long> {
	Optional<Seed> findByEmail(String email);
}
