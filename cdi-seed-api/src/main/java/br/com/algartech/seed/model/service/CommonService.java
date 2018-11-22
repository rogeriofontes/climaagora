package br.com.algartech.seed.model.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonService<T, ID extends Serializable> {
	T save(T t);

	List<T> listAll();

	T update(ID id, T t);

	Optional<T> findById(Long id);

	void remove(Long id);

	Page<T> findAllPageable(Pageable pageable);
}
