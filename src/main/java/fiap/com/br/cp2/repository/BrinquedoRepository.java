package fiap.com.br.cp2.repository;

import fiap.com.br.cp2.entity.Brinquedo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório Spring Data JPA para a entidade Brinquedo.
 *
 * Herda automaticamente os métodos CRUD:
 *   - findAll()       → listar todos
 *   - findById(id)    → buscar por ID
 *   - save(entity)    → criar / atualizar
 *   - deleteById(id)  → excluir por ID
 *   - existsById(id)  → verificar existência
 */
@Repository
public interface BrinquedoRepository extends JpaRepository<Brinquedo, Long> {
}
