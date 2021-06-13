package br.projeto.ProjPessoal.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.projeto.ProjPessoal.Model.Usuario;
import br.projeto.ProjPessoal.Repository.UsuarioRepository;

@RestController
public class GreetingsController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @RequestMapping(value = "/mostrarnome/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Aprendendo Spring Boot API " + name + "!";
    }

    @RequestMapping(value = "/Olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome) {

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuarioRepository.save(usuario); //Grava no banco de dados

        return "Ola mundo " + nome;
    }

    @GetMapping(value = "listatodos") //Primeiro método de API
    @ResponseBody //Retorna os dados para o corpo da resposta
    public ResponseEntity<List<Usuario>> listaUsuario() {

        List<Usuario> usuarios = usuarioRepository.findAll(); //Executa a consulta no banco de dados
        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); //Retorna a lista em JSON
    }

    @PostMapping(value = "salvar") //Mapeia a url
    @ResponseBody //Descrição dá resposta
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) { //Recebe os dados pra salvar

        Usuario user = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }

    @PutMapping(value = "atualizar")
    @ResponseBody
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) { //Recebe os dados para salvar.
        if (usuario.getId() == null) {
            return new ResponseEntity<String>("Id não foi informado.", HttpStatus.OK);
        }
        Usuario user = usuarioRepository.saveAndFlush(usuario);

        return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam Long iduser) {

        usuarioRepository.deleteById(iduser);

        return new ResponseEntity<String>("Usuario deletado com sucesso!", HttpStatus.OK);
    }

    @GetMapping(value = "buscaruserid")
    @ResponseBody
    public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "iduser") Long iduser) {

        Usuario usuario = usuarioRepository.findById(iduser).get();

        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

    @GetMapping(value = "buscarPorNome")
    @ResponseBody
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) {

        List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());

        return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    }

}