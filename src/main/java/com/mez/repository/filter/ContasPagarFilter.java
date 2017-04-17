package com.mez.repository.filter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ContasPagarFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long numeroDe;
	private Long numeroAte;
	private Date dataCriacaoDe;
	private Date dataCriacaoAte;
	private String nomeFornecedor;
	private BigDecimal valor;

	public Long getNumeroDe() {
		return numeroDe;
	}

	public void setNumeroDe(Long numeroDe) {
		this.numeroDe = numeroDe;
	}

	public Long getNumeroAte() {
		return numeroAte;
	}

	public void setNumeroAte(Long numeroAte) {
		this.numeroAte = numeroAte;
	}

	public Date getDataCriacaoDe() {
		return dataCriacaoDe;
	}

	public void setDataCriacaoDe(Date dataCriacaoDe) {
		this.dataCriacaoDe = dataCriacaoDe;
	}

	public Date getDataCriacaoAte() {
		return dataCriacaoAte;
	}

	public void setDataCriacaoAte(Date dataCriacaoAte) {
		this.dataCriacaoAte = dataCriacaoAte;
	}

	public String getNomeFornecedor() {
		return nomeFornecedor;
	}

	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}