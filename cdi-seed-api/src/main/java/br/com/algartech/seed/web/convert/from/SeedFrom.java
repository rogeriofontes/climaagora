package br.com.algartech.seed.web.convert.from;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.algartech.seed.model.domain.Seed;
import br.com.algartech.seed.web.dto.SeedDTO;

@Component
public class SeedFrom implements Converter<SeedDTO, Seed> {

	@Override
	public Seed convert(SeedDTO source) {
		Seed target = new Seed();
		target.setId(source.getId());
		target.setDescription(source.getDescription());
		target.setEmail(source.getEmail());
		return target;
	}

}
