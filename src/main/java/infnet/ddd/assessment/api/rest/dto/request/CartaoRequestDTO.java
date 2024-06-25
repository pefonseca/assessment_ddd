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
public class CartaoRequestDTO {

    private String numero;
    private Boolean ativo;
    private double limite;
    private String validade;

}
