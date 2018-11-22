package br.com.algartech.seed.web.convert.to;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.algartech.seed.model.domain.Seed;
import br.com.algartech.seed.web.dto.SeedDTO;

@Component
public class SeedTo implements Converter<Seed, SeedDTO> {

	@Override
	public SeedDTO convert(Seed source) {
		SeedDTO target = new SeedDTO();
		target.setId(source.getId());
		target.setDescription(source.getDescription());
		target.setEmail(source.getEmail());
		return target;
	}

}
