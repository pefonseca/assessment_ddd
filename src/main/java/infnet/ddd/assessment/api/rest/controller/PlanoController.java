package infnet.ddd.assessment.api.rest.controller;

import infnet.ddd.assessment.api.rest.dto.response.PlanoResponseDTO;
import infnet.ddd.assessment.domain.service.PlanoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/plano")
@RequiredArgsConstructor
public class PlanoController {

    private final PlanoService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PlanoResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlanoResponseDTO findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }
}
