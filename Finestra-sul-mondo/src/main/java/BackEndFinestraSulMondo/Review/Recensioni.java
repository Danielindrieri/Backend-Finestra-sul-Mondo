package BackEndFinestraSulMondo.Review;

import BackEndFinestraSulMondo.Auth.AppUser;
import BackEndFinestraSulMondo.Itinerary.Itinerario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recensioni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private Double voto;

    @Lob
    @Column(name = "recensioni", nullable = false)
    private String recensione;

    @ManyToOne
    @JoinColumn(name = "appUser_id", nullable = false)
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "itinerario_id", nullable = false)
    private Itinerario itinerario;
}
