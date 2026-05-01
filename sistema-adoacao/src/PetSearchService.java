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


        //qntd de criterios
        System.out.print("Quantos critérios deseja usar? (1 ou 2): ");
        int qtd = sc.nextInt();
        sc.nextLine();

        Menu.menuBuscaPets();
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

            if(op2 == op1){
                System.out.println("Não pode repetir o mesmo critério!");
                return;
            }

            System.out.print("Digite o valor: ");
            val2 = normalizar(sc.nextLine());
        }

        int i = 1;

        for (Pet p : pets) {

            if (p.getTipo() != tipo) continue;

            boolean match1 = verificar(p, op1, val1);
            boolean match2 = true;

            if (qtd == 2) {
                match2 = verificar(p, op2, val2);
            }

            if (match1 && match2) {
                System.out.println(i++ + ". " + formatarPet(p));
            }
        }

        if (i == 1) {
            System.out.println("Nenhum pet foi encontrado!");
        }
    }

    private boolean verificar(Pet p, int opcao, String valor) {

        switch (opcao) {

            case 1: // nome
                return normalizar(p.getNomeCompleto()).contains(valor);

            case 2: // sexo
                return p.getSexo().name().equalsIgnoreCase(valor);

            case 3: // idade
                return p.getIdade() != null &&
                        String.valueOf(p.getIdade()).contains(valor);

            case 4: // peso
                return p.getPeso() != null &&
                        String.valueOf(p.getPeso()).contains(valor);

            case 5: // raça
                return normalizar(p.getRaca()).contains(valor);

            case 6: // endereço
                return normalizar(p.getEndereco().toString()).contains(valor);

            default:
                return false;
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
