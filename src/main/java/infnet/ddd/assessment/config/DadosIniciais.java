package infnet.ddd.assessment.config;

import infnet.ddd.assessment.domain.entity.Banda;
import infnet.ddd.assessment.domain.entity.Musica;
import infnet.ddd.assessment.domain.entity.Plano;
import infnet.ddd.assessment.domain.repository.BandaRepository;
import infnet.ddd.assessment.domain.repository.PlanoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DadosIniciais {

    private final PlanoRepository planoRepository;
    private final BandaRepository bandaRepository;

    @PostConstruct
    public void popularBaseDadosCartao() {
        if(planoRepository.count() == 0) {
            Plano plano1 = new Plano("Plano Grátis", true, "Plano Grátis", 0.0);
            Plano plano2 = new Plano("Plano Básico", true, "Plano Básico", 10.0);
            Plano plano3 = new Plano("Plano Premium", true, "Plano Premium", 30.0);
            planoRepository.saveAll(List.of(plano1, plano2, plano3));

            Banda banda = Banda.builder().nome("MORADA").musicas(new ArrayList<>()).descricao("Morada, estilizado em letras maiúsculas como MORADA, é uma banda brasileira de Worship, formada na cidade de Fernandópolis, interior de São Paulo em 2009.").build();
            Musica musica1 = Musica.builder().nome("Musica 1").duracao(4).banda(banda).playlists(new ArrayList<>()).build();
            Musica musica2 = Musica.builder().nome("Musica 2").duracao(4).banda(banda).playlists(new ArrayList<>()).build();
            Musica musica3 = Musica.builder().nome("Musica 3").duracao(4).banda(banda).playlists(new ArrayList<>()).build();
            banda.getMusicas().add(musica1);
            banda.getMusicas().add(musica2);
            banda.getMusicas().add(musica3);
            bandaRepository.save(banda);

            System.out.println("Base de dados preenchida com sucesso.");
        } else {
            System.out.println("Base de dados já está preenchida.");
        }
    }

}
