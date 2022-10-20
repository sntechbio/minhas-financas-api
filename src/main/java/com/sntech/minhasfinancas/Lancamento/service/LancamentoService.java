package com.sntech.minhasfinancas.Lancamento.service;

import com.sntech.minhasfinancas.Lancamento.enums.StatusLancamento;
import com.sntech.minhasfinancas.Lancamento.model.Lancamento;

import java.util.List;

public interface LancamentoService {

    Lancamento salvar(Lancamento lancamento);
    Lancamento atualizar(Lancamento lancamento);
    void deletar(Lancamento lancamento);
    List<Lancamento> buscar(Lancamento lancamentoFiltro);
    void atualizarStatus(Lancamento lancamento, StatusLancamento statusLancamento);
    void validar(Lancamento lancamento);

}
