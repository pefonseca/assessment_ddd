package infnet.ddd.assessment.domain.service;

import infnet.ddd.assessment.api.rest.dto.request.TransacaoRequestDTO;
import infnet.ddd.assessment.api.rest.dto.response.TransacaoResponseDTO;
import infnet.ddd.assessment.domain.entity.Cartao;
import infnet.ddd.assessment.domain.entity.Plano;
import infnet.ddd.assessment.domain.entity.Transacao;

import java.util.List;

public interface TransacaoService {

    TransacaoResponseDTO create(TransacaoRequestDTO transacaoRequestDTO);
    void criarTransacao(Cartao cartao, Plano plano);
    void criarTransacaoParaAlterarPlano(Cartao cartao, Plano plano);
}
