package com.sntech.minhasfinancas.security;

import com.sntech.minhasfinancas.Usuario.model.Usuario;

public interface JwtService {

    String gerarToken(Usuario usuario);

    //Claims obterClaims(String token) throws ExpiredJwtException;

    boolean isTokenValido(String token);

    String obterLoginUsuario( String token );

}
