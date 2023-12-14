package br.com.infnet.InformaPersonagemAT;

import br.com.infnet.InformaPersonagemAT.model.PersonagemDTO;
import br.com.infnet.InformaPersonagemAT.util.PersonagemApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para PersonagemApi")
class PersonagemApiTests {

    @Test
    @DisplayName("Teste bem-sucedido para pegarPorId")
    void testPegarPorId_Success() {
        Long id = 1L;

        PersonagemDTO personagemDTO = PersonagemApi.pegarPorId(id);

        assertNotNull(personagemDTO);
        assertEquals(id, personagemDTO.getId());
    }

    @Test
    @DisplayName("Teste para pegarPorId com ID inválido lançando RuntimeException")
    void testPegarPorId_InvalidId_ThrowsRuntimeException() {
        Long invalidId = -1L;

        assertThrows(RuntimeException.class, () -> {
            PersonagemApi.pegarPorId(invalidId);
        });
    }

    @Test
    @DisplayName("Teste para getLastStatusCode")
    void testGetLastStatusCode() {
        int expectedStatusCode = 200;

        PersonagemApi.pegarPorId(1L);
        int lastStatusCode = PersonagemApi.getLastStatusCode();

        assertEquals(expectedStatusCode, lastStatusCode);
    }
}
