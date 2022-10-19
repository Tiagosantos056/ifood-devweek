package com.ifood.devweek.projetoTeste.repository;

import com.ifood.devweek.projetoTeste.model.Sacola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SacolaRepository extends JpaRepository<Sacola, Long> {

}
