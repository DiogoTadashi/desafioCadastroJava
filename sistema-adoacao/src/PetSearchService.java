import java.util.List;
import java.util.Scanner;

public class PetSearchService {
    private final Scanner sc;
    private Menu menu;
    private static final String PASTA = "petsCadastrados";
    private List<Pet> pets;

    public PetSearchService(List<Pet> pets, Scanner sc) {
        this.pets = pets;
        this.sc = sc;
    }

    public void buscarPets() {
    }
}
