import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FormReader reader = new FormReader();
        PetRepository petRepository = new PetRepository();

        petRepository.carregar();

        PetSearchService searchService = new PetSearchService(petRepository.getPets(), sc);
        PetService petService = new PetService(sc, petRepository, searchService);

        Menu menu = new Menu(sc, reader, petService, searchService);

        menu.run();

        sc.close();
    }
}