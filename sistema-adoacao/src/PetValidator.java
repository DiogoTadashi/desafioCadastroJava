public class PetValidator {
    public static String validarObrigatorio(String input, String campo) {
        if (input == null || input.isBlank()) {
            throw new CampoExceptions(campo + " é obrigatório!");
        }
        return input.trim();
    }
    public static String validarOpcionalTexto(String input, String Campo){
        if(input == null || input.isBlank()){
            return Pet.NAO_INFORMADO;
        }
        return input;
    }

    public static String validarNome(String nome) {
        if (!nome.matches("[a-zA-ZÀ-ÿ ]+")) {
            throw new IllegalArgumentException("Nome inválido!");
        }
        return nome;
    }

    public static void validarRaca(String raca) {
        if (raca.equals(Pet.NAO_INFORMADO)) return;

        if (!raca.matches("[a-zA-ZÀ-ÿ ]+")) {
            throw new IllegalArgumentException("Raça inválida!");
        }
    }

    public static Double validarPeso(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }
        try{
            input = input.replace(",", ".");

            double peso = Double.parseDouble(input);
            if (peso < 0.5 || peso > 60) {
                throw new IllegalArgumentException("Peso inválido!");
            }
            return peso;
        }catch(NumberFormatException e){
            throw new CampoExceptions("Peso deve conter apenas números!");
        }
    }

    public static Double validarIdade(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }

        input = input.replace(",", ".");

        try {
            double idade = Double.parseDouble(input);
            if (idade > 20) {
                throw new IllegalArgumentException("Idade inválida!");
            }
            if (idade < 1 && idade > 0) {
                idade = idade / 12.0;
            }
            return idade;
        }catch(NumberFormatException e){
            throw new CampoExceptions("Idade deve conter apenas números!");
        }
    }
}
