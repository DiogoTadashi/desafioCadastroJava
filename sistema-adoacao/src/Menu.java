import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private final FormReader reader;
    private final Scanner sc;
    private PetService petService;

    public Menu(FormReader reader, Scanner sc) {
        this.reader = reader;
        this.sc = sc;
        this.petService = new PetService(sc);
    }

    public void run() {
        int opcao = 99;
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Cadastrar um novo pet");
            System.out.println("2 - Alterar os dados do pet cadastrado");
            System.out.println("3 - Deletar um pet cadastrado");
            System.out.println("4 - Listar todos os pets cadastrados");
            System.out.println("5 - Listar pets por algum critério (idade, nome, raça)");
            System.out.println("6 - Sair");
            System.out.print("Escolha o que deseja fazer : ");

            try {
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1 -> petService.cadastrarPet(reader.lerFormulario());
                    case 2 -> petService.cadastrarPet(reader.lerFormulario());
                    case 3 -> petService.cadastrarPet(reader.lerFormulario());
                    case 4 -> petService.cadastrarPet(reader.lerFormulario());
                    case 5 -> petService.cadastrarPet(reader.lerFormulario());

                    case 6 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite apenas números.");
                sc.nextLine();
            }
        } while (opcao != 6);
    }
}
