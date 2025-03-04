package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.EmailDTO;
import br.com.motta.ecommerce.dto.PaymentRequestDTO;
import br.com.motta.ecommerce.service.EmailService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.payment.PaymentPayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private EmailService service;

    @Value("${access-token}")
    private String accessToken;

    @PostMapping("/payment")
    public void payment(@RequestBody PaymentRequestDTO payment) throws MPException, MPApiException {

        MercadoPagoConfig.setAccessToken(accessToken);

        PaymentClient client = new PaymentClient();
        Payment pagamento = client.get(Long.parseLong(payment.id()));

        String email = pagamento.getPayer().getEmail();

        PaymentPayer payer = pagamento.getPayer();

        String bodyEmail = "Olá, " + payer.getFirstName() + "! Sua compra em nossa loja foi aprovada, já estamos preparando com muito carinho!" +
                "\n\nID da Compra: " + pagamento.getId() + "\nDescrição da Compra: " + pagamento.getDescription() +
                "\n\nProdutos";
        service.sendEmail(new EmailDTO(email, "Compra Aprovada - Clow E-Commerce", bodyEmail));
    }

}
