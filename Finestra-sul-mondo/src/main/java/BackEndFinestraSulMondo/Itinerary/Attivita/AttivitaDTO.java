package BackEndFinestraSulMondo.Itinerary.Attivita;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AttivitaDTO {
    @NotBlank(message = "il titolo dell'attivita non puo essere vuoto")
    private String titolo;

    @NotBlank(message = "la descrizione dell'attivita non puo essere vuota")
    private String descrizione;

    public static AttivitaDTO mapEntityToDTO(Attivita entity) {
        AttivitaDTO dto = new AttivitaDTO();
        dto.setTitolo(entity.getTitolo());
        dto.setDescrizione(entity.getDescrizione());
        return dto;
    }
}
