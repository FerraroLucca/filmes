package br.com.magnasistemas.filme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.magnasistemas.filme.models.PaisModel;
import jakarta.transaction.Transactional;

@Repository
public interface PaisRepository extends JpaRepository<PaisModel, Long>{
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM filme.TB_PAIS; ALTER SEQUENCE filme.tb_pais_id_seq RESTART WITH 1;", nativeQuery = true)
	void LimparDadosERedefinirSequence();
	
}
