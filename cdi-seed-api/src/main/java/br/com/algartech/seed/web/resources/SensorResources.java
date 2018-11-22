package br.com.algartech.seed.web.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.algartech.seed.model.domain.Sensor;
import br.com.algartech.seed.model.repository.SensorRepository;

@RestController
@RequestMapping(path = "/sensors")
public class SensorResources {
	
	@Autowired
	private SensorRepository sensorRepository;
	
	@GetMapping
	public List<Sensor> list() {
		return sensorRepository.findAll();
	}
 	
}
