package com.generation.farmacia.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.farmacia.Model.UsuarioLogin;
import com.generation.farmacia.Model.UsuarioModel;
import com.generation.farmacia.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Optional<UsuarioModel> cadastrarUsuario(UsuarioModel usuario) {
		if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
			return Optional.empty();

		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		return Optional.of(usuarioRepository.save(usuario));
	}

	public Optional<UsuarioModel> atualizarUsuario(UsuarioModel usuario) {
		if (usuarioRepository.findById(usuario.getId()).isPresent()) {

			Optional<UsuarioModel> buscaUsuario = usuarioRepository.findByEmail(usuario.getEmail());
			if (buscaUsuario.isPresent() && (buscaUsuario.get().getId() != usuario.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario já existe!", null);
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			
			return Optional.ofNullable(usuarioRepository.save(usuario));
		}
		return Optional.empty();
	}

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {
		
		Optional<UsuarioModel> buscaUsuario = usuarioRepository.findByEmail(usuarioLogin.get().getEmail());
		if(buscaUsuario.isPresent()) {
		
			if (compararSenhas(usuarioLogin.get().getSenha(), buscaUsuario.get().getSenha())) {
				usuarioLogin.get().setId(buscaUsuario.get().getId());
				usuarioLogin.get().setNome(buscaUsuario.get().getNome());
				usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getEmail(), usuarioLogin.get().getSenha()));
				usuarioLogin.get().setSenha(buscaUsuario.get().getSenha());
				usuarioLogin.get().setFoto(buscaUsuario.get().getFoto());
				return usuarioLogin;

			}

		}
		return Optional.empty();
	}

	private String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(senha);

	}

	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.matches(senhaDigitada, senhaBanco);

	}

	private String gerarBasicToken(String usuario, String senha) {
		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));

		return "Basic " + new String(tokenBase64);

	}
}
