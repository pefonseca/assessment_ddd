package infnet.ddd.assessment.api.rest.dto.response;

import infnet.ddd.assessment.domain.entity.Banda;
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
public class BandaResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private List<MusicaResponseDTO> musicas;

    public Banda toEntity() {
        return Banda.builder()
                    .id(this.getId())
                    .nome(this.getNome())
                    .descricao(this.getDescricao())
                    .musicas(new ArrayList<>())
                    .build();
    }

}
