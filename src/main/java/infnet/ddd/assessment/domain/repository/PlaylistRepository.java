package infnet.ddd.assessment.domain.repository;

import infnet.ddd.assessment.domain.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByUsuarioId(Long id);

}
