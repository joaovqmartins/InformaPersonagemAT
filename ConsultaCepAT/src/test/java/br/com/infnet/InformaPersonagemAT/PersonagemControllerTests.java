package br.com.infnet.InformaPersonagemAT;

import br.com.infnet.InformaPersonagemAT.controller.PersonagemController;
import br.com.infnet.InformaPersonagemAT.exceptions.PersonagemNotFoundException;
import br.com.infnet.InformaPersonagemAT.model.PersonagemDTO;
import br.com.infnet.InformaPersonagemAT.service.PersonagemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonagemControllerTests {

    @Mock
    private PersonagemService personagemService;

    private Logger logger;

    @InjectMocks
    private PersonagemController personagemController;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia ao chamar pegarTodosPersonagens com lista vazia")
    void testPegarTodosPersonagensListaVazia() {

        when(personagemService.pegarTodosPersonagens(any(), any())).thenReturn(Collections.emptyList());


        ResponseEntity<List<PersonagemDTO>> responseEntity = personagemController.pegarTodosPersonagens(null, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
    }

    @Test
    @DisplayName("Deve retornar um personagem ao chamar pegarPersonagemPorId")
    void testPegarPersonagemPorIdSucesso() {
        PersonagemDTO personagemDTO = new PersonagemDTO();
        when(personagemService.pegarPersonagemApi(any())).thenReturn(personagemDTO);


        ResponseEntity responseEntity = personagemController.pegarPersonagemPorId(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(personagemDTO, responseEntity.getBody());
    }

    @Test
    @DisplayName("Deve retornar NOT FOUND ao chamar pegarPersonagemPorId com PersonagemNotFoundException")
    void testPegarPersonagemPorIdNotFound() {

        when(personagemService.pegarPersonagemApi(any())).thenThrow(new PersonagemNotFoundException("Personagem não encontrado"));


        ResponseEntity responseEntity = personagemController.pegarPersonagemPorId(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().toString().contains("Personagem não encontrado"));
    }

    @Test
    @DisplayName("Deve retornar CREATED ao chamar cadastrarPersonagem")
    void testCadastrarPersonagemSucesso() {

        PersonagemDTO personagemDTO = new PersonagemDTO();


        ResponseEntity<String> responseEntity = personagemController.cadastrarPersonagem(personagemDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Personagem cadastrado com sucesso", responseEntity.getBody());
        verify(personagemService, times(1)).cadastrarPersonagem(any());
    }

    @Test
    @DisplayName("Deve retornar OK ao chamar atualizarPersonagem")
    void testAtualizarPersonagemSucesso() {

        PersonagemDTO personagemDTO = new PersonagemDTO();


        ResponseEntity<String> responseEntity = personagemController.atualizarPersonagem(1L, personagemDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Personagem atualizado com sucesso", responseEntity.getBody());
        verify(personagemService, times(1)).atualizarPersonagem(any(), any());
    }

    @Test
    @DisplayName("Deve retornar NOT FOUND ao chamar atualizarPersonagem com PersonagemNotFoundException")
    void testAtualizarPersonagemNotFound() {

        doThrow(new PersonagemNotFoundException("Personagem não encontrado")).when(personagemService).atualizarPersonagem(any(), any());


        ResponseEntity responseEntity = personagemController.atualizarPersonagem(1L, new PersonagemDTO());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().toString().contains("Personagem não encontrado"));
    }

    @Test
    @DisplayName("Deve retornar OK ao chamar deletarPersonagem")
    void testDeletarPersonagemSucesso() {

        ResponseEntity<String> responseEntity = personagemController.deletarPersonagem(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Personagem deletado com sucesso", responseEntity.getBody());
        verify(personagemService, times(1)).deletarPersonagem(any());
    }

    @Test
    @DisplayName("Deve retornar NOT FOUND ao chamar deletarPersonagem com PersonagemNotFoundException")
    void testDeletarPersonagemNotFound() {

        doThrow(new PersonagemNotFoundException("Personagem não encontrado")).when(personagemService).deletarPersonagem(any());


        ResponseEntity responseEntity = personagemController.deletarPersonagem(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().toString().contains("Personagem não encontrado"));
    }
}
