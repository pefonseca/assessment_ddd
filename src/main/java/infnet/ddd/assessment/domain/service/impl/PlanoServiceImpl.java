package infnet.ddd.assessment.domain.service.impl;

import infnet.ddd.assessment.api.rest.dto.response.PlanoResponseDTO;
import infnet.ddd.assessment.domain.entity.Cartao;
import infnet.ddd.assessment.domain.entity.Plano;
import infnet.ddd.assessment.domain.entity.Usuario;
import infnet.ddd.assessment.domain.repository.CartaoRepository;
import infnet.ddd.assessment.domain.repository.PlanoRepository;
import infnet.ddd.assessment.domain.repository.UsuarioRepository;
import infnet.ddd.assessment.domain.service.CartaoService;
import infnet.ddd.assessment.domain.service.PlanoService;
import infnet.ddd.assessment.domain.service.TransacaoService;
import infnet.ddd.assessment.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanoServiceImpl implements PlanoService {

    private final PlanoRepository planoRepository;

    public PlanoServiceImpl(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
    }

    @Override
    public List<PlanoResponseDTO> findAll() {
        return planoRepository.findAll().stream().map(Plano::toDto).toList();
    }

    @Override
    public PlanoResponseDTO findById(Long id) {
        return planoRepository.findById(id).orElseThrow().toDto();
    }
}
