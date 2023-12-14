package br.com.infnet.InformaPersonagemAT.exceptions;

public class PersonagemNotFoundException extends RuntimeException {
    public PersonagemNotFoundException(String message) {
        super(message);
    }
}

