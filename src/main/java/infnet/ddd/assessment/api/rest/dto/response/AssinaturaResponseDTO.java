package infnet.ddd.assessment.api.rest.dto.response;

import infnet.ddd.assessment.domain.entity.Assinatura;
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
public class AssinaturaResponseDTO {

    private Long id;
    private boolean ativo;
    private LocalDateTime dtAssinatura;
    private PlanoResponseDTO planoResponseDTO;

    public Assinatura toEntity() {
        return Assinatura.builder()
                .id(this.id)
                .ativo(this.ativo)
                .dtAssinatura(this.dtAssinatura)
                .plano(planoResponseDTO.toEntity())
                .build();
    }
}
