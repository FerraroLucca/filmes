package br.com.magnasistemas.filme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.magnasistemas.filme.models.DiretorModel;

@Repository
public interface DiretorRepository extends JpaRepository<DiretorModel, Long>{
}
