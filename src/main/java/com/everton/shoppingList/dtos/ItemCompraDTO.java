package com.everton.shoppingList.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BuyRequestDto {
    private BigDecimal quantidade;
    private BigDecimal preco;

}
