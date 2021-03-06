package application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.Projeto;
import application.model.Usuario;
import application.repository.IUsuarioDAO;
import javassist.NotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private IUsuarioDAO dao;
	@Autowired
	private PasswordEncriptionAndDecription encription;
	
	public List<Usuario> getAll() {
		return dao.findAll();
	}

	public Usuario getById(long id) throws NotFoundException {
		Optional<Usuario> byId = dao.findById(id);
		if(!byId.isPresent()) {
			throw new NotFoundException("Usuario não encontrado");
		}
		return byId.get();
	}
	
	public List<Projeto> getUserProjects(long id) throws NotFoundException{
		Optional<Usuario> byId = dao.findById(id);
		if(!byId.isPresent()) {
			throw new NotFoundException("Usuario não encontrado");
		}
		return byId.get().getProjetos();
	}

	public Usuario createUsuario(Usuario usuarioParam) throws NotFoundException {
		Optional<Usuario> findByEmail = dao.findByEmail(usuarioParam.getEmail());
		if(findByEmail.isPresent()) {
			throw new NotFoundException("Email já cadastrado");
		}
		String encodedPass = encription.encode(usuarioParam.getPassword());
		usuarioParam.setPassword(encodedPass);
		Usuario usuario = dao.save(usuarioParam);		
		return usuario;
	}

	public Usuario updateUsuario(long id, Usuario usuarioP) throws NotFoundException {
		if(getById(id) == null) {
			throw new NotFoundException("Usuario não encontrado");
		}
		Usuario user = dao.save(usuarioP);
		return user;
	}

	public Usuario deleteUsuario(long id) throws NotFoundException {
		Usuario usuario = getById(id);
		if(usuario == null) {
			throw new NotFoundException("Usuario não encontrado");
		}
		dao.deleteById(id);
		return usuario;
	}

}
