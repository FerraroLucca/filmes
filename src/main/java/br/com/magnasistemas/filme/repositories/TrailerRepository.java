package br.com.magnasistemas.filme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.magnasistemas.filme.models.TrailerModel;
import jakarta.transaction.Transactional;

public interface TrailerRepository extends JpaRepository<TrailerModel, Long>{
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM filme.TB_TRAILER; ALTER SEQUENCE filme.tb_trailer_id_seq RESTART WITH 1;", nativeQuery = true)
	void LimparDadosERedefinirSequence();

}
