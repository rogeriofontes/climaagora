package br.com.algartech.seed.web.resources;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.algartech.seed.model.domain.Seed;
import br.com.algartech.seed.model.service.SeedService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/seeds")
@Api(value = "Classe de Recurso seed via Rest para API SEED.")
public class SeedResources {
	private static final Logger log = LogManager.getLogger(SeedResources.class);
	
	@Autowired
	private SeedService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	@Timed
	@ApiOperation(value = "Servico busca uma lista de Seeds", response = Boolean.class, notes = "Essa operação é uma chamada de busca para a lista de seeds.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sucesso na busca de Seeds.", response = String.class),
			@ApiResponse(code = 404, message = "Algo deu errado na geração da lista de seeds."),
			@ApiResponse(code = 400, message = "Algo deu errado na geração da lista de seeds."),
			@ApiResponse(code = 500, message = "Erro no servidor ou na chamada para geração da lista de seeds.") })
	public ResponseEntity<List<Seed>> getAll() {
		List<Seed> seeds = service.listAll();
		return new ResponseEntity<>(seeds, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@CacheEvict(value = "seedsInCache", allEntries = true)
	@Timed
	@ApiOperation(value = "Servico gravar os dados do Seed", response = Boolean.class, notes = "Essa operação é uma chamada para gravação do seed.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sucesso na gravação do Seed.", response = String.class),
			@ApiResponse(code = 404, message = "Algo deu errado na gravação do Seed."),
			@ApiResponse(code = 400, message = "Algo deu errado na gravação do Seed."),
			@ApiResponse(code = 500, message = "Erro no servidor ou na chamada para gravação do Seed") })
	public ResponseEntity<Seed> add(@ApiParam(value = "Objeto seed.", required = true) @Valid @RequestBody Seed seed) {
		Seed result = service.save(seed);
		return new ResponseEntity<Seed>(result, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@CacheEvict(value = "seedsInCache", allEntries = true)
	@Timed
	@ApiOperation(value = "Servico alterar os dados do Seed", response = Boolean.class, notes = "Essa operação é uma chamada para alteração do seed.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sucesso na alteração do Seed.", response = String.class),
			@ApiResponse(code = 404, message = "Algo deu errado na alteração do Seed."),
			@ApiResponse(code = 400, message = "Algo deu errado na alteração do Seed."),
			@ApiResponse(code = 500, message = "Erro no servidor ou na chamada para alteração do Seed") })
	public ResponseEntity<Seed> change(@ApiParam(value = "Id do seed, para alteração.", required = true) @PathVariable Long id, @ApiParam(value = "Objeto seed.", required = true) @RequestBody Seed seed) {
		Seed result = service.update(id, seed);
		return new ResponseEntity<Seed>(result, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@CacheEvict(value = "seedsInCache", allEntries = true)
	@Timed
	@ApiOperation(value = "Servico remover os dados do Seed", response = Boolean.class, notes = "Essa operação é uma chamada para remoção do seed.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sucesso na remoção do Seed.", response = String.class),
			@ApiResponse(code = 404, message = "Algo deu errado na remoção do Seed."),
			@ApiResponse(code = 400, message = "Algo deu errado na remoção do Seed."),
			@ApiResponse(code = 500, message = "Erro no servidor ou na chamada para remoção do Seed") })
	public ResponseEntity<?> remove(@ApiParam(value = "Id do seed, para alteração.", required = true) @PathVariable Long id) {
		service.remove(id);
		return new ResponseEntity<>("Dados Deletados!", HttpStatus.OK);
	}

	@GetMapping(path = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Timed
	@ApiOperation(value = "Servico busca um seed por o email", response = Boolean.class, notes = "Essa operação é uma chamada de busca um seed por o email.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header") })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sucesso na busca de Seed.", response = String.class),
			@ApiResponse(code = 404, message = "Algo deu errado na geração da lista de seeds."),
			@ApiResponse(code = 400, message = "Algo deu errado na geração da lista de seeds."),
			@ApiResponse(code = 500, message = "Erro no servidor ou na chamada para geração da lista de seeds.") })
	public ResponseEntity<Seed> getSeedByEmail(@ApiParam(value = "Email do seed, para busca de seed por email.", required = true) @PathVariable("email") String email) {
		Optional<Seed> seed = service.findByEmail(email);

		log.info("Seed: " + seed.get().toString());
		return new ResponseEntity<Seed>(seed.get(), HttpStatus.OK);
	}
}
