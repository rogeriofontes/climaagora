package br.com.algartech.seed.model.repository.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolationException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.algartech.seed.model.domain.Seed;
import br.com.algartech.seed.model.repository.SeedRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SeedRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private SeedRepository repository;

	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void should_find_no_seeds_if_repository_is_empty() {
		Iterable<Seed> seeds = repository.findAll();

		assertThat(seeds).isEmpty();
	}

	@Test
	public void should_store_a_seed() {
		Seed seed = repository.save(new Seed("Jack", "smith@sokc.com"));

		assertThat(seed).hasFieldOrPropertyWithValue("description", "Jack");
		assertThat(seed).hasFieldOrPropertyWithValue("email", "smith@sokc.com");
	}

	@Ignore
	public void should_delete_all_seed() {
		entityManager.persist(new Seed("Jack", "Smith"));
		entityManager.persist(new Seed("Adam", "Johnson"));

		repository.deleteAll();

		assertThat(repository.findAll()).isEmpty();
	}

	@Ignore
	public void should_find_all_seeds() {
		Seed seed1 = new Seed("Jack", "Smith");
		entityManager.persist(seed1);

		Seed seed2 = new Seed("Adam", "Johnson");
		entityManager.persist(seed2);

		Seed seed3 = new Seed("Peter", "Smith");
		entityManager.persist(seed3);

		Iterable<Seed> seeds = repository.findAll();

		assertThat(seeds).hasSize(3).contains(seed1, seed2, seed3);
	}

	@Ignore
	public void should_find_seed_by_id() {
		Seed seed1 = new Seed("Jack", "Smith");
		entityManager.persist(seed1);

		Seed seed2 = new Seed("Adam", "Johnson");
		entityManager.persist(seed2);

		Optional<Seed> foundSeed = repository.findById(seed2.getId());

		assertThat(foundSeed.get()).isEqualTo(seed2);
	}

	@Test(expected = ConstraintViolationException.class)
	public void should_throw_constraint_violation_execption_description_is_null() {
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("O campo nome é obrigatorio");
		repository.save(new Seed());
	}
}
