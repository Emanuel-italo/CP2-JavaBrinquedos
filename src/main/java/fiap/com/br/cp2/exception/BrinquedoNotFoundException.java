package fiap.com.br.cp2.exception;

/**
 * Exceção lançada quando um Brinquedo não é encontrado no banco de dados.
 * Mapeada para HTTP 404 Not Found pelo GlobalExceptionHandler.
 */
public class BrinquedoNotFoundException extends RuntimeException {

    /**
     * Construtor que recebe o ID não encontrado.
     *
     * @param id ID do brinquedo que não foi encontrado
     */
    public BrinquedoNotFoundException(Long id) {
        super("Brinquedo não encontrado com ID: " + id);
    }

    /**
     * Construtor que recebe uma mensagem personalizada.
     *
     * @param message mensagem descritiva do erro
     */
    public BrinquedoNotFoundException(String message) {
        super(message);
    }
}
