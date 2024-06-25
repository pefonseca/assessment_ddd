package infnet.ddd.assessment.api.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    private String nome;
    private String senha;
    private String cpf;
    private String email;
    private String cep;
    private CartaoRequestDTO cartao;
    private Long planoId;

}
