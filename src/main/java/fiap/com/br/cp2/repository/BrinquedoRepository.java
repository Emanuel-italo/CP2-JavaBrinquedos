package fiap.com.br.cp2.repository;

import fiap.com.br.cp2.entity.Brinquedo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BrinquedoRepository extends JpaRepository<Brinquedo, Long> {
}
