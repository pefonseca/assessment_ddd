package infnet.ddd.assessment.domain.service.impl;

import infnet.ddd.assessment.api.rest.dto.request.TransacaoRequestDTO;
import infnet.ddd.assessment.api.rest.dto.response.TransacaoResponseDTO;
import infnet.ddd.assessment.domain.entity.Cartao;
import infnet.ddd.assessment.domain.entity.Plano;
import infnet.ddd.assessment.domain.entity.Transacao;
import infnet.ddd.assessment.domain.repository.CartaoRepository;
import infnet.ddd.assessment.domain.repository.TransacaoRepository;
import infnet.ddd.assessment.domain.service.TransacaoService;
import infnet.ddd.assessment.infra.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository transacaoRepository;

    @Override
    public TransacaoResponseDTO create(TransacaoRequestDTO transacaoRequestDTO) {
        Transacao transacao = new Transacao();
        BeanUtils.copyProperties(transacaoRequestDTO, transacao);
        return transacaoRepository.save(transacao).toDto();
    }

    public void criarTransacaoParaAlterarPlano(Cartao cartao, Plano plano)  {

        validarCartaoAtivo(cartao);

        Transacao transacao = Transacao.builder()
                                       .merchant(plano.getNome())
                                       .descricao(plano.getDescricao())
                                       .valor(plano.getValor())
                                       .dtTransacao(LocalDateTime.now())
                                       .cartao(cartao)
                                       .build();

        validacoesCartao(cartao, transacao);

        cartao.setLimite(cartao.getLimite() - transacao.getValor());
        transacao = transacaoRepository.save(transacao);
        cartao.getTransacoes().add(transacao);
    }

    public void criarTransacao(Cartao cartao, Plano plano) {

        validarCartaoAtivo(cartao);

        Transacao transacao = Transacao.builder()
                                       .merchant(plano.getNome())
                                       .descricao(plano.getDescricao())
                                       .valor(plano.getValor())
                                       .dtTransacao(LocalDateTime.now())
                                       .cartao(cartao)
                                       .build();

        validacoesCartao(cartao, transacao);

        cartao.setLimite(cartao.getLimite() - transacao.getValor());

        create(transacao.toDtoRequest());
        List<Transacao> transacoes = new ArrayList<>();
        transacoes.add(transacao);
        cartao.setTransacoes(transacoes);
    }

    private void validarCartaoAtivo(Cartao cartao) {
        if (!cartao.getAtivo()) {
            throw new CustomException("Cartão não está ativo");
        }
    }

    private void validacoesCartao(Cartao cartao, Transacao transacao) {
        if (!this.validarLimite(cartao, transacao)) {
            throw new CustomException("Cartão não possui limite para esta transação");
        }

        if (!this.validarQuantidadeTransacao(cartao, transacao)) {
            throw new CustomException("Transação invalida, 3 tentativas, Tente novamente após 5 minutos.");
        }

        if (!this.validarTransacaoSemelhante(cartao, transacao)) {
            throw new CustomException("Transação invalida, Valor e Merchant iguais.");
        }
    }

    private boolean validarQuantidadeTransacao(Cartao cartao, Transacao transacao) {
        LocalDateTime doisMinutosAtras = LocalDateTime.now().minusMinutes(2);

        List<Transacao> ultimasTransacoes = cartao.getTransacoes().stream()
                                                                  .filter(x -> x.getDtTransacao().isAfter(doisMinutosAtras))
                                                                  .toList();

        return ultimasTransacoes.size() < 3;
    }

    private boolean validarTransacaoSemelhante(Cartao cartao, Transacao transacao) {
        LocalDateTime doisMinutosAtras = LocalDateTime.now().minusMinutes(2);

        List<Transacao> ultimasTransacoes = cartao.getTransacoes().stream()
                .filter(x -> x.getDtTransacao().isAfter(doisMinutosAtras))
                .toList();

        long transacoesSemelhantes = ultimasTransacoes.stream()
                .filter(x -> x.getMerchant().equals(transacao.getMerchant()) && x.getValor() == transacao.getValor())
                .count();

        return transacoesSemelhantes < 2;
    }

    private boolean validarLimite(Cartao cartao, Transacao transacao) {
        return cartao.getLimite() > transacao.getValor();
    }

}
