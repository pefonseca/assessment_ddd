package infnet.ddd.assessment.domain.service;

import infnet.ddd.assessment.api.rest.dto.request.PlaylistRequestDTO;
import infnet.ddd.assessment.api.rest.dto.response.PlaylistResponseDTO;

import java.util.List;

public interface PlaylistService {

    List<PlaylistResponseDTO> findByUsuario(Long id);
    PlaylistResponseDTO salvarMusicaFavorita(Long id, Long musicaId);
    PlaylistResponseDTO removerMusicaFavorita(Long id, Long musicaId);
}
