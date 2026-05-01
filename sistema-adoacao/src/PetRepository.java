import enums.SexPet;
import enums.TypePet;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PetRepository {
    private final List<Pet> pets = new ArrayList<>();
    private Pet pet;
    String CAMINHO = "sistema-adoacao\\src\\petsCadastrados\\pets.txt";

    public void carregar() {
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO))) {

            String linha;

            while ((linha = br.readLine()) != null) {
                String[] d = linha.split(";");

                Pet p = new Pet();

                String[] nome = d[0].split(" ");
                p.setNome(nome[0]);
                p.setSobrenome(nome.length > 1 ? nome[1] : "");

                p.setTipo(TypePet.valueOf(d[1]));
                p.setSexo(SexPet.valueOf(d[2]));

                Address end = new Address();
                end.setStreet(d[3]);
                end.setNum(d[4]);
                end.setCity(d[5]);
                p.setEndereco(end);

                p.setIdade(d[6].equals("null") ? null : Double.parseDouble(d[6]));
                p.setPeso(d[7].equals("null") ? null : Double.parseDouble(d[7]));

                p.setRaca(d[8]);

                pets.add(p);
            }

        } catch (IOException e) {
            System.out.println("Nenhum pet salvo ainda.");
        }
    }

    public void criarObjetoPet(Pet pet) {
        String nomeCompleto = pet.getNomeCompleto();
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