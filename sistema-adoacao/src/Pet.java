import enums.SexPet;
import enums.TypePet;

public class Pet {
    public static final String NAO_INFORMADO = "NÃO INFORMADO";
    private String nome;
    private String sobrenome;

    private TypePet tipo;
    private SexPet sexo;
    private Address endereco;
    private Double idade;
    private Double peso;
    private String raca;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public TypePet getTipo() {
        return tipo;
    }

    public void setTipo(TypePet tipo) {
        this.tipo = tipo;
    }

    public SexPet getSexo() {
        return sexo;
    }

    public void setSexo(SexPet sexo) {
        this.sexo = sexo;
    }

    public Address getEndereco() {
        return endereco;
    }

    public void setEndereco(Address endereco) {
        this.endereco = endereco;
    }

    public Double getIdade() {
        return idade;
    }

    public void setIdade(Double idade) {
        this.idade = idade;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}
