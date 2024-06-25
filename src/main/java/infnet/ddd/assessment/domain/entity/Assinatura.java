package infnet.ddd.assessment.domain.entity;


import infnet.ddd.assessment.api.rest.dto.response.AssinaturaResponseDTO;
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
@Entity(name = "tb_assinatura")
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean ativo;
    private LocalDateTime dtAssinatura;

    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Plano plano;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public AssinaturaResponseDTO toDto() {
        return AssinaturaResponseDTO.builder().id(this.id).planoResponseDTO(plano.toDto()).ativo(this.ativo).dtAssinatura(this.dtAssinatura).build();
    }

}
