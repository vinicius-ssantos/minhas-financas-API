package com.vinic.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.vinic.minhasfinancas.model.entity.Lancamento;
import com.vinic.minhasfinancas.model.entity.Usuario;
import com.vinic.minhasfinancas.model.enums.StatusLancamento;
import com.vinic.minhasfinancas.model.enums.TipoLancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{
	@Query( value = 
			  " select sum(l.valor) from Lancamento l join l.usuario u "
			+ " where u.id = :idUsuario and l.tipo =:tipo and l.status = :status group by u " )
	BigDecimal obterSaldoPorTipoLancamentoEUsuarioEStatus(
			@Param("idUsuario") Long idUsuario, 
			@Param("tipo") TipoLancamento tipo,
			@Param("status") StatusLancamento status);
}
