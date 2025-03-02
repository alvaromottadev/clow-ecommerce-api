package br.com.motta.ecommerce.dto;

public record EnderecoRequestDTO

        (String logradouro,
         Integer numero,
         String bairro,
         String complemento,
         String cidade,
         String estado,
         String cep) {

}
