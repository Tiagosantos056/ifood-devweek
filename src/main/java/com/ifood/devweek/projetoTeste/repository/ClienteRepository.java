package com.ifood.devweek.projetoTeste.repository;

import com.ifood.devweek.projetoTeste.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
