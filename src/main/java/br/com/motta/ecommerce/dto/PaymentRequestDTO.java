package br.com.motta.ecommerce.dto;

public record PaymentRequestDTO(

        String action,

        String id,

        String type


) {
}
