package br.projeto.ProjPessoal.service;

import br.projeto.ProjPessoal.dto.UsuarioDto;
import br.projeto.ProjPessoal.model.Usuario;
import br.projeto.ProjPessoal.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
    }

    @Test
    void salvarUsuarioComSucesso() {
        // Arrange: configurar os dados e o comportamento esperado
        Usuario usuario = new Usuario(1L, "João", 30);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario); // Simula o comportamento do save
        UsuarioDto usuarioDTO = new UsuarioDto(null, "João", 30);
        UsuarioDto resultado = usuarioService.salvar(usuarioDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("João", resultado.nome());
        assertEquals(30, resultado.idade());

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void listarTodosUsuarios() {

        Usuario usuario1 = new Usuario(1L, "João", 30);
        Usuario usuario2 = new Usuario(2L, "Maria", 25);
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        List<UsuarioDto> usuarios = usuarioService.listarTodos();

        assertEquals(2, usuarios.size());
        assertEquals("João", usuarios.get(0).nome());
        assertEquals("Maria", usuarios.get(1).nome());

        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void buscarUsuarioPorIdComSucesso() {

        Usuario usuario = new Usuario(1L, "João", 30);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<UsuarioDto> resultado = usuarioService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().id());
        assertEquals("João", resultado.get().nome());

        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void buscarUsuarioPorIdNaoEncontrado() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<UsuarioDto> resultado = usuarioService.buscarPorId(1L);
        assertFalse(resultado.isPresent());

        verify(usuarioRepository, times(1)).findById(1L);
    }
}