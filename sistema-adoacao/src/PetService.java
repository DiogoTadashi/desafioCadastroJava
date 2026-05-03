import enums.SexPet;
import enums.TypePet;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PetService {
    private final Scanner sc;
    private final PetRepository petRepository;
    private final PetSearchService petSearchService;

    public PetService(Scanner sc, PetRepository petRepository, PetSearchService petSearchService) {
        this.sc = sc;
        this.petRepository = petRepository;
        this.petSearchService = petSearchService;
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

            pet.setId(UUID.randomUUID().toString());

            System.out.println("\nPet cadastrado com sucesso!");
            petRepository.getPets().add(pet);
            petRepository.criarObjetoPet(pet);
            petRepository.salvarPetArquivo(pet);

        } catch (CampoExceptions e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void alterarPet(){
        List<Pet> resultados = petSearchService.buscarPets();

        if (resultados.isEmpty()) return;

        int escolha;

        while (true) {
            System.out.print("Escolha o número do pet (Digite 0 para cancelar): ");
            escolha = sc.nextInt();
            sc.nextLine();

            if (escolha == 0) {
                System.out.println("Operação cancelada.");
                return;
            }

            if (escolha >= 1 && escolha <= resultados.size()) {
                break;
            }

            System.out.println("Opção inválida! Tente novamente.");
        }

        Pet pet = resultados.get(escolha - 1);

        System.out.println("Alterando: " + pet.getNomeCompleto());

        int opcao;

        do {
            System.out.println("\nO que deseja alterar?");
            System.out.println("1 - Nome");
            System.out.println("2 - Endereço");
            System.out.println("3 - Idade");
            System.out.println("4 - Peso");
            System.out.println("5 - Raça");
            System.out.println("0 - Sair");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> alterarNome(pet);
                case 2 -> alterarEndereco(pet);
                case 3 -> alterarIdade(pet);
                case 4 -> alterarPeso(pet);
                case 5 -> alterarRaca(pet);
                case 0 -> System.out.println("Saindo da edição...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    public void deletarPet() {
        List<Pet> resultados = petSearchService.buscarPets();

        if (resultados.isEmpty()) return;

        int escolha;

        while (true) {
            System.out.print("Escolha o número do pet para deletar (0 cancelar): ");
            try {
                escolha = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
                continue;
            }

            if (escolha == 0) {
                System.out.println("Operação cancelada.");
                return;
            }

            if (escolha < 1 || escolha > resultados.size()) {
                System.out.println("Número inválido! Tente novamente.");
                continue; // volta para pedir o número de novo
            }

            break;
        }

        Pet pet = resultados.get(escolha - 1);

        System.out.println("Deletando: " + pet.getNomeCompleto());

        System.out.print("Confirmar exclusão (SIM/NÃO): ");
        String confirmacao = sc.nextLine().trim().toUpperCase();

        if (!confirmacao.equals("SIM")) {
            System.out.println("Exclusão cancelada.");
            return;
        }
        //remove da memória e do arquivo e arquivo individual
        petRepository.remover(pet);
        System.out.println("Pet deletado com sucesso!");
    }

    private void alterarNome(Pet pet) {
        System.out.print("Novo nome: ");
        String input = sc.nextLine().trim();

        if (!input.isBlank()) {
            try {
                PetValidator.validarNome(input);

                String[] partes = input.split("\\s+");
                if (partes.length < 2) {
                    System.out.println("Informe nome e sobrenome!");
                    return;
                }

                pet.setNome(partes[0]);
                String sobrenome = String.join(" ", Arrays.copyOfRange(partes, 1, partes.length));
                pet.setSobrenome(sobrenome);

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private void alterarPeso(Pet pet) {
        System.out.print("Novo peso: ");
        String input = sc.nextLine();
        if (!input.isBlank()) {
            pet.setPeso(PetValidator.validarPeso(input));
        }
    }

    private void alterarIdade(Pet pet) {
        System.out.print("Novo idade: ");
        String input = sc.nextLine();
        if (!input.isBlank()) {
            pet.setIdade(PetValidator.validarIdade(input));
        }
    }

    private void alterarEndereco(Pet pet) {
        Address endereco = pet.getEndereco();

        System.out.println("Deixe em branco para manter o valor atual.");

        System.out.print("Número (" + endereco.getNum() + "): ");
        String num = sc.nextLine();
        if (!num.isBlank()) {
            endereco.setNum(PetValidator.validarOpcionalTexto(num, "numero"));
        }

        System.out.print("Cidade (" + endereco.getCity() + "): ");
        String cidade = sc.nextLine();
        if (!cidade.isBlank()) {
            endereco.setCity(PetValidator.validarObrigatorio(cidade, "cidade"));
        }

        System.out.print("Rua (" + endereco.getStreet() + "): ");
        String rua = sc.nextLine();
        if (!rua.isBlank()) {
            endereco.setStreet(PetValidator.validarObrigatorio(rua, "rua"));
        }
    }

    private void alterarRaca(Pet pet) {
        System.out.print("Novo raça: ");
        String input = sc.nextLine();

        if (!input.isBlank()) {
            pet.setRaca(PetValidator.validarRaca(input));
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
