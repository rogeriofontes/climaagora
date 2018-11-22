package br.com.algartech.seed.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.algartech.seed.model.domain.Seed;
import br.com.algartech.seed.model.repository.SeedRepository;
import br.com.algartech.seed.model.service.SeedService;

@Service
public class SeedServiceImpl implements SeedService {

	@Autowired
	private SeedRepository seedRepository;

	@Override
	public Seed save(Seed seed) {
		return seedRepository.save(seed);
	}

	@Override
	@Cacheable("seedsInCache")
	public List<Seed> listAll() {
		return seedRepository.findAll();
	}

	@Override
	public Seed update(Long id, Seed seed) {
		seed.update(id, seed);
		return seedRepository.save(seed);
	}

	@Override
	@Cacheable("seedsInCache")
	public Optional<Seed> findById(Long id) {
		return seedRepository.findById(id);
	}

	@Override
	public void remove(Long id) {
		seedRepository.deleteById(id);
	}

	@Override
	@Cacheable("seedsInCache")
	public Page<Seed> findAllPageable(Pageable pageable) {
		return seedRepository.findAll(pageable);
	}

	@Override
	@Cacheable("seedsInCache")
	public Optional<Seed> findByEmail(String email) {
		return seedRepository.findByEmail(email);
	}

}
