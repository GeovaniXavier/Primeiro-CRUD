package br.projeto.ProjPessoal.mapper;

import br.projeto.ProjPessoal.dto.UsuarioDto;
import br.projeto.ProjPessoal.model.Usuario;

public class UsuarioMapper {

    public static UsuarioDto toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioDto(usuario.getId(), usuario.getNome(), usuario.getIdade());
    }

    public static Usuario toEntity(UsuarioDto usuarioDTO) {
        if (usuarioDTO == null) {
            return null;
        }
        return Usuario.builder()
                .id(usuarioDTO.id())
                .nome(usuarioDTO.nome())
                .idade(usuarioDTO.idade())
                .build();
    }
}
