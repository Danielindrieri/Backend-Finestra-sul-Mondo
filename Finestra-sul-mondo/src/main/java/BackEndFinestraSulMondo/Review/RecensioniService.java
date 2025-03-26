package BackEndFinestraSulMondo.Review;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecensioniService {
    private final RecensioniRepository recensioniRepository;

    @Autowired
    private RecensioniService(RecensioniRepository recensioniRepository) {
        this.recensioniRepository = recensioniRepository;
    }

    public void saveRecensione(Recensioni recensioni) {
        recensioniRepository.save(recensioni);
    }

    public void deleteRecensioneById(Long id) {
        recensioniRepository.deleteById(id);
    }

    public List<Recensioni> getRecensioni() {
        recensioniRepository.findAll();
        return recensioniRepository.findAll();
    }

    public void updateRecensioneById(Long id, Recensioni recensioni) {
        if (recensioni == null) {
            throw new IllegalArgumentException("L'oggetto recensione non può essere nullo");
        }

        Recensioni recensione = recensioniRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recensione con ID " + id + " non trovata."));

        if (recensioni.getRecensione() != null) {
            recensione.setRecensione(recensioni.getRecensione());
        }
        if (recensioni.getVoto() != null) {
            recensione.setVoto(recensioni.getVoto());
        }
        recensioniRepository.save(recensione);
    }
}
