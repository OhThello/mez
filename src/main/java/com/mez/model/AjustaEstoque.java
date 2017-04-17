package com.mez.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class AjustaEstoque implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@Column(name = "data_ajuste")
	private Date dataAjuste = new Date();

	private String tipo = "ENTRADA";

	private String motivo;

	@Column(name = "estoque_atual")
	private Integer estoqueAtual;

	@Column(name = "estoque_anterior")
	private Integer estoqueAnterior;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "codigo_produto")
	private Produto produto;

	public void ajustarEstoque() throws Exception {
		this.estoqueAnterior = produto.getQuantidadeEstoque();
		if (tipo.equals("ENTRADA")) {
			Integer novoEstoque = produto.getQuantidadeEstoque() + estoqueAtual;
			produto.setQuantidadeEstoque(novoEstoque);
		} else {
			if (estoqueAtual.compareTo(produto.getQuantidadeEstoque()) > 0) {
				throw new Exception("Quantidade insuficiente no estoque");
			}
			Integer novoEstoque = produto.getQuantidadeEstoque() - estoqueAtual;
			produto.setQuantidadeEstoque(novoEstoque);

		}
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getDataAjuste() {
		return dataAjuste;
	}

	public void setDataAjuste(Date dataAjuste) {
		this.dataAjuste = dataAjuste;
	}

	@NotBlank
	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@NotNull
	public Integer getEstoqueAtual() {
		return estoqueAtual;
	}

	public void setEstoqueAtual(Integer estoqueAtual) {
		this.estoqueAtual = estoqueAtual;
	}

	public Integer getEstoqueAnterior() {
		return estoqueAnterior;
	}

	public void setEstoqueAnterior(Integer estoqueAnterior) {
		this.estoqueAnterior = estoqueAnterior;
	}

	@NotNull
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AjustaEstoque other = (AjustaEstoque) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
