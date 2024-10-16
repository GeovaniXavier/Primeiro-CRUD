package br.projeto.ProjPessoal.service;

import br.projeto.ProjPessoal.dto.UsuarioDto;
import br.projeto.ProjPessoal.mapper.UsuarioMapper;
import br.projeto.ProjPessoal.model.Usuario;
import br.projeto.ProjPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDto salvar(UsuarioDto usuarioDTO) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return UsuarioMapper.toDTO(usuarioSalvo);
    }

    public UsuarioDto atualizar(UsuarioDto usuarioDTO) {
        if (usuarioDTO.id() == null) {
            throw new IllegalArgumentException("Id não foi informado.");
        }
        Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
        Usuario usuarioAtualizado = usuarioRepository.saveAndFlush(usuario);
        return UsuarioMapper.toDTO(usuarioAtualizado);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<UsuarioDto> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDto> buscarPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(UsuarioMapper::toDTO);
    }

    public List<UsuarioDto> buscarPorNome(String nome) {
        List<Usuario> usuarios = usuarioRepository.buscarPorNome(nome.trim().toUpperCase());
        return usuarios.stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }
}
