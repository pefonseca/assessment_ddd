package infnet.ddd.assessment.domain.repository;

import infnet.ddd.assessment.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
