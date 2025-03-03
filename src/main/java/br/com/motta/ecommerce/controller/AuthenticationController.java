package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.authentication.LoginRequestDTO;
import br.com.motta.ecommerce.dto.authentication.LoginResponseDTO;
import br.com.motta.ecommerce.infra.security.JwtTokenUtil;
import br.com.motta.ecommerce.infra.security.UsuarioDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequestDTO data){
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(data.login(), data.password())
            );
            var userDetails = (UsuarioDetails) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data){
//        return null;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity register(@RequestBody @Validated UsuarioRequestDTO data){
//        return null;
//    }

}
