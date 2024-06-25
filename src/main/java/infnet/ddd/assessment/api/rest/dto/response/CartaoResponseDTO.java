package infnet.ddd.assessment.api.rest.dto.response;

import infnet.ddd.assessment.domain.entity.Cartao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoResponseDTO {

    private Long id;
    private String numero;
    private Boolean ativo;
    private LocalDateTime validade;
    private double limite;
    private List<TransacaoResponseDTO> transacoes;

    public Cartao toEntity() {
        return Cartao.builder()
                .id(this.id)
                .numero(this.numero)
                .ativo(this.ativo)
                .validade(this.validade)
                .limite(this.limite)
                .transacoes(this.transacoes.stream().map(TransacaoResponseDTO::toEntity).toList())
                .build();
    }

}
