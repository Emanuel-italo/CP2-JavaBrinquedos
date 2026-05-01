package fiap.com.br.cp2.service;

import fiap.com.br.cp2.dto.BrinquedoDTO;
import fiap.com.br.cp2.entity.Brinquedo;
import fiap.com.br.cp2.exception.BrinquedoNotFoundException;
import fiap.com.br.cp2.repository.BrinquedoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrinquedoService {

    @Autowired
    private BrinquedoRepository brinquedoRepository;

    public BrinquedoDTO criar(BrinquedoDTO dto) {
        // Usa o construtor cheio da sua entidade (passando null no ID para o banco gerar)
        Brinquedo brinquedo = new Brinquedo(
                null,
                dto.getNome(),
                dto.getTipo(),
                dto.getClassificacao(),
                dto.getTamanho(),
                dto.getPreco()
        );

        Brinquedo salvo = brinquedoRepository.save(brinquedo);
        return toDTO(salvo);
    }

    public BrinquedoDTO atualizar(Long id, BrinquedoDTO dto) {
        Brinquedo existente = brinquedoRepository.findById(id)
                .orElseThrow(() -> new BrinquedoNotFoundException(id));

        // Usando o SEU método atualizarCom em vez de setters individuais!
        existente.atualizarCom(
                dto.getNome(),
                dto.getTipo(),
                dto.getClassificacao(),
                dto.getTamanho(),
                dto.getPreco()
        );

        Brinquedo atualizado = brinquedoRepository.save(existente);
        return toDTO(atualizado);
    }

    public List<BrinquedoDTO> listarTodos() {
        return brinquedoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BrinquedoDTO buscarPorId(Long id) {
        Brinquedo brinquedo = brinquedoRepository.findById(id)
                .orElseThrow(() -> new BrinquedoNotFoundException(id));
        return toDTO(brinquedo);
    }

    public void excluir(Long id) {
        if (!brinquedoRepository.existsById(id)) {
            throw new BrinquedoNotFoundException(id);
        }
        brinquedoRepository.deleteById(id);
    }

    // Método utilitário de conversão
    private BrinquedoDTO toDTO(Brinquedo brinquedo) {
        return new BrinquedoDTO(
                brinquedo.getId(),
                brinquedo.getNome(),
                brinquedo.getTipo(),
                brinquedo.getClassificacao(),
                brinquedo.getTamanho(),
                brinquedo.getPreco()
        );
    }
}