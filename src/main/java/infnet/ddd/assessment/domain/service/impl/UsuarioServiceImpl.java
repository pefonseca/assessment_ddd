package infnet.ddd.assessment.domain.service.impl;

import infnet.ddd.assessment.api.rest.dto.request.UsuarioRequestDTO;
import infnet.ddd.assessment.api.rest.dto.response.UsuarioResponseDTO;
import infnet.ddd.assessment.domain.entity.Assinatura;
import infnet.ddd.assessment.domain.entity.Cartao;
import infnet.ddd.assessment.domain.entity.Plano;
import infnet.ddd.assessment.domain.entity.Playlist;
import infnet.ddd.assessment.domain.entity.Usuario;
import infnet.ddd.assessment.domain.repository.UsuarioRepository;
import infnet.ddd.assessment.domain.service.PlanoService;
import infnet.ddd.assessment.domain.service.TransacaoService;
import infnet.ddd.assessment.domain.service.UsuarioService;
import infnet.ddd.assessment.infra.exception.CustomException;
import infnet.ddd.assessment.infra.feign.CepClient;
import infnet.ddd.assessment.infra.response.EnderecoResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PlanoService planoService;
    private final TransacaoService transacaoService;
    private final CepClient cepClient;

    private static final String DEFAULT_PLAYLIST_NAME= "Musicas Favoritas";

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, @Lazy PlanoService planoService, TransacaoService transacaoService, CepClient cepClient) {
        this.usuarioRepository = usuarioRepository;
        this.planoService = planoService;
        this.transacaoService = transacaoService;
        this.cepClient = cepClient;
    }

    @Override
    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll().stream().map(Usuario::toDto).toList();
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        return Objects.requireNonNull(usuarioRepository.findById(id).orElseThrow(() -> new CustomException("Usuario não encontrado"))).toDto();
    }

    @Override
    public UsuarioResponseDTO create(UsuarioRequestDTO usuarioRequestDTO) throws Exception {
        Usuario usuario = new Usuario();
        EnderecoResponse endereco = cepClient.consultarCep(usuarioRequestDTO.getCep());
        usuario.criar(usuarioRequestDTO, endereco);

        Cartao cartao = createCartao(usuarioRequestDTO);
        cartao.setUsuario(usuario);
        usuario.getCartoes().add(cartao);

        validacaoUsuario(usuario);

        Plano plano = planoService.findById(usuarioRequestDTO.getPlanoId()).toEntity();
        transacaoService.criarTransacao(cartao, plano);

        Assinatura assinatura = createAssinatura(plano, usuario);
        usuario.getAssinaturas().add(assinatura);

        Playlist playlist = createPlaylist(usuario);
        usuario.getPlaylists().add(playlist);

        Usuario savedUsuario = usuarioRepository.save(usuario);

        return savedUsuario.toDto();
    }

    @Override
    public UsuarioResponseDTO alterarPlano(Long id, Long planoId) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new CustomException("Usuario not found."));

        Cartao cartao = usuario.getCartoes().stream().findFirst().orElseThrow(() -> new CustomException("Cartão not found."));

        Plano plano = planoService.findById(planoId).toEntity();

        usuario.getAssinaturas().forEach(assinatura -> {
            if(assinatura.isAtivo()) {
                assinatura.setAtivo(false);
            }
        });

        Assinatura novaAssinatura = createAssinatura(plano, usuario);

        usuario.getAssinaturas().add(novaAssinatura);

        transacaoService.criarTransacaoParaAlterarPlano(cartao, plano);

        return usuarioRepository.save(usuario).toDto();

    }

    private void validacaoUsuario(Usuario usuario) {
        if(!usuarioTemCartaoValido(usuario)) {
            throw new CustomException("Usuário não tem um cartão de crédito válido.");
        }

        if (usuarioTemPlanoAtivo(usuario)) {
            throw new CustomException("Usuário já possui um plano ativo");
        }
    }

    private Cartao createCartao(UsuarioRequestDTO usuarioRequestDTO) {
        return Cartao.builder()
                     .ativo(usuarioRequestDTO.getCartao().getAtivo())
                     .numero(usuarioRequestDTO.getCartao().getNumero())
                     .validade(converterStringToLocalDateTime(usuarioRequestDTO.getCartao().getValidade()))
                     .limite(usuarioRequestDTO.getCartao().getLimite())
                     .transacoes(new ArrayList<>())
                     .build();
    }

    private Assinatura createAssinatura(Plano plano, Usuario usuario) {
        return Assinatura.builder()
                         .plano(plano)
                         .dtAssinatura(LocalDateTime.now())
                         .ativo(true)
                         .usuario(usuario)
                         .build();
    }

    private Playlist createPlaylist(Usuario usuario) {
        return Playlist.builder()
                       .nome(DEFAULT_PLAYLIST_NAME)
                       .musicas(new ArrayList<>())
                       .usuario(usuario)
                       .build();
    }

    private LocalDateTime converterStringToLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(date, formatter);
        return LocalDateTime.of(parsedDate, LocalTime.now());
    }

    private boolean usuarioTemCartaoValido(Usuario usuario) {
        for (Cartao cartao : usuario.getCartoes()) {
            if (cartao.getAtivo() && cartao.getValidade().isAfter(LocalDate.now().atStartOfDay())) {
                return true;
            }
        }
        return false;
    }

    private boolean usuarioTemPlanoAtivo(Usuario usuario) {
        for (Plano plano : usuario.getAssinaturas().stream().map(Assinatura::getPlano).toList()) {
            if (plano.isAtivo()) {
                return true;
            }
        }
        return false;
    }
}
