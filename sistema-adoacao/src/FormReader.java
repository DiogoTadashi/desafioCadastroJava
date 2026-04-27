import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FormReader {
    public List<String> lerFormulario() {
        List<String> perguntas = new ArrayList<>();

        try (FileReader fr = new FileReader("formulario.txt");
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                perguntas.add(linha);
            }

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return perguntas;
    }
}