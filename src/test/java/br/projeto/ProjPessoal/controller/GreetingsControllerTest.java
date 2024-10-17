package br.projeto.ProjPessoal.controller;

import br.projeto.ProjPessoal.dto.UsuarioDto;
import br.projeto.ProjPessoal.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GreetingsController.class)
class GreetingsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGreetingText() throws Exception {
        mockMvc.perform(get("/usuarios/mostrarnome/{nome}", "João"))
                .andExpect(status().isOk())
                .andExpect(content().string("Aprendendo Spring Boot API João!"));
    }

    @Test
    void testRetornaOlaMundo() throws Exception {
        UsuarioDto usuarioDto = new UsuarioDto(null, "João", 30);
        when(usuarioService.salvar(any(UsuarioDto.class))).thenReturn(usuarioDto);

        mockMvc.perform(get("/usuarios/olamundo/{nome}", "João"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ola mundo João"));
    }

    @Test
    void testListaTodosUsuarios() throws Exception {
        List<UsuarioDto> usuarios = Arrays.asList(
                new UsuarioDto(1L, "João", 30),
                new UsuarioDto(2L, "Maria", 25)
        );
        when(usuarioService.listarTodos()).thenReturn(usuarios);

        mockMvc.perform(get("/usuarios/listatodos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))  // Verifica o tamanho da lista
                .andExpect(jsonPath("$[0].nome").value("João"))
                .andExpect(jsonPath("$[1].nome").value("Maria"));
    }

    @Test
    void testSalvarUsuario() throws Exception {
        UsuarioDto usuarioDTO = new UsuarioDto(null, "João", 30);
        UsuarioDto usuarioSalvo = new UsuarioDto(1L, "João", 30);
        when(usuarioService.salvar(any(UsuarioDto.class))).thenReturn(usuarioSalvo);

        mockMvc.perform(post("/usuarios/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void testAtualizarUsuario() throws Exception {
        UsuarioDto usuarioDTO = new UsuarioDto(1L, "João", 30);
        when(usuarioService.atualizar(any(UsuarioDto.class))).thenReturn(usuarioDTO);

        mockMvc.perform(put("/usuarios/atualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void testDeletarUsuario() throws Exception {
        mockMvc.perform(delete("/usuarios/delete")
                        .param("iduser", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario deletado com sucesso!"));
    }

    @Test
    void testBuscarUsuarioPorId() throws Exception {
        UsuarioDto usuarioDTO = new UsuarioDto(1L, "João", 30);
        when(usuarioService.buscarPorId(1L)).thenReturn(java.util.Optional.of(usuarioDTO));

        mockMvc.perform(get("/usuarios/buscaruserid")
                        .param("iduser", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void testBuscarUsuarioPorNome() throws Exception {
        List<UsuarioDto> usuarios = Arrays.asList(
                new UsuarioDto(1L, "João", 30),
                new UsuarioDto(2L, "Maria", 25)
        );
        when(usuarioService.buscarPorNome("João")).thenReturn(usuarios);

        mockMvc.perform(get("/usuarios/buscarPorNome")
                        .param("name", "João"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))  // Verifica o tamanho da lista
                .andExpect(jsonPath("$[0].nome").value("João"))
                .andExpect(jsonPath("$[1].nome").value("Maria"));
    }
}