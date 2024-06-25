package infnet.ddd.assessment.api.rest.controller;

import infnet.ddd.assessment.api.rest.dto.response.PlaylistResponseDTO;
import infnet.ddd.assessment.domain.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<PlaylistResponseDTO> findByUsuarioId(@PathVariable(value = "id") Long id) {
        return playlistService.findByUsuario(id);
    }

    @PostMapping("/{id}/favoritorar_musica/{musicaId}")
    @ResponseStatus(HttpStatus.OK)
    public PlaylistResponseDTO favoritarMusica(@PathVariable(value = "id") Long id,
                                               @PathVariable(value = "musicaId") Long musicaId) {
        return playlistService.salvarMusicaFavorita(id, musicaId);
    }

    @PostMapping("/{id}/desfavoritorar_musica/{musicaId}")
    @ResponseStatus(HttpStatus.OK)
    public PlaylistResponseDTO desfavoritarMusica(@PathVariable(value = "id") Long id,
                                                  @PathVariable(value = "musicaId") Long musicaId) {
        return playlistService.removerMusicaFavorita(id, musicaId);
    }
}
