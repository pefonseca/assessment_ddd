package infnet.ddd.assessment.domain.service.impl;

import infnet.ddd.assessment.api.rest.dto.response.MusicaResponseDTO;
import infnet.ddd.assessment.api.rest.dto.response.PlaylistResponseDTO;
import infnet.ddd.assessment.domain.entity.Musica;
import infnet.ddd.assessment.domain.entity.Playlist;
import infnet.ddd.assessment.domain.repository.PlaylistRepository;
import infnet.ddd.assessment.domain.service.MusicaService;
import infnet.ddd.assessment.domain.service.PlaylistService;
import infnet.ddd.assessment.infra.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final MusicaService musicaService;

    @Override
    public List<PlaylistResponseDTO> findByUsuario(Long id) {
        return playlistRepository.findByUsuarioId(id).stream().map(play ->
                        PlaylistResponseDTO.builder()
                                           .id(play.getId())
                                           .nome(play.getNome())
                                           .musicas(play.getMusicas()
                                                   .stream()
                                                   .map(music ->
                                                           MusicaResponseDTO.builder()
                                                                            .id(music.getId())
                                                                            .nome(music.getNome())
                                                                            .duracao(music.getDuracao())
                                                                            .build())
                                                   .toList())
                                           .build())
                                           .toList();
    }

    @Override
    public PlaylistResponseDTO salvarMusicaFavorita(Long id, Long musicaId) {
        Musica musica = musicaService.findById(musicaId).toEntity();
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new CustomException("Playlist not found."));
        playlist.getMusicas().add(musica);
        playlistRepository.save(playlist);
        return playlist.toDto();
    }

    @Override
    public PlaylistResponseDTO removerMusicaFavorita(Long id, Long musicaId) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new CustomException("Playlist not found."));
        List<Musica> musicasAtualizadas = new ArrayList<>();
        playlist.getMusicas().forEach(m -> {
            if (!m.getId().equals(musicaId)) {
                musicasAtualizadas.add(m);
            }
        });

        playlist.setMusicas(musicasAtualizadas);
        playlistRepository.save(playlist);
        return playlist.toDto();
    }
}
