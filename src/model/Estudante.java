import java.time.LocalDate;

public class Estudante {
    private int idEstudante;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private String email;

    public Estudante() {}

    public Estudante(int idEstudante, String nome, LocalDate dataNascimento, String cpf, String email) {
        this.idEstudante = idEstudante;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.email = email;
    }

    public int getIdEstudante() {
        return idEstudante;
    }

    public void setIdEstudante(int idEstudante) {
        this.idEstudante = idEstudante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

            @Override
    public String toString() {
        return "Estudante [id=" + idEstudante 
        + ", nome=" + nome 
        + ", dataNascimento=" + dataNascimento 
        + "cpf=" + cpf 
        + "e-mail=" + email +"]";
    }
}
