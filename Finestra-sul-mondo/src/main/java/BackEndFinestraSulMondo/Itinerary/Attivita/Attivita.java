package BackEndFinestraSulMondo.Itinerary.Attivita;

import BackEndFinestraSulMondo.Itinerary.Itinerario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Attivita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String descrizione;

    @ManyToOne
    @JoinColumn(name = "itinerario_id", nullable = false)
    private Itinerario itinerario;

    public Attivita(String titolo, String descrizione) {
        this.titolo = titolo;
        this.descrizione = descrizione;
    }

}
