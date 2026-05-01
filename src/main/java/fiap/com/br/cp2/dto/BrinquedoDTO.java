package fiap.com.br.cp2.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO de brinquedo implementado como <strong>Java Record</strong> (Java 17).
 *
 * <p>Records são imutáveis por padrão: Jackson usa o construtor canônico
 * para desserialização e os accessors ({@code nome()}, {@code tipo()}, …)
 * para serialização, produzindo JSON idêntico ao exigido pelo CP2.
 *
 * <h3>Validações (Bean Validation – Jakarta)</h3>
 * <table>
 *   <tr><th>Campo</th><th>Regras</th></tr>
 *   <tr><td>nome</td>          <td>@NotBlank, @Size(2‑100)</td></tr>
 *   <tr><td>tipo</td>          <td>@NotBlank, @Size(2‑50)</td></tr>
 *   <tr><td>classificacao</td> <td>@NotNull, @Min(0), @Max(14)</td></tr>
 *   <tr><td>tamanho</td>       <td>@NotBlank, @Size(2‑20)</td></tr>
 *   <tr><td>preco</td>         <td>@NotNull, @DecimalMin("0.01")</td></tr>
 * </table>
 */
public record BrinquedoDTO(

        /** Preenchido nas respostas; ignorado / opcional nas requisições de criação. */
        Long id,

        @NotBlank(message = "O nome do brinquedo é obrigatório.")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
        String nome,

        @NotBlank(message = "O tipo do brinquedo é obrigatório.")
        @Size(min = 2, max = 50, message = "O tipo deve ter entre 2 e 50 caracteres.")
        String tipo,

        @NotNull(message = "A classificação do brinquedo é obrigatória.")
        @Min(value = 0,  message = "A classificação mínima é de 0 anos.")
        @Max(value = 14, message = "A classificação máxima é de 14 anos (infantil).")
        Integer classificacao,

        @NotBlank(message = "O tamanho do brinquedo é obrigatório.")
        @Size(min = 2, max = 20, message = "O tamanho deve ter entre 2 e 20 caracteres.")
        String tamanho,

        @NotNull(message = "O preço é obrigatório.")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
        BigDecimal preco

) {

    /* ------------------------------------------------------------------ */
    /* Factory – converte entidade → DTO (usado no Service)                */
    /* ------------------------------------------------------------------ */

    /**
     * Cria um {@code BrinquedoDTO} a partir da entidade JPA.
     * Centraliza o mapeamento no próprio tipo, evitando métodos toDTO/toEntity
     * espalhados pelo Service.
     *
     * @param b entidade persistida
     * @return DTO pronto para serialização JSON
     */
    public static BrinquedoDTO de(fiap.com.br.cp2.entity.Brinquedo b) {
        return new BrinquedoDTO(
                b.getId(),
                b.getNome(),
                b.getTipo(),
                b.getClassificacao(),
                b.getTamanho(),
                b.getPreco()
        );
    }
}