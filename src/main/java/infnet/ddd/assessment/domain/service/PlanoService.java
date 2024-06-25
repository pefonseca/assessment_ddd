package infnet.ddd.assessment.domain.service;

import infnet.ddd.assessment.api.rest.dto.response.PlanoResponseDTO;

import java.util.List;

public interface PlanoService {

    List<PlanoResponseDTO> findAll();
    PlanoResponseDTO findById(Long id);

}
