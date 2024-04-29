package br.com.magnasistemas.filme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.magnasistemas.filme.models.FilmeModel;

public interface FilmeRepository extends JpaRepository<FilmeModel, Long>{

}
