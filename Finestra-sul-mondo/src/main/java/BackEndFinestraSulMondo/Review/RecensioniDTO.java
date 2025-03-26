package BackEndFinestraSulMondo.Review;

import jakarta.validation.constraints.NotBlank;

public class RecensioniDTO {

    @NotBlank(message = "Recensione non può essere vuota")
    private String recensione;

    @NotBlank(message = "bisogna dare un voto da 1 a 5")
    private double voto;
}
