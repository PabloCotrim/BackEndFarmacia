package com.generation.farmacia.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.generation.farmacia.Model.UsuarioModel;
import com.generation.farmacia.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Optional<UsuarioModel> usuario = usuarioRepository.findByEmail(userName);
		usuario.orElseThrow(() -> new UsernameNotFoundException(userName + "not found."));

		return usuario.map(UserDetailsImpl::new).get();
	}

}
