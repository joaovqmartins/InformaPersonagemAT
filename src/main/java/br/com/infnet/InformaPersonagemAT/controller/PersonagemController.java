package br.com.infnet.InformaPersonagemAT.controller;

import br.com.infnet.InformaPersonagemAT.exceptions.PersonagemNotFoundException;
import br.com.infnet.InformaPersonagemAT.model.PersonagemDTO;
import br.com.infnet.InformaPersonagemAT.service.PersonagemService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Log
@RestController
@RequestMapping("/search")
public class PersonagemController {

    private PersonagemService personagemService;

    public PersonagemController(PersonagemService personagemService) {
        this.personagemService = personagemService;
    }

    @GetMapping
    public ResponseEntity<List<PersonagemDTO>> pegarTodosPersonagens(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "limit", required = false) Integer limit) {
        List<PersonagemDTO> personagensDTO = personagemService.pegarTodosPersonagens(id, limit);
        return ResponseEntity.ok(personagensDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity pegarPersonagemPorId(@PathVariable Long id) {
        try {
            PersonagemDTO personagemDTO = personagemService.pegarPersonagemApi(id);
            return ResponseEntity.ok(personagemDTO);
        } catch (PersonagemNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> cadastrarPersonagem(@RequestBody PersonagemDTO personagemDTO) {
        personagemService.cadastrarPersonagem(personagemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Personagem cadastrado com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarPersonagem(@PathVariable Long id, @RequestBody PersonagemDTO personagemDTO) {
        try {
            personagemService.atualizarPersonagem(id, personagemDTO);
            return ResponseEntity.ok("Personagem atualizado com sucesso");
        } catch (PersonagemNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarPersonagem(@PathVariable Long id) {
        try {
            personagemService.deletarPersonagem(id);
            return ResponseEntity.ok("Personagem deletado com sucesso");
        } catch (PersonagemNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}