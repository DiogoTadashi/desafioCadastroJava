import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PetRepository {
    private final List<Pet> pets = new ArrayList<>();
    private Pet pet;
    String nomeCompleto = pet.getNomeCompleto();;

    public void carregar() {

    }

    public void criarObjetoPet(Pet pet) {
        String pattern = "yyyyMMdd'T'HHmm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String data = sdf.format(new Date());

        String nomeCompletoParaData = nomeCompleto.trim().toUpperCase().replaceAll("\\s+", "");

        String nameFile = data + "-" + nomeCompletoParaData;

        File file = new File("sistema-adoacao\\src\\petsCadastrados\\" + nameFile + ".txt");

        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("1 - " + nomeCompleto);
            bw.newLine();
            bw.write("2 - " + pet.getTipo().toString());
            bw.newLine();
            bw.write("3 - " + pet.getSexo().toString());
            bw.newLine();
            bw.write("4 - " + pet.getEndereco().toString());
            bw.newLine();
            bw.write("5 - " + pet.getIdade() + " anos");
            bw.newLine();
            bw.write("6 - " + pet.getPeso() + " kg");
            bw.newLine();
            bw.write("7 - " + pet.getRaca());
            bw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void salvarPetArquivo(Pet pet) {
        String CAMINHO = "sistema-adoacao\\src\\petsCadastrados\\pets.txt";
        try (FileWriter fw = new FileWriter(CAMINHO, true)) {

            String linha = pet.getNome() + " " + pet.getSobrenome() + ";" +
                    pet.getTipo() + ";" +
                    pet.getSexo() + ";" +
                    pet.getEndereco().getStreet() + ";" +
                    pet.getEndereco().getNum() + ";" +
                    pet.getEndereco().getCity() + ";" +
                    pet.getIdade() + ";" +
                    pet.getPeso() + ";" +
                    pet.getRaca();

            fw.write(linha + "\n");

        } catch (IOException e) {
            System.out.println("Erro ao salvar pet.");
        }
    }

    public List<Pet> getPets() {
        return pets;
    }
}