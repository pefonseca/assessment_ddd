package infnet.ddd.assessment.domain.service;

import infnet.ddd.assessment.api.rest.dto.request.CartaoRequestDTO;
import infnet.ddd.assessment.api.rest.dto.response.CartaoResponseDTO;

public interface CartaoService {

    CartaoResponseDTO create(CartaoRequestDTO cartaoRequestDTO);

}
