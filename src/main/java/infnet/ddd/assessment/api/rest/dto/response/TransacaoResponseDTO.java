package infnet.ddd.assessment.api.rest.dto.response;

import infnet.ddd.assessment.domain.entity.Transacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoResponseDTO {

    private Long codigoAutorizacao;
    private double valor;
    private LocalDateTime dtTransacao;
    private String merchant;
    private String descricao;

    public Transacao toEntity() {
        return Transacao.builder()
                        .codigoAutorizacao(this.codigoAutorizacao)
                        .valor(this.valor)
                        .dtTransacao(this.dtTransacao)
                        .merchant(this.merchant)
                        .descricao(this.descricao)
                        .build();
    }

}
