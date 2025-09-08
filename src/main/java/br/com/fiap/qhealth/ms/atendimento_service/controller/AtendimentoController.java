package br.com.fiap.qhealth.ms.atendimento_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/atendimentos")
public class AtendimentoController {

    @GetMapping
    public String hello() {
        return "Hello, World!";
    }
}
