package BackEndFinestraSulMondo.Itinerary.Admin;

import BackEndFinestraSulMondo.Itinerary.Attivita.Attivita;
import BackEndFinestraSulMondo.Itinerary.Itinerario;
import BackEndFinestraSulMondo.Itinerary.ItinerarioDTO;
import BackEndFinestraSulMondo.Itinerary.ItinerarioRepository;
import BackEndFinestraSulMondo.Itinerary.Ristoranti.Ristoranti;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItinerarioAdminService {

    private final ItinerarioRepository itinerarioRepository;
    private final Cloudinary cloudinary;

    // Crea un nuovo itinerario
    public Itinerario creaItinerario(ItinerarioDTO dto, List<MultipartFile> immagini) throws IOException {
        List<String> immaginiUrls = uploadImmagini(immagini);

        Itinerario itinerario = Itinerario.builder()
                .titolo(dto.getTitolo())
                .descrizione(dto.getDescrizione())
                .città(dto.getCittà())
                .immagini(immaginiUrls)
                .build();

            List<Attivita> attivitaList = dto.getCoseDaFare().stream()
                    .map(attivitaDTO -> {
                        Attivita attivita = new Attivita(
                                attivitaDTO.getTitolo(),
                                attivitaDTO.getDescrizione()
                        );
                        attivita.setItinerario(itinerario); // Imposta la relazione
                        return attivita;
                    })
                    .collect(Collectors.toList());

            List<Ristoranti> ristorantiList = dto.getDoveMangiare().stream()
                    .map(ristoranteDTO -> {
                        Ristoranti ristorante = new Ristoranti(
                                ristoranteDTO.getTitolo(),
                                ristoranteDTO.getIndirizzo(),
                                ristoranteDTO.getDescrizione()
                        );
                        ristorante.setItinerario(itinerario);
                        return ristorante;
                    })
                    .collect(Collectors.toList());

        itinerario.getCoseDaFare().forEach(attivita -> attivita.setItinerario(itinerario));
        itinerario.getDoveMangiare().forEach(ristorante -> ristorante.setItinerario(itinerario));
        itinerario.setCoseDaFare(attivitaList);
        itinerario.setDoveMangiare(ristorantiList);

        return itinerarioRepository.save(itinerario);
    }

    // Aggiorna un itinerario esistente
    public Itinerario aggiornaItinerario(Long id, ItinerarioDTO dto, List<MultipartFile> immagini) throws IOException {
        Itinerario itinerarioEsistente = getItinerarioById(id);


        itinerarioEsistente.getCoseDaFare().clear();
        itinerarioEsistente.getDoveMangiare().clear();

        List<Attivita> nuoveAttivita = dto.getCoseDaFare().stream()
                .map(attivitaDTO -> {
                    Attivita attivita = new Attivita(
                            attivitaDTO.getTitolo(),
                            attivitaDTO.getDescrizione()
                    );
                    attivita.setItinerario(itinerarioEsistente);
                    return attivita;
                })
                .collect(Collectors.toList());

        List<Ristoranti> nuoviRistoranti = dto.getDoveMangiare().stream()
                .map(ristoranteDTO -> {
                    Ristoranti ristorante = new Ristoranti(
                            ristoranteDTO.getTitolo(),
                            ristoranteDTO.getIndirizzo(),
                            ristoranteDTO.getDescrizione()
                    );
                    ristorante.setItinerario(itinerarioEsistente);
                    return ristorante;
                })
                .collect(Collectors.toList());

        // Aggiorna i campi
        itinerarioEsistente.setTitolo(dto.getTitolo());
        itinerarioEsistente.setDescrizione(dto.getDescrizione());
        itinerarioEsistente.setCittà(dto.getCittà());
        itinerarioEsistente.setCoseDaFare(nuoveAttivita);
        itinerarioEsistente.setDoveMangiare(nuoviRistoranti);

        // Gestione immagini
        if (!immagini.isEmpty()) {
            eliminaImmaginiCloudinary(itinerarioEsistente.getImmagini());
            List<String> nuoveImmagini = uploadImmagini(immagini);
            itinerarioEsistente.setImmagini(nuoveImmagini);
        }

        return itinerarioRepository.save(itinerarioEsistente);
    }

    // Elimina un itinerario
    public void eliminaItinerario(Long id) throws IOException {
        Itinerario itinerario = getItinerarioById(id);
        eliminaImmaginiCloudinary(itinerario.getImmagini());
        itinerarioRepository.delete(itinerario);
    }

    // Metodi per il caricamento e l'eliminazione delle immagini
    private List<String> uploadImmagini(List<MultipartFile> files) throws IOException {
        List<String> urlImmagini = new ArrayList<>();
        for (MultipartFile file : files) {
            byte[] fileBytes = file.getBytes();
            Map<?,?> risultatoUpload = cloudinary.uploader().upload(fileBytes, ObjectUtils.asMap("folder", "itinerari"));
            urlImmagini.add((String) risultatoUpload.get("url").toString());
        }
        return urlImmagini;
    }

    private void eliminaImmaginiCloudinary(List<String> urls) {
        for (String url : urls) {
            try {
                String publicId = extractPublicId(url);
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            } catch (IOException e) {
                throw new RuntimeException("Errore nell'eliminazione dell'immagine", e);
            }
        }
    }

    private String extractPublicId(String url) {
        int startIndex = url.lastIndexOf("/") + 1;
        int endIndex = url.lastIndexOf(".");
        return url.substring(startIndex, endIndex);
    }

    private Itinerario getItinerarioById(Long id) {
        return itinerarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Itinerario non trovato"));
    }
}