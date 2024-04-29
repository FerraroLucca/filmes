package br.com.magnasistemas.filme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.magnasistemas.filme.models.AtorModel;

public interface AtorRepository extends JpaRepository<AtorModel, Long>{

}
