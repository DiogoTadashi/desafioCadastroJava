import enums.SexPet;
import enums.TypePet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PetService {
    private final Scanner sc;
    private String nomeCompleto;

    public PetService(Scanner sc) {
        this.sc = sc;
    }

    public void cadastrarPet(List<String> perguntas) {
        Pet pet = new Pet();

        try {
            System.out.println(perguntas.get(0));
            String nomeCompleto = sc.nextLine().trim();

            if (nomeCompleto.isBlank()) {
                throw new CampoExceptions("Nome e sobrenome são obrigatórios!");
            }
            PetValidator.validarNome(nomeCompleto);
            this.nomeCompleto = nomeCompleto;

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
            System.out.println(pet);
            criarObjetoPet(pet);

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

    private void criarObjetoPet(Pet pet) {
        String pattern = "yyyyMMdd'T'HHmm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String data = sdf.format(new Date());

        String nomeCompletoParaData = nomeCompleto.trim().toUpperCase().replaceAll("\\s+", "");

        String nameFile = data+"-"+nomeCompletoParaData;
// CORRIGIR AQUI
        File file = new File("src\\petsCadastrados"+ nameFile +".txt");

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(nomeCompleto);
            fw.write(pet.getTipo().toString());
            fw.write(pet.getSexo().toString());
            fw.write(pet.getEndereco().toString());
            fw.write(String.valueOf(pet.getIdade()));
            fw.write(String.valueOf(pet.getPeso()));
            fw.write(pet.getRaca().toString());
            fw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
