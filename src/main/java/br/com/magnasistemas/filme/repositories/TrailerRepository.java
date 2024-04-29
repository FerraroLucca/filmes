package br.com.magnasistemas.filme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.magnasistemas.filme.models.TrailerModel;

public interface TrailerRepository extends JpaRepository<TrailerModel, Long>{

}
