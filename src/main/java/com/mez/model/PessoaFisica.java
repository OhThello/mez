package com.mez.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table
public class PessoaFisica extends Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	private String cpf;
	private String rg;
	private String sexo = "Masculino";
	private Date dataNascimento;

	@NotBlank
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@NotNull
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	// @Override
	// public String getDocumentoFederal() {
	// return cpf;
	// }
	//
	// @Override
	// public String getDocumentoEstadual() {
	// return rg;
	// }

}
