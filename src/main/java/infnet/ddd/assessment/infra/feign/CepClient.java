package infnet.ddd.assessment.infra.feign;//package infnet.ddd.tp3.infra.feign;

import infnet.ddd.assessment.infra.response.EnderecoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cepClient", url = "https://viacep.com.br/ws")
public interface CepClient {

    @GetMapping(value = "/{cep}/json/", produces = "application/json")
    EnderecoResponse consultarCep(@PathVariable("cep") String cep);

}
