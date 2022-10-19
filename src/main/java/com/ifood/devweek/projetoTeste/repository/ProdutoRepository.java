package com.ifood.devweek.projetoTeste.repository;

import com.ifood.devweek.projetoTeste.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
