import java.time.LocalDate;

public class Matricula {
    private int idMatricula;
    private LocalDate dataMatricula;
    private String statusMatricula;
    private int idEstudante;
    private int idCurso;

    public Matricula() {}

    public Matricula(int idMatricula, LocalDate dataMatricula, String statusMatricula, int idEstudante, int idCurso) {
        this.idMatricula = idMatricula;
        this.dataMatricula = dataMatricula;
        this.statusMatricula = statusMatricula;
        this.idEstudante = idEstudante;
        this.idCurso = idCurso;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public String getStatusMatricula() {
        return statusMatricula;
    }

    public void setStatusMatricula(String statusMatricula) {
        this.statusMatricula = statusMatricula;
    }

    public int getIdEstudante() {
        return idEstudante;
    }

    public void setIdEstudante(int idEstudante) {
        this.idEstudante = idEstudante;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    @Override
    public String toString() {
        return "Matricula [idMatricula=" + idMatricula 
           + ", dataMatricula=" + dataMatricula 
           + ", statusMatricula=" + statusMatricula 
           + ", idEstudante=" + idEstudante 
           + ", idCurso=" + idCurso + "]";
    }

}
