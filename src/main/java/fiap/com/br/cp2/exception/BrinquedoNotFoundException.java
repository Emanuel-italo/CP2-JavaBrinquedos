package fiap.com.br.cp2.exception;

public class BrinquedoNotFoundException extends RuntimeException {


    public BrinquedoNotFoundException(Long id) {
        super("Brinquedo não encontrado com ID: " + id);
    }


    public BrinquedoNotFoundException(String message) {
        super(message);
    }
}
