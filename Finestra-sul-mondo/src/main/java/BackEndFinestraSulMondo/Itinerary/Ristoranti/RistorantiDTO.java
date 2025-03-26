package BackEndFinestraSulMondo.Itinerary.Ristoranti;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RistorantiDTO {
    @NotBlank(message = "Nome non può essere vuoto")
    private String titolo;

    @NotBlank(message = "Indirizzo non può essere vuoto")
    private String indirizzo;

    @NotBlank(message = "Descrizione non può essere vuota")
    private String descrizione;

    public static RistorantiDTO mapEntityToDTO(Ristoranti entity) {
        RistorantiDTO dto = new RistorantiDTO();
        dto.setTitolo(entity.getTitolo());
        dto.setIndirizzo(entity.getIndirizzo());
        dto.setDescrizione(entity.getDescrizione());
        return dto;
    }
}
