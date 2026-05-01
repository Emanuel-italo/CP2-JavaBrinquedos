package fiap.com.br.cp2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler global de exceções da API REST.
 *
 * Trata os seguintes casos:
 *   - BrinquedoNotFoundException → HTTP 404 Not Found
 *   - MethodArgumentNotValidException → HTTP 400 Bad Request (Bean Validation)
 *   - Exception (genérico) → HTTP 500 Internal Server Error
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 404 — Brinquedo não encontrado.
     */
    @ExceptionHandler(BrinquedoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBrinquedoNotFound(BrinquedoNotFoundException ex) {
        Map<String, Object> erro = new HashMap<>();
        erro.put("timestamp", LocalDateTime.now().toString());
        erro.put("status", 404);
        erro.put("erro", "Não Encontrado");
        erro.put("mensagem", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    /**
     * 400 — Erros de validação dos campos do DTO (Bean Validation).
     * Retorna o mapa de campos inválidos com as respectivas mensagens.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> camposInvalidos = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            camposInvalidos.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        Map<String, Object> erro = new HashMap<>();
        erro.put("timestamp", LocalDateTime.now().toString());
        erro.put("status", 400);
        erro.put("erro", "Dados Inválidos");
        erro.put("mensagem", "Verifique os campos obrigatórios.");
        erro.put("campos", camposInvalidos);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    /**
     * 500 — Erro interno inesperado.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericError(Exception ex) {
        Map<String, Object> erro = new HashMap<>();
        erro.put("timestamp", LocalDateTime.now().toString());
        erro.put("status", 500);
        erro.put("erro", "Erro Interno");
        erro.put("mensagem", "Ocorreu um erro inesperado. Tente novamente.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
