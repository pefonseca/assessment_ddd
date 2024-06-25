package infnet.ddd.assessment.api.rest.dto.response;

import infnet.ddd.assessment.domain.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String senha;
    private String cpf;
    private String email;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private List<CartaoResponseDTO> cartoes;
    private List<PlaylistResponseDTO> playlists;
    private List<AssinaturaResponseDTO> assinaturas;

    public Usuario toEntity() {
        return Usuario.builder()
                .id(this.id)
                .nome(this.nome)
                .senha(this.senha)
                .cpf(this.cpf)
                .email(this.email)
                .rua(this.rua)
                .bairro(this.bairro)
                .cidade(this.cidade)
                .estado(this.estado)
                .cep(this.cep)
                .cartoes(this.cartoes.stream().map(CartaoResponseDTO::toEntity).toList())
                .playlists(this.playlists.stream().map(PlaylistResponseDTO::toEntity).toList())
                .assinaturas(this.assinaturas.stream().map(AssinaturaResponseDTO::toEntity).toList())
                .build();
    }

}
