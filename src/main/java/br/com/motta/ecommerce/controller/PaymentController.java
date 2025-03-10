package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.EmailDTO;
import br.com.motta.ecommerce.dto.PaymentRequestDTO;
import br.com.motta.ecommerce.dto.ResponseDTO;
import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.exception.SignatureInvalidException;
import br.com.motta.ecommerce.model.Cliente;
import br.com.motta.ecommerce.repository.ClienteRepository;
import br.com.motta.ecommerce.service.EmailService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private EmailService service;

    @Autowired
    private ClienteRepository clienteRepository;

    @Value("${access-token}")
    private String accessToken;

    @Value("${secret-signature}")
    private String secretSignature;

    @PostMapping("/payment")
    public ResponseEntity<?> payment(@RequestBody PaymentRequestDTO payment) throws MPException, MPApiException {

//        if (!secretSignature.equals(secretSignatureRequest)){
//            throw new SignatureInvalidException("Não foi permitido o acesso nesse endpoint.");
//        }

        if (payment.action().equals("payment.created")) {
            MercadoPagoConfig.setAccessToken(accessToken);

            PaymentClient client = new PaymentClient();
            Payment pagamento = client.get(Long.parseLong(payment.data().id()));

            String login = pagamento.getExternalReference().split(",")[0];
            String pedidoId = pagamento.getExternalReference().split(",")[1];
            Cliente cliente = clienteRepository.findByLogin(login).orElseThrow(() -> new NotFoundException("Cliente não encontrado."));
            ;

            String bodyEmail = "Olá, " + cliente.getUsername() + "! Sua compra em nossa loja foi aprovada, já estamos preparando com muito carinho!" +
                    "\n\nID da Compra: " + pagamento.getId() + "\nID do Pedido: " + pedidoId + "\nDescrição da Compra: " + pagamento.getDescription();

            service.sendEmail(new EmailDTO(login, "Compra Aprovada - Clow E-Commerce", bodyEmail));

            return ResponseEntity.ok(new ResponseDTO("Pedido aprovado com sucesso."));
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
