package infnet.ddd.assessment.api.rest.dto.response;

import infnet.ddd.assessment.domain.entity.Playlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistResponseDTO {

    private Long id;
    private String nome;
    private List<MusicaResponseDTO> musicas;

    public Playlist toEntity() {
        return Playlist.builder().id(this.id).nome(this.nome).musicas(new ArrayList<>()).build();
    }

}
