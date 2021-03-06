package application.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import application.model.Usuario;
import application.service.UsuarioService;
import javassist.NotFoundException;

@Controller
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> getAll(){
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable long id){
		Usuario usuario;
		try {
			usuario = service.getById(id);
			return ResponseEntity.ok(usuario);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping()
	public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuarioParam) throws NotFoundException{
		Usuario usuario = service.createUsuario(usuarioParam);
		return ResponseEntity.ok(usuario);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> updateUsuario(@PathVariable long id, @RequestBody Usuario usuarioP){
		Usuario usuario;
		try {
			usuario = service.updateUsuario(id, usuarioP);
			return ResponseEntity.ok(usuario);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Usuario> deleteUsuario(@PathVariable long id){
		Usuario usuario;
		try {
			usuario = service.deleteUsuario(id);
			return ResponseEntity.ok(usuario);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}
	
}
