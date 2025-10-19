package conexion;
import java.sql.Connection;

public class TestaConexaoLocal {
    public static void main(String[] args) throws Exception {
    try (Connection c = ConexaoLocal.getConnection()) {
      System.out.println(c != null ? "OK: conectou no MySQL LOCAL!" : "Falhou");
    }
  }
}
