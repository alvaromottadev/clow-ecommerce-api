package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.ResponseDTO;
import br.com.motta.ecommerce.model.Cliente;
import br.com.motta.ecommerce.model.Pedido;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferencePayer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    @Value("${access-token}")
    private String accessToken;

    public String efetuarPedido(Cliente cliente, Long pedidoId, String titulo, Integer quantidade, Double valor) {
        if (titulo == null || quantidade <= 0 || valor <= 0.0) {
            return "Não foi possível efetuar o pedido.";
        }

        try {
            MercadoPagoConfig.setAccessToken(accessToken);

            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title(titulo)
                    .quantity(quantidade)
                    .unitPrice(new BigDecimal(valor))
                    .currencyId("BRL")
                    .description("Pedido realizado na Clow E-Commerce")
                    .build();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("https://www.linkpagamento.com")
                    .pending("https://www.linkpendente.com")
                    .failure("https://linkvoltar.com")
                    .build();


            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .externalReference(cliente.getLogin() + "," + pedidoId)
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            return preference.getInitPoint();

        } catch (MPException | MPApiException e) {
            return e.toString();
        }
    }

}