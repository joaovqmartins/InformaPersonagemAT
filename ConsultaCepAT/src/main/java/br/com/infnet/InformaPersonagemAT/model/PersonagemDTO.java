package br.com.infnet.InformaPersonagemAT.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
public class PersonagemDTO {


    @JsonProperty("id")
    public Long id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("species")
    public String species;
    @JsonProperty("origin")
    public String origin;
    @JsonProperty("abilities")
    public List<String> abilities;

    public PersonagemDTO(Long id, String name, String species, String origin, List<String> abilities) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.origin = origin;
        this.abilities = abilities;
    }
}
