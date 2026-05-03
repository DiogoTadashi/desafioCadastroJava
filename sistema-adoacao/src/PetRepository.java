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
                if (d.length < 11) continue;

                pet.setId(d[0]);
                pet.setNomeArquivo(d[1]);

                String[] nome = d[2].split(" ");
                pet.setNome(nome[0]);
                pet.setSobrenome(nome.length > 1 ? nome[1] : "");

                pet.setTipo(TypePet.valueOf(d[3]));
                pet.setSexo(SexPet.valueOf(d[4]));

                Address end = new Address();
                end.setStreet(d[5]);
                end.setNum(d[6]);
                end.setCity(d[7]);
                pet.setEndereco(end);

                pet.setIdade(d[8].equals("null") ? null : Double.parseDouble(d[8]));
                pet.setPeso(d[9].equals("null") ? null : Double.parseDouble(d[9]));

                pet.setRaca(d[10]);

                pets.add(pet);
            }

        } catch (IOException e) {
            System.out.println("Nenhum pet salvo ainda.");
        }
    }

    public void criarObjetoPet(Pet pet) {
        String pattern = "yyyyMMdd'T'HHmm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String data = sdf.format(new Date());

        String nomeCompletoParaData = pet.getNomeCompleto().trim().toUpperCase().replaceAll("\\s+", "");

        String nameFile = data + "-" + nomeCompletoParaData + ".txt";

        pet.setNomeArquivo(nameFile);

        File file = new File("sistema-adoacao\\src\\petsCadastrados\\" + nameFile);

        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("1 - " + pet.getNomeCompleto());
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

            String linha = pet.getId() + ";" +
                    pet.getNomeArquivo() + ";" +
                    pet.getNome() + " " + pet.getSobrenome() + ";" +
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

    public void remover(Pet pet) {
        // 1. remove da lista em memória
        pets.remove(pet);
        // 2. remove do arquivo principal
        removerDoArquivo(pet);
        // 3. remove arquivo individual
        removerArquivoIndividual(pet);
    }

    private void removerDoArquivo(Pet petParaRemover) {
        File arquivo = new File(CAMINHO);
        List<String> linhasRestantes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                if (!linha.startsWith(petParaRemover.getId() + ";")) {
                    linhasRestantes.add(linha);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            for (String l : linhasRestantes) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo.");
        }
    }

    private void removerArquivoIndividual(Pet pet) {
        if (pet.getNomeArquivo() == null) return;

        File arquivo = new File("sistema-adoacao\\src\\petsCadastrados\\" + pet.getNomeArquivo());

        if (arquivo.exists()) {
            if (arquivo.delete()) {
                System.out.println("Arquivo " + pet.getNomeArquivo() + " removido.");
            } else {
                System.out.println("Não foi possível remover o arquivo individual.");
            }
        }
    }

    public List<Pet> getPets() {
        return pets;
    }
}