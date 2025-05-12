package com.revisao.ecommerce.controllers;

import com.revisao.ecommerce.dto.UsuarioDTO;
import com.revisao.ecommerce.entities.Usuario;
import com.revisao.ecommerce.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    public UsuarioDTO cadastrar(@RequestBody UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());

        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        usuario = usuarioRepository.save(usuario);

        return new UsuarioDTO(usuario);
    }
}