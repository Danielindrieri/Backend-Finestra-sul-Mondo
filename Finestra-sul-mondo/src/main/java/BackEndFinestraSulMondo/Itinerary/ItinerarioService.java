package BackEndFinestraSulMondo.Itinerary;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItinerarioService {
    private final ItinerarioRepository itinerarioRepository;

    //prendiamo tutti gli itinerari
    public List<Itinerario> getAllItinerari() {
        return itinerarioRepository.findAll();
    }

    // prendiamo gli itinerari per idi
    public Itinerario getItinerarioById(Long id) {
        return itinerarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Itinerario non trovato"));
    }

    // prendiamo un itinerario per citta
    public List<Itinerario> getItinerarioPerCitta(String città) {
        return itinerarioRepository.findByCittà(città);
    }

    // ricerca di un itinerario per titolo
    public List<Itinerario> cercaPerTitolo(String titolo) {
        return itinerarioRepository.findByTitoloContainingIgnoreCase(titolo);
    }
}