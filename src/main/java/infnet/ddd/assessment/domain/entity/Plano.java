package infnet.ddd.assessment.domain.entity;

import infnet.ddd.assessment.api.rest.dto.response.PlanoResponseDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Entity(name = "tb_plano")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private boolean ativo;
    private String descricao;
    private double valor;

    @OneToMany(mappedBy = "plano")
    private List<Assinatura> assinaturas = new ArrayList<>();

    public Plano(String nome, boolean ativo, String descricao, double valor) {
        this.nome = nome;
        this.ativo = ativo;
        this.descricao = descricao;
        this.valor = valor;
    }

    public PlanoResponseDTO toDto() {
        return PlanoResponseDTO.builder().id(this.id).nome(this.nome).ativo(this.ativo).descricao(this.descricao).valor(this.valor).assinaturas(new ArrayList<>()).build();
    }
}
