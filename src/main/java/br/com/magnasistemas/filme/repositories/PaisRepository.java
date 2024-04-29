package br.com.magnasistemas.filme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.magnasistemas.filme.models.PaisModel;

@Repository
public interface PaisRepository extends JpaRepository<PaisModel, Long>{
}
