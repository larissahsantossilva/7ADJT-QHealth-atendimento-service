package br.com.fiap.qhealth.ms.atendimento_service.service;

import br.com.fiap.qhealth.ms.atendimento_service.external.paciente.PacienteClient;
import br.com.fiap.qhealth.ms.atendimento_service.external.paciente.response.PacienteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PacienteServiceTest {

    private PacienteClient pacienteClient;
    private PacienteService pacienteService;

    @BeforeEach
    void setUp() {
        pacienteClient = mock(PacienteClient.class);
        pacienteService = new PacienteService(pacienteClient);
    }

    @Test
    void testBuscarPacientePorId() {
        String cpf = "12345678900";
        PacienteResponse response = new PacienteResponse(
            UUID.randomUUID(),
            "John Doe",
            "john.doe@example.com",
            "johndoe",
            "12345678900",
            "M",
            "+5511999999999",
            LocalDate.of(1990, 1, 1),
            LocalDateTime.now(),
            LocalDateTime.now()
        );

        ResponseEntity<PacienteResponse> entity = ResponseEntity.ok(response);

        when(pacienteClient.buscarPacientePorId(cpf)).thenReturn(entity);

        ResponseEntity<PacienteResponse> result = pacienteService.buscarPacientePorId(cpf);

        assertEquals(entity, result);
        assertEquals(response, result.getBody());
    }
}