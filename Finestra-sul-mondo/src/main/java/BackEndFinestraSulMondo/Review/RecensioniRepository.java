package BackEndFinestraSulMondo.Review;

import BackEndFinestraSulMondo.Itinerary.Itinerario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecensioniRepository extends JpaRepository <Recensioni, Long>{
   List<Recensioni> findByItinerario(Itinerario itinerario);


}
