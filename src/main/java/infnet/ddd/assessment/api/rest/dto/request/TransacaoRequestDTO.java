package infnet.ddd.assessment.api.rest.dto.request;

import infnet.ddd.assessment.domain.entity.Cartao;
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
public class TransacaoRequestDTO {

    private double valor;
    private LocalDateTime dtTransacao;
    private String merchant;
    private String descricao;
    private Cartao cartao;

}
