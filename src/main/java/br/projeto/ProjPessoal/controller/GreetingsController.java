package br.projeto.ProjPessoal.controller;

import br.projeto.ProjPessoal.dto.UsuarioDto;
import br.projeto.ProjPessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class GreetingsController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/mostrarnome/{nome}")
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String nome) {
        return "Aprendendo Spring Boot API " + nome + "!";
    }

    @GetMapping("/olamundo/{nome}")
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome) {
        UsuarioDto usuarioDTO = new UsuarioDto(null, nome, 0);
        usuarioService.salvar(usuarioDTO);
        return "Ola mundo " + nome;
    }

    @GetMapping("/listatodos")
    public ResponseEntity<List<UsuarioDto>> listaUsuario() {
        List<UsuarioDto> usuarios = usuarioService.listarTodos();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PostMapping("/salvar")
    public ResponseEntity<UsuarioDto> salvar(@RequestBody UsuarioDto usuarioDTO) {
        UsuarioDto usuarioSalvo = usuarioService.salvar(usuarioDTO);
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody UsuarioDto usuarioDTO) {
        try {
            UsuarioDto usuarioAtualizado = usuarioService.atualizar(usuarioDTO);
            return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Id não foi informado.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long iduser) {
        usuarioService.deletar(iduser);
        return new ResponseEntity<>("Usuario deletado com sucesso!", HttpStatus.OK);
    }

    @GetMapping("/buscaruserid")
    public ResponseEntity<UsuarioDto> buscaruserid(@RequestParam(name = "iduser") Long iduser) {
        return usuarioService.buscarPorId(iduser)
                .map(usuarioDTO -> new ResponseEntity<>(usuarioDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/buscarPorNome")
    public ResponseEntity<List<UsuarioDto>> buscarPorNome(@RequestParam(name = "name") String name) {
        List<UsuarioDto> usuarios = usuarioService.buscarPorNome(name);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}