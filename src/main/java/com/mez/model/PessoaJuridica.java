package com.mez.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table
public class PessoaJuridica extends Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	private String cnpj;
	private String inscricaoEstadual;
	private String razaoSocial;
	private Date DataAbertura;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	@NotBlank
	@Size(max = 120)
	@Column(nullable = false, length = 120)
	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public Date getDataAbertura() {
		return DataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		DataAbertura = dataAbertura;
	}

//	@Override
//	public String getDocumentoFederal() {
//		return cnpj;
//	}
//
//	@Override
//	public String getDocumentoEstadual() {
//		return inscricaoEstadual;
//	}

}
