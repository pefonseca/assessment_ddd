package infnet.ddd.assessment.domain.service.impl;

import infnet.ddd.assessment.api.rest.dto.response.MusicaResponseDTO;
import infnet.ddd.assessment.domain.repository.MusicaRepository;
import infnet.ddd.assessment.domain.service.MusicaService;
import infnet.ddd.assessment.infra.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicaServiceImpl implements MusicaService {

    private final MusicaRepository repository;

    @Override
    public MusicaResponseDTO findById(Long id) {
        return repository.findById(id).map(music ->
                MusicaResponseDTO.builder()
                        .id(music.getId())
                        .nome(music.getNome())
                        .duracao(music.getDuracao())
                        .bandaResponseDTO(music.getBanda().toDto())
                        .build()).orElseThrow(() -> new CustomException("Música not found."));
    }
}
