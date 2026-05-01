import enums.TypePet;

import java.text.Normalizer;
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

        if (pets.isEmpty()) {
            System.out.println("Nenhum pet encontrado");
            return;
        }

        TypePet tipo = null;

        while (tipo == null) {
            try {
                System.out.println("Selecione um tipo de pet para buscar");
                for (TypePet tipoPet : TypePet.values()) {
                    System.out.println("Tipo: " + tipoPet.name());
                }

                tipo = TypePet.valueOf(sc.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo Inválido");
            }
        }

        Menu.menuBuscaPets();

        //qntd de criterios
        System.out.print("Quantos critérios deseja usar? (1 ou 2): ");
        int qtd = sc.nextInt();
        sc.nextLine();

        //1 criterio
        System.out.print("Escolha o primeiro critério: ");
        int op1 = sc.nextInt();
        sc.nextLine();

        //1 criterio escolhendo
        System.out.print("Digite o valor: ");
        String val1 = normalizar(sc.nextLine());

        //2 criterio
        int op2 = -1;
        String val2 = "";

        if(qtd == 2){
            System.out.print("Escolha o segundo critério: ");
            op2 = sc.nextInt();
            sc.nextLine();

            System.out.print("Digite o valor: ");
            val2 = normalizar(sc.nextLine());
        }

    }

    private String normalizar(String texto) {
        texto = texto.toLowerCase();

        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    private String formatarPet(Pet p) {
        return p.getNomeCompleto() + " - " +
                p.getTipo() + " - " +
                p.getSexo() + " - " +
                p.getEndereco().getStreet() + ", " +
                p.getEndereco().getNum() + " - " +
                p.getEndereco().getCity() + " - " +
                p.getIdade() + " anos - " +
                p.getPeso() + "kg - " +
                p.getRaca();
    }
}
