package BackEndFinestraSulMondo.Itinerary;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItinerarioRepository extends JpaRepository<Itinerario,Long> {
     List<Itinerario> findByCittà(String città);
     List<Itinerario> findByTitoloContainingIgnoreCase(String titolo);
}
