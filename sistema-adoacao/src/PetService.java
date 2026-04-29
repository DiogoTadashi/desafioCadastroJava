import enums.SexPet;
import enums.TypePet;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PetService {
    private final Scanner sc;
    private PetRepository petRepository;

    public PetService(Scanner sc, PetRepository petRepository) {
        this.sc = sc;
        this.petRepository = petRepository;
    }

    public void cadastrarPet(List<String> perguntas) {
        Pet pet = new Pet();

        try {
            System.out.println(perguntas.getFirst());
            String nomeCompleto = sc.nextLine().trim();

            if (nomeCompleto.isBlank()) {
                throw new CampoExceptions("Nome e sobrenome são obrigatórios!");
            }
            PetValidator.validarNome(nomeCompleto);

            String[] partes = nomeCompleto.trim().split("\\s+");
            if (partes.length < 2) {
                throw new CampoExceptions("Informe nome e sobrenome!");
            }

            pet.setNome(partes[0]);
            String sobrenome = String.join(" ", Arrays.copyOfRange(partes, 1, partes.length));
            pet.setSobrenome(sobrenome);

            // TIPO
            pet.setTipo(lerTipo(perguntas.get(1)));

            // SEXO
            pet.setSexo(lerSexo(perguntas.get(2)));

            // ENDEREÇO
            System.out.println(perguntas.get(3));
            Address endereco = new Address();

            System.out.println("Número:");
            String num = sc.nextLine();
            endereco.setNum(PetValidator.validarOpcionalTexto(num, "numero"));

            System.out.println("Cidade:");
            endereco.setCity(PetValidator.validarObrigatorio(sc.nextLine(), "cidade"));

            System.out.println("Rua:");
            endereco.setStreet(PetValidator.validarObrigatorio(sc.nextLine(), "rua"));

            pet.setEndereco(endereco);

            // IDADE
            System.out.println(perguntas.get(4));
            pet.setIdade(PetValidator.validarIdade(sc.nextLine()));

            // PESO
            System.out.println(perguntas.get(5));
            pet.setPeso(PetValidator.validarPeso(sc.nextLine()));

            // RAÇA
            System.out.println(perguntas.get(6));
            String raca = PetValidator.validarOpcionalTexto(sc.nextLine(), "raça");
            PetValidator.validarRaca(raca);
            pet.setRaca(raca);

            System.out.println("\nPet cadastrado com sucesso!");
            petRepository.getPets().add(pet);
            petRepository.criarObjetoPet(pet);
            petRepository.salvarPetArquivo(pet);

        } catch (CampoExceptions e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private TypePet lerTipo(String pergunta) {
        TypePet tipo = null;

        while (tipo == null) {
            try {
                System.out.println(pergunta);
                for (TypePet t : TypePet.values()) {
                    System.out.println("- " + t);
                }

                tipo = TypePet.valueOf(sc.nextLine().toUpperCase());

            } catch (IllegalArgumentException e) {
                System.out.println("Tipo inválido!");
            }
        }

        return tipo;
    }

    private SexPet lerSexo(String pergunta) {
        SexPet sexo = null;

        while (sexo == null) {
            try {
                System.out.println(pergunta);
                for (SexPet s : SexPet.values()) {
                    System.out.println("- " + s);
                }

                sexo = SexPet.valueOf(sc.nextLine().toUpperCase());

            } catch (IllegalArgumentException e) {
                System.out.println("Sexo inválido!");
            }
        }

        return sexo;
    }
}
