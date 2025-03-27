package BackEndFinestraSulMondo.Itinerary.Ristoranti;

import BackEndFinestraSulMondo.Itinerary.Attivita.Attivita;
import BackEndFinestraSulMondo.Itinerary.Itinerario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ristoranti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String indirizzo;

    @Column(nullable = false,length = 2000)
    private String descrizione;

    @ManyToOne
    @JoinColumn(name = "itinerario_id", nullable = false)
    private Itinerario itinerario;

    public Ristoranti(String titolo, String indirizzo, String descrizione) {
        this.titolo = titolo;
        this.indirizzo = indirizzo;
        this.descrizione = descrizione;
    }
}
