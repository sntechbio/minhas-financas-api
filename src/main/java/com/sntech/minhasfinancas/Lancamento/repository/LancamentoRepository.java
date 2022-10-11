package com.sntech.minhasfinancas.Lancamento.repository;

import com.sntech.minhasfinancas.Lancamento.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
