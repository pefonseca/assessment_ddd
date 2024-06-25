package infnet.ddd.assessment.domain.entity;

import infnet.ddd.assessment.api.rest.dto.request.UsuarioRequestDTO;
import infnet.ddd.assessment.api.rest.dto.response.UsuarioResponseDTO;
import infnet.ddd.assessment.infra.response.EnderecoResponse;
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
import java.util.Base64;
import java.util.InputMismatchException;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cartao> cartoes = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Playlist> playlists = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assinatura> assinaturas = new ArrayList<>();

    private static final String DEFAULT_PLAYLIST_NAME= "Musicas Favoritas";

    public void criar(UsuarioRequestDTO usuarioRequestDTO, EnderecoResponse enderecoResponse) throws Exception {
        if (isValidoCpf(usuarioRequestDTO.getCpf()) == false) {
            throw new Exception("CPF está invalido");
        }

        this.setCpf(usuarioRequestDTO.getCpf());
        this.setNome(usuarioRequestDTO.getNome());
        this.setEmail(usuarioRequestDTO.getEmail());
        this.setSenha(this.encodeSenha(usuarioRequestDTO.getSenha()));
        this.setRua(enderecoResponse.getLogradouro());
        this.setBairro(enderecoResponse.getBairro());
        this.setCidade(enderecoResponse.getLocalidade());
        this.setEstado(enderecoResponse.getUf());
        this.setCep(enderecoResponse.getCep());
    }

//    public void favoritarMusica(UUID idPlayList, Musica musica) {
//        for(Playlist playlist : this.playlists) {
//            if (playlist.getId().equals(idPlayList)) {
//                playlist.getMusicas().add(musica);
//            }
//        }
//    }
//
//    public void desfavoritarMusica(UUID idPlayList, Musica musica) {
//        for(Playlist playlist : this.playlists) {
//            if (playlist.getId().equals(idPlayList)) {
//                playlist.getMusicas().remove(musica);
//            }
//        }
//    }

    private String encodeSenha(String senha) {
        return Base64.getEncoder().encodeToString(senha.getBytes());
    }

    private boolean isValidoCpf(String CPF) {
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere "0" no inteiro 0
                // (48 eh a posicao de "0" na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    public UsuarioResponseDTO toDto() {
        return UsuarioResponseDTO.builder()
                                 .id(this.id)
                                 .nome(this.nome)
                                 .senha(this.senha)
                                 .cpf(this.cpf)
                                 .email(this.email)
                                 .rua(this.rua)
                                 .bairro(this.bairro)
                                 .cidade(this.cidade).estado(this.estado)
                                 .cep(this.cep)
                                 .cartoes(this.cartoes.stream().map(Cartao::toDto).toList())
                                 .playlists(this.playlists.stream().map(Playlist::toDto).toList())
                                 .assinaturas(this.assinaturas.stream().map(Assinatura::toDto).toList())
                                 .build();
    }
}
