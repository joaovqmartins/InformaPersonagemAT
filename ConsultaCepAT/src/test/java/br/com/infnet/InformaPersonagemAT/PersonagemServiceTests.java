package br.com.infnet.InformaPersonagemAT;

import br.com.infnet.InformaPersonagemAT.exceptions.PersonagemNotFoundException;
import br.com.infnet.InformaPersonagemAT.model.PersonagemDTO;
import br.com.infnet.InformaPersonagemAT.service.PersonagemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("PersonagemService Test")
class PersonagemServiceTests {

    private static final Logger log = LoggerFactory.getLogger(PersonagemServiceTests.class);

    private PersonagemService personagemService;

    @BeforeEach
    void setUp() {
        personagemService = new PersonagemService();
    }

    @Test
    @DisplayName("Deve retornar corretamente a lista de Personagens")
    void testPegarTodosPersonagens() {
        log.info("Iniciando o teste testPegarTodosPersonagens...");

        personagemService.cadastrarPersonagem(new PersonagemDTO(1L, "Personagem1", "Species1", "Origin1", List.of("Ability1", "Ability2")));
        personagemService.cadastrarPersonagem(new PersonagemDTO(2L, "Personagem2", "Species2", "Origin2", List.of("Ability3", "Ability4")));

        List<PersonagemDTO> result = personagemService.pegarTodosPersonagens(1L, null);
        assertEquals(1, result.size());
        assertEquals("Personagem1", result.get(0).getName());

        result = personagemService.pegarTodosPersonagens(null, null);
        assertEquals(2, result.size());

        log.info("Teste testPegarTodosPersonagens concluído com sucesso.");
    }

    @Test
    @DisplayName("Deve cadastrar corretamente um novo Personagem")
    void testCadastrarPersonagem() {
        log.info("Iniciando o teste testCadastrarPersonagem...");

        PersonagemDTO personagemDTO = new PersonagemDTO(null, "NovoPersonagem", "NovaSpecies", "NovaOrigin", List.of("NovaAbility"));

        assertDoesNotThrow(() -> personagemService.cadastrarPersonagem(personagemDTO));

        List<PersonagemDTO> allPersonagens = personagemService.getAll();
        assertEquals(1, allPersonagens.size());
        assertNotNull(allPersonagens.get(0).getId());
        assertEquals("NovoPersonagem", allPersonagens.get(0).getName());

        log.info("Teste testCadastrarPersonagem concluído com sucesso.");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar Personagem com ID inexistente")
    void testDeletarPersonagem() {
        log.info("Iniciando o teste testDeletarPersonagem...");

        personagemService.cadastrarPersonagem(new PersonagemDTO(1L, "PersonagemParaDeletar", "SpeciesParaDeletar", "OriginParaDeletar", List.of("AbilityParaDeletar")));

        assertDoesNotThrow(() -> personagemService.deletarPersonagem(1L));

        List<PersonagemDTO> allPersonagens = personagemService.getAll();
        assertEquals(0, allPersonagens.size());

        assertThrows(PersonagemNotFoundException.class, () -> personagemService.deletarPersonagem(2L));

        log.info("Teste testDeletarPersonagem concluído com sucesso.");
    }

    @Test
    @DisplayName("Deve atualizar corretamente um Personagem existente")
    void testAtualizarPersonagem() {
        log.info("Iniciando o teste testAtualizarPersonagem...");

        personagemService.cadastrarPersonagem(new PersonagemDTO(1L, "PersonagemOriginal", "SpeciesOriginal", "OriginOriginal", List.of("AbilityOriginal")));

        PersonagemDTO updatedPersonagemDTO = new PersonagemDTO(1L, "PersonagemAtualizado", "SpeciesAtualizado", "OriginAtualizado", List.of("AbilityAtualizado"));
        assertDoesNotThrow(() -> personagemService.atualizarPersonagem(1L, updatedPersonagemDTO));

        List<PersonagemDTO> allPersonagens = personagemService.getAll();
        assertEquals(1, allPersonagens.size());
        assertEquals("PersonagemAtualizado", allPersonagens.get(0).getName());

        assertThrows(PersonagemNotFoundException.class, () -> personagemService.atualizarPersonagem(999L, updatedPersonagemDTO));

        log.info("Teste testAtualizarPersonagem concluído com sucesso.");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar obter Personagem da API com ID inválido")
    void testPegarPersonagemApi() {
        log.info("Iniciando o teste testPegarPersonagemApi...");

        assertThrows(PersonagemNotFoundException.class, () -> personagemService.pegarPersonagemApi(99999L));

        log.info("Teste testPegarPersonagemApi concluído com sucesso.");
    }
    }
