package com.ifood.devweek.projetoTeste.service.impl;

import com.ifood.devweek.projetoTeste.enumeration.FormaPagamento;
import com.ifood.devweek.projetoTeste.model.Item;
import com.ifood.devweek.projetoTeste.model.Restaurante;
import com.ifood.devweek.projetoTeste.model.Sacola;
import com.ifood.devweek.projetoTeste.repository.ItemRepository;
import com.ifood.devweek.projetoTeste.repository.ProdutoRepository;
import com.ifood.devweek.projetoTeste.repository.SacolaRepository;
import com.ifood.devweek.projetoTeste.resource.ItemDTO;
import com.ifood.devweek.projetoTeste.service.SacolaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {

    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtoRepository;

    private final ItemRepository itemRepository;

    @Override
    public Item incluirItemSacola(ItemDTO itemDTO) {

        Sacola sacola = verSacola(itemDTO.getSacolaId());

        if (sacola.isFechada()) {
            throw new RuntimeException("A sacola está fechada.!");
        }

        Item itemParaSerInserido = Item.builder()
                .quantidade(itemDTO.getQuantidade())
                .sacola(sacola)
                .produto(produtoRepository.findById(itemDTO.getProdutoId()).orElseThrow(
                        () -> {
                            throw new RuntimeException("Essa produto não existe..!");
                        }
                ))
                .build();

        List<Item> itensDaSacola = sacola.getItens();
        if (itensDaSacola.isEmpty()) {
            itensDaSacola.add(itemParaSerInserido);
        } else {
            Restaurante restauranteAtual = itensDaSacola.get(0).getProduto().getRestaurante();
            Restaurante restauranteDoItemParaAdicionar = itemParaSerInserido.getProduto().getRestaurante();

            if (restauranteAtual.equals(restauranteDoItemParaAdicionar)) {
                itensDaSacola.add(itemParaSerInserido);
            } else {
                throw new RuntimeException("Não é possível adicionar produtos de restaurantes diferete. Finalize o primeiro pedido");
            }
        }

        List<Double> valorDosItens = new ArrayList<>();
        for (Item itemDaSacola: itensDaSacola) {
            double valorTotalItem = itemDaSacola.getProduto().getValorUnitario() * itemDaSacola.getQuantidade();
            valorDosItens.add(valorTotalItem);
        }

        double valorTotalSacola = valorDosItens.stream()
                .mapToDouble(valorTotalDeCadaItem -> valorTotalDeCadaItem)
                .sum();
        sacola.setValorTotal(valorTotalSacola);

        /**Double valorTotalSacola = 0.0;
        for (Double valorDeCadaItem : valorDosItens){
            valorTotalSacola += valorDeCadaItem;
        }*/



        sacolaRepository.save(sacola);
        return itemRepository.save(itemParaSerInserido);
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow(
                () -> {
                    throw  new RuntimeException("Essa sacola não existe..!");
                }
        );
    }

    @Override
    public Sacola fecharSacola(Long id, int numeroFormaPagamento) {
        Sacola sacola = verSacola(id);
        if (sacola.getItens().isEmpty()) {
            throw new RuntimeException("Inclua itens na sacola.!");
        }

        FormaPagamento formaPagamento =
                numeroFormaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;

        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);
        return sacolaRepository.save(sacola);
    }
}
