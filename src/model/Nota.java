public class Nota {
    private int idNota;
    private double notaEstudante;
    private String semestre;
    private int idMatricula;

    public Nota() {}

    public Nota(int idNota, double notaEstudante, String semestre, int idMatricula) {
        this.idNota = idNota;
        this.notaEstudante = notaEstudante;
        this.semestre = semestre;
        this.idMatricula = idMatricula;
    }

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public double getNotaEstudante() {
        return notaEstudante;
    }

    public void setNotaEstudante(double notaEstudante) {
        this.notaEstudante = notaEstudante;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    @Override
public String toString() {
    return "Nota [idNota=" + idNota 
           + ", notaEstudante=" + notaEstudante 
           + ", semestre=" + semestre 
           + ", idMatricula=" + idMatricula + "]";
}
}
