package infnet.ddd.assessment.domain.entity;

import infnet.ddd.assessment.api.rest.dto.request.CartaoRequestDTO;
import infnet.ddd.assessment.api.rest.dto.response.CartaoResponseDTO;
import infnet.ddd.assessment.api.rest.dto.response.TransacaoResponseDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_cartao")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private Boolean ativo;
    private LocalDateTime validade;
    private double limite;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes = new ArrayList<>();

    private int TRANSACAO_INTERVALO_TEMPO = 2;
    private int TRANSACAO_QUANTIDADE_ALTA_FREQUENCIA = 3;
    private int TRANSACAO_MERCHANT_DUPLICADAS = 2;

    public CartaoResponseDTO toDto() {
        List<TransacaoResponseDTO> transacoesDTO = this.transacoes.stream().map(Transacao::toDto).toList();
        return CartaoResponseDTO.builder()
                                .id(this.id)
                                .numero(this.numero)
                                .ativo(this.ativo)
                                .validade(this.validade)
                                .limite(this.limite)
                                .transacoes(transacoesDTO)
                                .build();
    }

    public Cartao toEntity(CartaoRequestDTO requestDTO) {
        return Cartao.builder()
                    .numero(requestDTO.getNumero())
                    .ativo(requestDTO.getAtivo())
                    .validade(converterStringToLocalDateTime(requestDTO.getValidade()))
                    .limite(requestDTO.getLimite())
                    .build();
    }

    public Cartao toEntityFindCartao() {
        return Cartao.builder()
                .id(this.id)
                .numero(this.numero)
                .ativo(this.ativo)
                .validade(this.validade)
                .limite(this.limite)
                .transacoes(this.transacoes.stream().map(tr ->
                        Transacao.builder()
                                .codigoAutorizacao(tr.getCodigoAutorizacao())
                                .valor(tr.getValor())
                                .dtTransacao(tr.getDtTransacao())
                                .merchant(tr.getMerchant())
                                .descricao(tr.getDescricao())
                                .build())
                        .toList())
                .build();
    }

    private LocalDateTime converterStringToLocalDateTime(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(string, formatter);
        return date.atStartOfDay();
    }
}
