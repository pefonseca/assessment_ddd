package infnet.ddd.assessment.domain.service;

import infnet.ddd.assessment.api.rest.dto.response.MusicaResponseDTO;

public interface MusicaService {

    MusicaResponseDTO findById(Long id);

}
