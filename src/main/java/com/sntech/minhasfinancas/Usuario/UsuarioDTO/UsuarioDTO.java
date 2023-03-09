package com.sntech.minhasfinancas.Usuario.UsuarioDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class UsuarioDTO {

    @NotBlank(message = "E-mail é obrigatório")
    @Email
    private String email;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Senha é obrigatório")
    private String senha;

}
