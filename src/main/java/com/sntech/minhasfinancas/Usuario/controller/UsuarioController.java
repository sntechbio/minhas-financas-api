package com.sntech.minhasfinancas.Usuario.controller;
import com.sntech.minhasfinancas.Lancamento.service.LancamentoService;
import com.sntech.minhasfinancas.Usuario.UsuarioDTO.UsuarioDTO;
import com.sntech.minhasfinancas.Usuario.model.Usuario;
import com.sntech.minhasfinancas.Usuario.service.UsuarioService;
import com.sntech.minhasfinancas.exception.ErroAutenticacao;
import com.sntech.minhasfinancas.exception.RegraNegocioException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;


@RestController
@RequestMapping("/api/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final LancamentoService lancamentoService;

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuarioAutenticado = usuarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);
        } catch (ErroAutenticacao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody @Valid UsuarioDTO usuarioDTO) {

        Usuario usuario = Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha()).build();

        try {
            Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}/saldo")
    public ResponseEntity obterSaldo(@PathVariable("id") Long id) {
        Optional<Usuario> usuario = usuarioService.obterPorId(id);

        if(!usuario.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
        return ResponseEntity.ok(saldo);
    }

}
