package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.EmailDTO;
import br.com.motta.ecommerce.dto.PaymentRequestDTO;
import br.com.motta.ecommerce.dto.ResponseDTO;
import br.com.motta.ecommerce.exception.SignatureInvalidException;
import br.com.motta.ecommerce.service.EmailService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentController {

    @Autowired
    private EmailService service;

    @Value("${access-token}")
    private String accessToken;

    @Value("${secret-signature}")
    private String secretSignature;

    @PostMapping("/payment")
    public ResponseEntity<?> payment(@RequestHeader("x-signature") String secretSignatureRequest, @RequestBody PaymentRequestDTO payment) throws MPException, MPApiException {

        System.out.println(secretSignatureRequest);
        secretSignatureRequest = secretSignatureRequest.split(",")[1].replace("v1=", "");
        System.out.println("NOVO -> " + secretSignatureRequest);

        if (!secretSignature.equals(secretSignatureRequest)){
            throw new SignatureInvalidException("Não foi permitido o acesso nesse endpoint.");
        }


        MercadoPagoConfig.setAccessToken(accessToken);

        PaymentClient client = new PaymentClient();
        Payment pagamento = client.get(Long.parseLong(payment.data().id()));

        String email = pagamento.getPayer().getEmail();

        String bodyEmail = "Parabéns! Sua compra em nossa loja foi aprovada, já estamos preparando com muito carinho!" +
                "\n\nID da Compra: " + pagamento.getId() + "\nDescrição da Compra: " + pagamento.getDescription() +
                "\n\nProdutos";
        service.sendEmail(new EmailDTO(email, "Compra Aprovada - Clow E-Commerce", bodyEmail));

        return ResponseEntity.ok(new ResponseDTO("Pedido aprovado com sucesso."));
    }

}
