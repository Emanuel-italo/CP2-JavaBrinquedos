package fiap.com.br.cp2.controller;

import fiap.com.br.cp2.dto.BrinquedoDTO;
import fiap.com.br.cp2.service.BrinquedoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para o gerenciamento de Brinquedos.
 *
 * Base URL: http://localhost:8080/brinquedos
 *
 * Endpoints disponíveis:
 *   GET    /brinquedos          → listar todos (200 OK)
 *   GET    /brinquedos/{id}     → buscar por ID (200 OK / 404 Not Found)
 *   POST   /brinquedos          → criar novo (201 Created / 400 Bad Request)
 *   PUT    /brinquedos/{id}     → atualizar (200 OK / 404 Not Found / 400 Bad Request)
 *   DELETE /brinquedos/{id}     → excluir (204 No Content / 404 Not Found)
 */
@RestController
@RequestMapping("/brinquedos")
public class BrinquedoController {

    private final BrinquedoService brinquedoService;

    public BrinquedoController(BrinquedoService brinquedoService) {
        this.brinquedoService = brinquedoService;
    }

    // -------------------------------------------------------------------------
    // GET /brinquedos — Listar todos os brinquedos
    // -------------------------------------------------------------------------

    /**
     * Lista todos os brinquedos cadastrados no banco de dados Oracle.
     *
     * Teste no Postman/Insomnia:
     *   GET http://localhost:8080/brinquedos
     *
     * @return 200 OK com lista de brinquedos em JSON
     */
    @GetMapping
    public ResponseEntity<List<BrinquedoDTO>> listarTodos() {
        List<BrinquedoDTO> brinquedos = brinquedoService.listarTodos();
        return ResponseEntity.ok(brinquedos); // HTTP 200 OK
    }

    // -------------------------------------------------------------------------
    // GET /brinquedos/{id} — Buscar brinquedo por ID
    // -------------------------------------------------------------------------

    /**
     * Busca um brinquedo específico pelo ID no banco de dados Oracle.
     *
     * Teste no Postman/Insomnia:
     *   GET http://localhost:8080/brinquedos/1
     *
     * @param id ID do brinquedo (path variable)
     * @return 200 OK com o brinquedo em JSON, ou 404 Not Found se não existir
     */
    @GetMapping("/{id}")
    public ResponseEntity<BrinquedoDTO> buscarPorId(@PathVariable Long id) {
        BrinquedoDTO brinquedo = brinquedoService.buscarPorId(id);
        return ResponseEntity.ok(brinquedo); // HTTP 200 OK
    }

    // -------------------------------------------------------------------------
    // POST /brinquedos — Criar novo brinquedo
    // -------------------------------------------------------------------------

    /**
     * Cria um novo brinquedo e persiste no banco de dados Oracle.
     * O body deve ser um JSON válido com todos os campos obrigatórios.
     *
     * Teste no Postman/Insomnia:
     *   POST http://localhost:8080/brinquedos
     *   Content-Type: application/json
     *   Body:
     *   {
     *     "nome": "Hot Wheels",
     *     "tipo": "Carrinho",
     *     "classificacao": 10,
     *     "tamanho": "Pequeno",
     *     "preco": 29.90
     *   }
     *
     * @param dto dados do brinquedo a ser criado (validado pelo Bean Validation)
     * @return 201 Created com o brinquedo criado em JSON
     */
    @PostMapping
    public ResponseEntity<BrinquedoDTO> criar(@Valid @RequestBody BrinquedoDTO dto) {
        BrinquedoDTO criado = brinquedoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado); // HTTP 201 Created
    }

    // -------------------------------------------------------------------------
    // PUT /brinquedos/{id} — Atualizar brinquedo existente
    // -------------------------------------------------------------------------

    /**
     * Atualiza os dados de um brinquedo existente no banco de dados Oracle.
     *
     * Teste no Postman/Insomnia:
     *   PUT http://localhost:8080/brinquedos/1
     *   Content-Type: application/json
     *   Body:
     *   {
     *     "nome": "Hot Wheels Edição Especial",
     *     "tipo": "Carrinho",
     *     "classificacao": 8,
     *     "tamanho": "Pequeno",
     *     "preco": 49.90
     *   }
     *
     * @param id  ID do brinquedo a ser atualizado (path variable)
     * @param dto novos dados do brinquedo (validado pelo Bean Validation)
     * @return 200 OK com o brinquedo atualizado em JSON, ou 404 Not Found se não existir
     */
    @PutMapping("/{id}")
    public ResponseEntity<BrinquedoDTO> atualizar(@PathVariable Long id,
                                                   @Valid @RequestBody BrinquedoDTO dto) {
        BrinquedoDTO atualizado = brinquedoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado); // HTTP 200 OK
    }

    // -------------------------------------------------------------------------
    // DELETE /brinquedos/{id} — Excluir brinquedo
    // -------------------------------------------------------------------------

    /**
     * Exclui um brinquedo do banco de dados Oracle pelo ID.
     *
     * Teste no Postman/Insomnia:
     *   DELETE http://localhost:8080/brinquedos/1
     *
     * @param id ID do brinquedo a ser excluído (path variable)
     * @return 204 No Content se excluído com sucesso, ou 404 Not Found se não existir
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        brinquedoService.excluir(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}
