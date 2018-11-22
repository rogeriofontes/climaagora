package br.com.algartech.seed.model.service.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.algartech.seed.model.domain.Seed;
import br.com.algartech.seed.model.repository.SeedRepository;
import br.com.algartech.seed.model.service.SeedService;
import br.com.algartech.seed.model.service.impl.SeedServiceImpl;

@RunWith(SpringRunner.class)
public class SeedServiceImplTest {

	@TestConfiguration
	static class EmployeeServiceImplTestContextConfiguration {

		@Bean
		public SeedService employeeService() {
			return new SeedServiceImpl();
		}
	}

	@Autowired
	private SeedService seedService;

	@MockBean
	private SeedRepository seedRepository;

	/*@Before
	public void setUp() {
		Seed seed = new Seed("alex", "sks@ls.com");

		Mockito.when(seedService.findByEmail(seed.getEmail()).get()).thenReturn(seed);
	}
*/
	@Test
	public void whenValidName_thenEmployeeShouldBeFound() {
		List<Seed> found = seedService.listAll();

		assertThat(found.size()).isEqualTo(0);
	}
}
