package infnet.ddd.assessment.domain.entity;

import infnet.ddd.assessment.api.rest.dto.request.TransacaoRequestDTO;
import infnet.ddd.assessment.api.rest.dto.response.TransacaoResponseDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "tb_transicao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoAutorizacao;
    private double valor;
    private LocalDateTime dtTransacao;
    private String merchant;
    private String descricao;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    public TransacaoResponseDTO toDto() {
        return TransacaoResponseDTO.builder()
                                    .codigoAutorizacao(this.codigoAutorizacao)
                                    .valor(this.valor)
                                    .dtTransacao(this.dtTransacao)
                                    .merchant(this.merchant)
                                    .descricao(this.descricao)
                                    .build();
    }

    public TransacaoRequestDTO toDtoRequest() {
        return TransacaoRequestDTO.builder()
                                  .valor(this.valor)
                                  .dtTransacao(this.dtTransacao)
                                  .merchant(this.merchant)
                                  .descricao(this.descricao)
                                  .build();
    }
}
