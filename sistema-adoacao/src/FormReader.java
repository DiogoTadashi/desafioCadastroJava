import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FormReader {
    public void showForm() {
        try (FileReader fr = new FileReader("formulario.txt");
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while((linha = br.readLine()) != null){
                System.out.println(linha);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo formulario.txt não encontrado.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
