public class Curso {
    private int idCurso;
    private String nomeCurso;
    private int cargaHoraria;

    public Curso() {}

    public Curso(int idCurso, String nomeCurso, int cargaHoraria) {
        this.idCurso = idCurso;
        this.nomeCurso = nomeCurso;
        this.cargaHoraria = cargaHoraria;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

        @Override
    public String toString() {
        return "Curso [id=" + idCurso 
        + ", nome=" + nomeCurso 
        + ", cargaHoraria=" + cargaHoraria + "]";
    }
}


