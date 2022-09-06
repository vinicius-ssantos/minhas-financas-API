package com.vinic.minhasfinancas.model.repository;

import com.vinic.minhasfinancas.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepositoryCustom extends JpaRepository<Lancamento, Long> {

}
