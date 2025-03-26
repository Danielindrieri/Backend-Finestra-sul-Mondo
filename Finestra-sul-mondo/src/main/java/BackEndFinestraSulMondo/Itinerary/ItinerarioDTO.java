package BackEndFinestraSulMondo.Itinerary;

import BackEndFinestraSulMondo.Itinerary.Attivita.AttivitaDTO;
import BackEndFinestraSulMondo.Itinerary.Ristoranti.RistorantiDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ItinerarioDTO {
    @JsonProperty("id")
    private Long id;

    @NotBlank(message="Titolo non può essere vuoto")
    @JsonProperty("titolo")
    @Pattern(regexp = "^\\S.*\\S$", message = "Titolo non valido")
    private String titolo;

    @NotBlank(message="Descrizione non può essere vuota")
    @JsonProperty("descrizione")
    @Size(min = 10, max = 1000, message = "Descrizione deve essere tra 10 e 1000 caratteri")
    private String descrizione;

    @NotBlank(message="Città non può essere vuota")
    @JsonProperty("città")
    private String città;

    @NotEmpty(message=" Aggiungi almeno una cosa da fare")
    @JsonProperty("coseDaFare")
    private List<AttivitaDTO> coseDaFare;

    @NotEmpty(message="Aggiungi almeno un Ristorante")
    @JsonProperty("doveMangiare")
    private List<RistorantiDTO> doveMangiare;

    @JsonProperty("immagini")
    private List<String> immaginiUrls;



    public static ItinerarioDTO mapEntityToDTO(Itinerario entity) {
        ItinerarioDTO dto = new ItinerarioDTO();
        dto.setId(entity.getId());
        dto.setTitolo(entity.getTitolo());
        dto.setDescrizione(entity.getDescrizione());
        dto.setCittà(entity.getCittà());
        dto.setImmaginiUrls(entity.getImmagini());

        dto.setCoseDaFare(
                entity.getCoseDaFare().stream()
                        .map(AttivitaDTO::mapEntityToDTO)
                        .collect(Collectors.toList())
        );

        dto.setDoveMangiare(
                entity.getDoveMangiare().stream()
                        .map(RistorantiDTO::mapEntityToDTO)
                        .collect(Collectors.toList())
        );

        return dto;
    }
}

