package BackEndFinestraSulMondo.Itinerary;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/itinerari")
@RequiredArgsConstructor
public class ItinerarioController {
    private final ItinerarioService itinerarioService;

    // Ottieni TUTTI gli itinerari (pubblico)
    @GetMapping
    public ResponseEntity<List<ItinerarioDTO>> ottieniTuttiItinerari() {
        List<ItinerarioDTO> dtos = itinerarioService.getAllItinerari()
                .stream()
                .map(ItinerarioDTO::mapEntityToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // Ottieni per ID (pubblico)
    @GetMapping("/{id}")
    public ResponseEntity<?> ottieniItinerarioPerId(@PathVariable String id) {
        try {
            // Controllo ID vuoto o non numerico
            if (id == null || id.trim().isEmpty() || !id.matches("\\d+")) {
                throw new NumberFormatException();
            }

            Long itineraryId = Long.parseLong(id);
            Itinerario itinerario = itinerarioService.getItinerarioById(itineraryId);
            return ResponseEntity.ok(ItinerarioDTO.mapEntityToDTO(itinerario));

        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of(
                            "error", "ID non valido",
                            "message", "L'ID deve essere un numero intero positivo"
                    )
            );
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                            "error", "Itinerario non trovato",
                            "message", "Nessun itinerario trovato con ID: " + id
                    )
            );
        }
    }


    // Cerca per città (pubblico)
    @GetMapping("/citta/{citta}")
    public ResponseEntity<List<ItinerarioDTO>> ottieniItinerariPerCitta(@PathVariable String citta) {
        List<ItinerarioDTO> dtos = itinerarioService.getItinerarioPerCitta(citta)
                .stream()
                .map(ItinerarioDTO::mapEntityToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // Cerca per titolo (pubblico)
    @GetMapping("/cerca")
    public ResponseEntity<List<ItinerarioDTO>> cercaItinerariPerTitolo(@RequestParam String titolo) {
        List<ItinerarioDTO> dtos = itinerarioService.cercaPerTitolo(titolo)
                .stream()
                .map(ItinerarioDTO::mapEntityToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}