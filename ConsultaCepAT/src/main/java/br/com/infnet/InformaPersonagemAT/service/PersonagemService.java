package br.com.infnet.InformaPersonagemAT.service;

import br.com.infnet.InformaPersonagemAT.exceptions.PersonagemNotFoundException;
import br.com.infnet.InformaPersonagemAT.model.PersonagemDTO;
import br.com.infnet.InformaPersonagemAT.util.PersonagemApi;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PersonagemService {

    private static final Logger log = Logger.getLogger(PersonagemService.class.getName());

    private Map<Long, PersonagemDTO> personagens = new HashMap<>();
    private Long lastId = (long) getAll().size();

    public List<PersonagemDTO> getAll() {
        return List.copyOf(personagens.values());
    }

    public List<PersonagemDTO> pegarTodosPersonagens(Long id, Integer limit) {
        return getAll().stream()
                .filter(personagem -> id == null || personagem.getId().equals(id))
                .limit(limit != null ? limit : getAll().size())
                .collect(Collectors.toList());
    }

    public void cadastrarPersonagem(PersonagemDTO personagemDTO) {
        Long id = ++this.lastId;
        personagemDTO.setId(id);
        personagens.put(id, personagemDTO);
    }

    public void deletarPersonagem(Long id) {
        PersonagemDTO personagemDTO = personagens.remove(id);
        if (personagemDTO == null) {
            throw new PersonagemNotFoundException("Personagem com ID " + id + " não encontrado.");
        }
    }

    public void atualizarPersonagem(Long id, PersonagemDTO atualizado) {
        if (!personagens.containsKey(id)) {
            throw new PersonagemNotFoundException("Personagem com ID " + id + " não encontrado.");
        }

        atualizado.setId(id);
        personagens.replace(id, atualizado);
    }

    public PersonagemDTO pegarPersonagemApi(Long id) {
        try {
            PersonagemDTO personagemDTO = PersonagemApi.pegarPorId(id);
            log.info("API de personagem consumida com sucesso. Status code: " + PersonagemApi.getLastStatusCode());
            return personagemDTO;
        } catch (RuntimeException ex) {
            throw new PersonagemNotFoundException(ex.getMessage());
        }
    }


}
