package infnet.ddd.assessment.domain.repository;

import infnet.ddd.assessment.domain.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
