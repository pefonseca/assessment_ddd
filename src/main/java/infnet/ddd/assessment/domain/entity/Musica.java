package infnet.ddd.assessment.domain.entity;

import infnet.ddd.assessment.api.rest.dto.response.MusicaResponseDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "tb_musica")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private int duracao;

    @ManyToOne
    @JoinColumn(name = "banda_id")
    private Banda banda;

    @ManyToMany(mappedBy = "musicas")
    private List<Playlist> playlists = new ArrayList<>();

    public MusicaResponseDTO toDto() {
        return MusicaResponseDTO.builder().id(this.id).nome(this.nome).duracao(this.duracao).build();
    }
}
