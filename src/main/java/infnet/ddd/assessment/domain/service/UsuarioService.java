package infnet.ddd.assessment.domain.service;

import infnet.ddd.assessment.api.rest.dto.request.UsuarioRequestDTO;
import infnet.ddd.assessment.api.rest.dto.response.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponseDTO> findAll();
    UsuarioResponseDTO findById(Long id);
    UsuarioResponseDTO create(UsuarioRequestDTO usuarioRequestDTO) throws Exception;
    UsuarioResponseDTO alterarPlano(Long id, Long planoId) throws Exception;

}
