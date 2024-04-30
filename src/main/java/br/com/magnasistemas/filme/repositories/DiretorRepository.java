package br.com.magnasistemas.filme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.magnasistemas.filme.models.DiretorModel;
import jakarta.transaction.Transactional;

@Repository
public interface DiretorRepository extends JpaRepository<DiretorModel, Long>{
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM filme.TB_DIRETOR; ALTER SEQUENCE filme.tb_diretor_id_seq RESTART WITH 1;", nativeQuery = true)
	void LimparDadosERedefinirSequence();
	
}
