package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.EmailDTO;
import br.com.motta.ecommerce.dto.PaymentRequestDTO;
import br.com.motta.ecommerce.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private EmailService service;

    @PostMapping("/payment")
    public void payment(@RequestBody PaymentRequestDTO payment){
        service.sendEmail(new EmailDTO("teste", "teste", payment.action() + " | "  + payment.id() + " | " + payment.type()));
    }

}
