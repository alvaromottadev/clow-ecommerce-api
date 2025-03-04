package br.com.motta.ecommerce.dto;

import br.com.motta.ecommerce.dto.produto.DataDTO;

public record PaymentRequestDTO(

        String action,

        DataDTO data,

        String type


) {
}
