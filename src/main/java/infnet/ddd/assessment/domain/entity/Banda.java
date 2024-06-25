package infnet.ddd.assessment.domain.entity;


import infnet.ddd.assessment.api.rest.dto.response.BandaResponseDTO;
import infnet.ddd.assessment.api.rest.dto.response.MusicaResponseDTO;
import jakarta.persistence.CascadeType;
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
@Entity(name = "tb_banda")
public class Banda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;

    @OneToMany(mappedBy = "banda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Musica> musicas = new ArrayList<>();

    public BandaResponseDTO toDto() {
        return BandaResponseDTO.builder()
                               .id(this.id)
                               .nome(this.nome)
                               .descricao(this.descricao)
                               .musicas(new ArrayList<>())
                               .build();
    }
}
