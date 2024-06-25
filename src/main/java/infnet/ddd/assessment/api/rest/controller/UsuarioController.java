package infnet.ddd.assessment.api.rest.controller;

import infnet.ddd.assessment.api.rest.dto.request.UsuarioRequestDTO;
import infnet.ddd.assessment.api.rest.dto.response.PlanoResponseDTO;
import infnet.ddd.assessment.api.rest.dto.response.UsuarioResponseDTO;
import infnet.ddd.assessment.domain.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioResponseDTO> findAll() {
        return usuarioService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO create(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) throws Exception {
        return usuarioService.create(usuarioRequestDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponseDTO findById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @PutMapping("/{id}/alterar_plano/{planoId}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponseDTO alterarPlano(@PathVariable(value = "id") Long id,
                                         @PathVariable(value = "planoId") Long planoId) throws Exception {
        return usuarioService.alterarPlano(id, planoId);
    }
}
