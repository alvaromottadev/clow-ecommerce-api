package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.ResponseDTO;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
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

    public ResponseEntity<ResponseDTO> efetuarPedido(String titulo, Integer quantidade, Double valor){
        if (titulo == null || quantidade <= 0 || valor <= 0.0){
            return ResponseEntity.badRequest().body(new ResponseDTO("Não foi possível efetuar o pedido."));
        }

        try {
            MercadoPagoConfig.setAccessToken(accessToken);

            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title(titulo)
                    .quantity(quantidade)
                    .unitPrice(new BigDecimal(0.1))
                    .currencyId("BRL")
                    .build();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("https://www.youtube.com")
                    .pending("https://instagram.com")
                    .failure("https://facebook.com")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .build();

            PreferenceClient client = new PreferenceClient();

            Preference preference = client.create(preferenceRequest);

            return ResponseEntity.ok(new ResponseDTO(preference.getInitPoint()));

        } catch (MPException | MPApiException e){
            return ResponseEntity.badRequest().body(new ResponseDTO(e.toString()));
        }
    }

    public ResponseEntity<ResponseDTO> verificarStatusPagamento(String url, String paymentId) {
        try {
            // Inicialize o Mercado Pago com o access token
            MercadoPagoConfig.setAccessToken(accessToken);

            // Recupera o pagamento pelo ID

            PaymentClient client = new PaymentClient();
            Payment payment = client.get(Long.parseLong(paymentId));

            // Verifica o status do pagamento
            String paymentStatus = payment.getStatus();
            String message;

            // Tratamento baseado no status do pagamento
            switch (paymentStatus) {
                case "approved":
                    message = "Pagamento aprovado.";
                    break;
                case "pending":
                    message = "Pagamento pendente.";
                    break;
                case "rejected":
                    message = "Pagamento rejeitado.";
                    break;
                default:
                    message = "Status desconhecido: " + paymentStatus;
            }

            // Retorna o status ao cliente
            return ResponseEntity.ok(new ResponseDTO(message + " | " + url));

        } catch (MPException | MPApiException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.toString()));
        }
    }
}
