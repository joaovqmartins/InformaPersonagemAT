package br.com.infnet.InformaPersonagemAT.util;

import br.com.infnet.InformaPersonagemAT.model.PersonagemDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PersonagemApi {

    private static final Logger log = Logger.getLogger(PersonagemApi.class.getName());
    private static int lastStatusCode;

    public static PersonagemDTO pegarPorId(Long id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .version(HttpClient.Version.HTTP_2)
                    .uri(new URI("https://finalspaceapi.com/api/v0/character/" + id))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

            lastStatusCode = httpResponse.statusCode();

            ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
            JsonNode root = mapper.readTree(httpResponse.body());

            PersonagemDTO personagemDTO = PersonagemDTO.builder()
                    .id(root.at("/id").asLong())
                    .name(root.at("/name").asText())
                    .species(root.at("/species").asText())
                    .origin(root.at("/origin").asText())
                    .abilities(root.at("/abilities").findValuesAsText("name"))
                    .build();

            log.info("API de personagem consumida com sucesso");

            return personagemDTO;
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            log.warning("Erro ao consumir a API: " + ex.getMessage());
            throw new RuntimeException("Erro ao consumir a API: " + ex.getMessage());
        }
    }

    public static int getLastStatusCode() {
        return lastStatusCode;
    }
}
