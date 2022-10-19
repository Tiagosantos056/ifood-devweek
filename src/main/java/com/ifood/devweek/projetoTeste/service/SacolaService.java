package com.ifood.devweek.projetoTeste.service;

import com.ifood.devweek.projetoTeste.model.Item;
import com.ifood.devweek.projetoTeste.model.Sacola;
import com.ifood.devweek.projetoTeste.resource.ItemDTO;

public interface SacolaService {

    Item incluirItemSacola(ItemDTO itemDTO);
    Sacola verSacola(Long id);
    Sacola fecharSacola(Long id, int formaPagamento);
}
