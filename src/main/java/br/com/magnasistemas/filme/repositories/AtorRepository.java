package br.com.magnasistemas.filme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.magnasistemas.filme.models.AtorModel;
import jakarta.transaction.Transactional;

public interface AtorRepository extends JpaRepository<AtorModel, Long>{
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM filme.TB_ATOR; ALTER SEQUENCE filme.tb_ator_id_seq RESTART WITH 1;", nativeQuery = true)
	void LimparDadosERedefinirSequence();

}
