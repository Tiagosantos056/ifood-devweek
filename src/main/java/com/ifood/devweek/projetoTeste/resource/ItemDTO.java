package com.ifood.devweek.projetoTeste.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Builder
@Data
@Embeddable
@NoArgsConstructor
public class ItemDTO {
    private Long produtoId;
    private int quantidade;
    private Long sacolaId;
}
