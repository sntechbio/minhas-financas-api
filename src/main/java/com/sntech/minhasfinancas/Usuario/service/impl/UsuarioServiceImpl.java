package com.sntech.minhasfinancas.Usuario.service.impl;

import com.sntech.minhasfinancas.Usuario.model.Usuario;
import com.sntech.minhasfinancas.Usuario.repository.UsuarioRepository;
import com.sntech.minhasfinancas.Usuario.service.UsuarioService;
import com.sntech.minhasfinancas.exception.RegraNegocioException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        return null;
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        return null;
    }

    @Override
    public void validarEmail(String email) {
        boolean emailExiste = repository.existsByEmail(email);
        if(emailExiste) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com esse e-mail");
        }
    }
}
