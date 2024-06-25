package infnet.ddd.assessment.api.rest.dto.response;

import infnet.ddd.assessment.domain.entity.Plano;
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
public class PlanoResponseDTO {

    private Long id;
    private String nome;
    private boolean ativo;
    private String descricao;
    private double valor;
    private List<AssinaturaResponseDTO> assinaturas;

    public Plano toEntity() {
        return Plano.builder()
                .id(this.id)
                .nome(this.nome)
                .ativo(this.ativo)
                .descricao(this.descricao)
                .valor(this.valor)
                .assinaturas(new ArrayList<>()).build();
    }

}
