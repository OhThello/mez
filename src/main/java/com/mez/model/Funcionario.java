package com.mez.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Funcionario extends PessoaFisica implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataAdmissao;
	private Date dataDemissao;
	private Cargo cargo;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataDemissao() {
		return dataDemissao;
	}

	public void setDataDemissao(Date dataDemissao) {
		this.dataDemissao = dataDemissao;
	}

	@ManyToOne
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

}
