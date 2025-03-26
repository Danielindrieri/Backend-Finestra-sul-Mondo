package BackEndFinestraSulMondo.Itinerary;


import BackEndFinestraSulMondo.Itinerary.Attivita.Attivita;
import BackEndFinestraSulMondo.Review.Recensioni;
import BackEndFinestraSulMondo.Itinerary.Ristoranti.Ristoranti;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Itinerario {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String descrizione;

    @Column(nullable = false)
    private String città;

    @ElementCollection
    private List<String> immagini = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "itinerario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attivita> coseDaFare = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "itinerario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ristoranti> doveMangiare = new ArrayList<>();

    @OneToMany(mappedBy = "itinerario",cascade = CascadeType.ALL)
    private List<Recensioni> recensioni = new ArrayList<>();


}
