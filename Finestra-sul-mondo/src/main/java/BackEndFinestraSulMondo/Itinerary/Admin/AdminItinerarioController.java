package BackEndFinestraSulMondo.Itinerary.Admin;

import BackEndFinestraSulMondo.Itinerary.Itinerario;
import BackEndFinestraSulMondo.Itinerary.ItinerarioDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/itinerari")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Slf4j
public class AdminItinerarioController {

    @Autowired
    private ItinerarioAdminService itinerarioAdminService;

    @PostMapping
    public ResponseEntity<Itinerario> creaItinerario(
            @RequestPart("itinerario") @Valid ItinerarioDTO itinerarioDTO,
            @RequestPart("immagini") List<MultipartFile> immagini
    ) throws IOException {
        log.info("Validazione superata per: {}", itinerarioDTO);

        if(immagini.isEmpty()) {
            throw new IllegalArgumentException("Almeno un'immagine è richiesta");
        }
        Itinerario nuovoItinerario = itinerarioAdminService.creaItinerario(itinerarioDTO, immagini);
        return new ResponseEntity<>(nuovoItinerario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Itinerario> aggiornaItinerario(
            @PathVariable Long id,
            @RequestPart("itinerario") @Valid ItinerarioDTO itinerarioDTO,
            @RequestPart(value = "immagini", required = false) List<MultipartFile> immagini
    ) throws IOException {
        return ResponseEntity.ok(itinerarioAdminService.aggiornaItinerario(
                id,
                itinerarioDTO,
                immagini != null ? immagini : List.of()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaItinerario(@PathVariable Long id) throws IOException {
        itinerarioAdminService.eliminaItinerario(id);
        return ResponseEntity.noContent().build();
    }
}