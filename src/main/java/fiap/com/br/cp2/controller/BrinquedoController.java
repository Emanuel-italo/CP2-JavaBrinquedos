package fiap.com.br.cp2.controller;

import fiap.com.br.cp2.dto.BrinquedoDTO;
import fiap.com.br.cp2.service.BrinquedoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brinquedos")
public class BrinquedoController {

    @Autowired
    private BrinquedoService service;

    @PostMapping
    public ResponseEntity<BrinquedoDTO> criar(@Valid @RequestBody BrinquedoDTO dto) {
        BrinquedoDTO novoBrinquedo = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoBrinquedo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrinquedoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody BrinquedoDTO dto) {
        BrinquedoDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    public ResponseEntity<List<BrinquedoDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrinquedoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}