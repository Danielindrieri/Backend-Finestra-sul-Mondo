package BackEndFinestraSulMondo.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/recensioni")
@RequiredArgsConstructor
public class RecensioniController {
    private final RecensioniService recensioniService;


    @PostMapping
    public ResponseEntity<String> saveRecensione(@RequestBody Recensioni recensioni) {
        recensioniService.saveRecensione(recensioni);
        return ResponseEntity.ok("Recensione salvata");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecensione(@PathVariable Long id) {
        recensioniService.deleteRecensioneById(id);
        return ResponseEntity.ok("Recensione eliminata");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecensione(
            @PathVariable Long id,
            @RequestBody Recensioni recensioni) {
        recensioniService.updateRecensioneById(id, recensioni);
        return ResponseEntity.ok("Recensione aggiornata");
    }

    @GetMapping
    public ResponseEntity<List<Recensioni>> getRecensioni() {
        return ResponseEntity.ok(recensioniService.getRecensioni());
    }
}