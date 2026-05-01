package fiap.com.br.cp2.service;

import fiap.com.br.cp2.dto.BrinquedoDTO;
import fiap.com.br.cp2.entity.Brinquedo;
import fiap.com.br.cp2.exception.BrinquedoNotFoundException;
import fiap.com.br.cp2.repository.BrinquedoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de serviço responsável pelas regras de negócio do CRUD de Brinquedos.
 *
 * Operações disponíveis:
 *   - listarTodos()        → retorna todos os brinquedos do BD
 *   - buscarPorId(id)      → retorna brinquedo pelo ID (404 se não encontrado)
 *   - criar(dto)           → persiste novo brinquedo no BD (Oracle)
 *   - atualizar(id, dto)   → atualiza brinquedo existente no BD (404 se não encontrado)
 *   - excluir(id)          → remove brinquedo do BD pelo ID (404 se não encontrado)
 */
@Service
public class BrinquedoService {

    private final BrinquedoRepository brinquedoRepository;

    public BrinquedoService(BrinquedoRepository brinquedoRepository) {
        this.brinquedoRepository = brinquedoRepository;
    }

    // -------------------------------------------------------------------------
    // READ — Listar todos
    // -------------------------------------------------------------------------

    /**
     * Retorna a lista completa de brinquedos cadastrados no banco de dados Oracle.
     *
     * @return lista de DTOs dos brinquedos
     */
    public List<BrinquedoDTO> listarTodos() {
        return brinquedoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------------------------
    // READ — Buscar por ID
    // -------------------------------------------------------------------------

    /**
     * Busca um brinquedo pelo ID no banco de dados Oracle.
     *
     * @param id ID do brinquedo
     * @return DTO do brinquedo encontrado
     * @throws BrinquedoNotFoundException se o ID não existir no BD (→ HTTP 404)
     */
    public BrinquedoDTO buscarPorId(Long id) {
        Brinquedo brinquedo = brinquedoRepository.findById(id)
                .orElseThrow(() -> new BrinquedoNotFoundException(id));
        return toDTO(brinquedo);
    }

    // -------------------------------------------------------------------------
    // CREATE — Criar novo brinquedo
    // -------------------------------------------------------------------------

    /**
     * Cria e persiste um novo brinquedo no banco de dados Oracle (Commit automático).
     *
     * @param dto dados do brinquedo a ser criado
     * @return DTO do brinquedo criado, com ID gerado pelo banco
     */
    public BrinquedoDTO criar(BrinquedoDTO dto) {
        Brinquedo brinquedo = toEntity(dto);
        Brinquedo salvo = brinquedoRepository.save(brinquedo);
        return toDTO(salvo);
    }

    // -------------------------------------------------------------------------
    // UPDATE — Atualizar brinquedo existente
    // -------------------------------------------------------------------------

    /**
     * Atualiza os dados de um brinquedo existente no banco de dados Oracle.
     *
     * @param id  ID do brinquedo a ser atualizado
     * @param dto novos dados do brinquedo
     * @return DTO do brinquedo atualizado
     * @throws BrinquedoNotFoundException se o ID não existir no BD (→ HTTP 404)
     */
    public BrinquedoDTO atualizar(Long id, BrinquedoDTO dto) {
        Brinquedo existente = brinquedoRepository.findById(id)
                .orElseThrow(() -> new BrinquedoNotFoundException(id));

        existente.setNome(dto.getNome());
        existente.setTipo(dto.getTipo());
        existente.setClassificacao(dto.getClassificacao());
        existente.setTamanho(dto.getTamanho());
        existente.setPreco(dto.getPreco());

        Brinquedo atualizado = brinquedoRepository.save(existente);
        return toDTO(atualizado);
    }

    // -------------------------------------------------------------------------
    // DELETE — Excluir brinquedo pelo ID
    // -------------------------------------------------------------------------

    /**
     * Exclui um brinquedo do banco de dados Oracle pelo ID.
     *
     * @param id ID do brinquedo a ser excluído
     * @throws BrinquedoNotFoundException se o ID não existir no BD (→ HTTP 404)
     */
    public void excluir(Long id) {
        if (!brinquedoRepository.existsById(id)) {
            throw new BrinquedoNotFoundException(id);
        }
        brinquedoRepository.deleteById(id);
    }

    // -------------------------------------------------------------------------
    // Métodos auxiliares de mapeamento Entity ↔ DTO
    // -------------------------------------------------------------------------

    /**
     * Converte uma entidade Brinquedo em BrinquedoDTO.
     */
    private BrinquedoDTO toDTO(Brinquedo brinquedo) {
        return new BrinquedoDTO(
                brinquedo.getId(),
                brinquedo.getNome(),
                brinquedo.getTipo(),
                brinquedo.getClassificacao(),
                brinquedo.getTamanho(),
                brinquedo.getPreco()
        );
    }

    /**
     * Converte um BrinquedoDTO em entidade Brinquedo (sem ID — gerado pelo banco).
     */
    private Brinquedo toEntity(BrinquedoDTO dto) {
        return new Brinquedo(
                null,
                dto.getNome(),
                dto.getTipo(),
                dto.getClassificacao(),
                dto.getTamanho(),
                dto.getPreco()
        );
    }
}
