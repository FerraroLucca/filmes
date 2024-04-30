package br.com.magnasistemas.filme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.magnasistemas.filme.models.FilmeModel;
import jakarta.transaction.Transactional;

public interface FilmeRepository extends JpaRepository<FilmeModel, Long>{
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM filme.TB_FILME; ALTER SEQUENCE filme.tb_filme_id_seq RESTART WITH 1;", nativeQuery = true)
	void LimparDadosERedefinirSequence();

}
