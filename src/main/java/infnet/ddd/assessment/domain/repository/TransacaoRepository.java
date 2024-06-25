package infnet.ddd.assessment.domain.repository;

import infnet.ddd.assessment.domain.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
