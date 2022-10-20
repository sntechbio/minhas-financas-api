package com.sntech.minhasfinancas.Usuario.service.impl;

import com.sntech.minhasfinancas.Usuario.model.Usuario;
import com.sntech.minhasfinancas.Usuario.repository.UsuarioRepository;
import com.sntech.minhasfinancas.Usuario.service.UsuarioService;
import com.sntech.minhasfinancas.exception.ErroAutenticacao;
import com.sntech.minhasfinancas.exception.RegraNegocioException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);

        if (!usuario.isPresent()) {
            throw new ErroAutenticacao("Usuário não encontrado para o email informado");
        }
        if(!usuario.get().getSenha().equals(senha)) {
            throw new ErroAutenticacao("Senha inválida");
        }

        return usuario.get();
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean emailExiste = repository.existsByEmail(email);
        if(emailExiste) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com esse e-mail");
        }
    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return repository.findById(id);
    }
}
