package infnet.ddd.assessment.api.rest.dto.response;

import infnet.ddd.assessment.domain.entity.Musica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicaResponseDTO {

    private Long id;
    private String nome;
    private int duracao;
    private BandaResponseDTO bandaResponseDTO;

    public Musica toEntity() {
        return Musica.builder()
                .id(this.getId())
                .nome(this.getNome())
                .duracao(this.duracao)
                .banda(bandaResponseDTO.toEntity())
                .build();
    }
}
