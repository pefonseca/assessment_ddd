package infnet.ddd.assessment.domain.repository;

import infnet.ddd.assessment.domain.entity.Banda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandaRepository extends JpaRepository<Banda, Long> {
}
