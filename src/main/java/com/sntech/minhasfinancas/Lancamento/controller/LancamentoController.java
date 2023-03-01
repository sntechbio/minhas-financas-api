package com.sntech.minhasfinancas.Lancamento.controller;

import com.sntech.minhasfinancas.Lancamento.enums.StatusLancamento;
import com.sntech.minhasfinancas.Lancamento.enums.TipoLancamento;
import com.sntech.minhasfinancas.Lancamento.lancamentoDTO.AtualizaStatusDTO;
import com.sntech.minhasfinancas.Lancamento.lancamentoDTO.LancamentoDTO;
import com.sntech.minhasfinancas.Lancamento.model.Lancamento;
import com.sntech.minhasfinancas.Lancamento.service.LancamentoService;
import com.sntech.minhasfinancas.Usuario.model.Usuario;
import com.sntech.minhasfinancas.Usuario.service.UsuarioService;
import com.sntech.minhasfinancas.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    private final LancamentoService lancamentoService;

    private final UsuarioService usuarioService;

    public LancamentoController(LancamentoService lancamentoService, UsuarioService usuarioService) {
        this.lancamentoService = lancamentoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity buscar(@RequestParam(value = "descrição", required = false) String descricao,
                                 @RequestParam(value = "mes", required = false) Integer mes,
                                 @RequestParam(value = "ano", required = false) Integer ano,
                                 @RequestParam("usuario") Long idUsuario) {
        Lancamento lancamentoFiltro = new Lancamento();
        lancamentoFiltro.setDescricao(descricao);
        lancamentoFiltro.setMes(mes);
        lancamentoFiltro.setAno(ano);

        Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
        if (!usuario.isPresent()) {
            return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuário não encontrado para o Id informado.");
        } else {
            lancamentoFiltro.setUsuario(usuario.get());
        }

        List<Lancamento> lancamentos = lancamentoService.buscar(lancamentoFiltro);
        return ResponseEntity.ok(lancamentos);
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO lancamentoDTO) {
        try {
            Lancamento entidadeLancamento = converter(lancamentoDTO);
            lancamentoService.salvar(entidadeLancamento);
            return ResponseEntity.ok(entidadeLancamento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    private ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto ) {
        return lancamentoService.obterPorId(id).map( entity -> {
            try {
                Lancamento lancamento = converter(dto);
                lancamento.setId(entity.getId());
                lancamentoService.atualizar(lancamento);
                return ResponseEntity.ok(lancamento);
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity("Lancamento não encontrado na base de Dados", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("{id}/atualiza-status")
    public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusDTO atualizaStatusDTO) {
        return lancamentoService.obterPorId(id).map(entity -> {
           StatusLancamento statusSelecionado =  StatusLancamento.valueOf(atualizaStatusDTO.getStatus());
           if(statusSelecionado == null) {
               return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lançamento, envie um status válido");
           }

           try {
               entity.setStatus(statusSelecionado);
               lancamentoService.atualizar(entity);
               return ResponseEntity.ok(entity);
           } catch (RegraNegocioException e) {
               return ResponseEntity.badRequest().body(e.getMessage());
           }

        }).orElseGet(() -> new ResponseEntity("Lancamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
    }


    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        return lancamentoService.obterPorId(id).map(entidade -> {
            lancamentoService.deletar(entidade);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity("Lancamento não encontrado na base de Dados", HttpStatus.BAD_REQUEST));
    }

    private Lancamento converter(LancamentoDTO dto) {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());

        Usuario usuario = usuarioService
                .obterPorId(dto.getUsuario())
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado para o Id informado"));

        lancamento.setUsuario(usuario);
        if (dto.getTipo() != null) {
            lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        }

        if (dto.getStatus() != null) {
            lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        }

        lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        return lancamento;
    }
}
