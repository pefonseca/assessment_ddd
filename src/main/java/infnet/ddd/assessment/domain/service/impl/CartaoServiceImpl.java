package infnet.ddd.assessment.domain.service.impl;

import infnet.ddd.assessment.api.rest.dto.request.CartaoRequestDTO;
import infnet.ddd.assessment.api.rest.dto.response.CartaoResponseDTO;
import infnet.ddd.assessment.domain.entity.Cartao;
import infnet.ddd.assessment.domain.repository.CartaoRepository;
import infnet.ddd.assessment.domain.service.CartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartaoServiceImpl implements CartaoService {

    private final CartaoRepository cartaoRepository;

    @Override
    public CartaoResponseDTO create(CartaoRequestDTO cartaoRequestDTO) {
        Cartao cartao = Cartao.builder()
                              .ativo(cartaoRequestDTO.getAtivo())
                              .numero(cartaoRequestDTO.getNumero())
                              .validade(converterStringToLocalDateTime(cartaoRequestDTO.getValidade()))
                              .limite(cartaoRequestDTO.getLimite())
                              .transacoes(new ArrayList<>())
                              .build();

        return cartaoRepository.save(cartao).toDto();
    }

    private LocalDateTime converterStringToLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(date, formatter);
        return LocalDateTime.of(parsedDate, LocalTime.now());
    }
}
