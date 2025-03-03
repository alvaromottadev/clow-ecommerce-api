package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.EmailDTO;
import br.com.motta.ecommerce.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService service;

    @PostMapping
    public void sendEmail(@RequestBody EmailDTO email){
        service.sendEmail(email);
    }

}
