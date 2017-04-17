package com.mez.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.mez.dao.PessoaJuridicaDao;
import com.mez.dao.ProdutoDao;
import com.mez.dao.UsuarioDao;
import com.mez.model.Compra;
import com.mez.model.FormaPagamento;
import com.mez.model.ItemCompra;
import com.mez.model.PessoaJuridica;
import com.mez.model.Produto;
import com.mez.model.Usuario;
import com.mez.service.CadastroCompraService;
import com.mez.util.jsf.FacesUtil;
import com.mez.validation.SKU;

@Named
@ViewScoped
public class CadastroCompraBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDao usuarios;

	@Inject
	private PessoaJuridicaDao clientes;

	@Inject
	private ProdutoDao produtos;

	@Inject
	private CadastroCompraService cadastroCompraService;

	private String sku;

	@Produces
	@PedidoEdicao
	private Compra compra;

	private List<Usuario> vendedores;

	private Produto produtoLinhaEditavel;

	public CadastroCompraBean() {
		limpar();
	}

	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			this.vendedores = this.usuarios.vendedores();

			this.compra.adicionarItemVazio();

			this.recalcularPedido();
		}
	}

	private void limpar() {
		compra = new Compra();
	}

	public void compraAlterado(@Observes CompraAlteradoEvent event) {
		this.compra = event.getCompra();
	}

	public void salvar() {
		this.compra.removerItemVazio();

		try {
			this.compra = this.cadastroCompraService.salvar(this.compra);

			FacesUtil.addInfoMessage("Compra salvo com sucesso!");
		} finally {
			this.compra.adicionarItemVazio();
		}
	}

	public void recalcularPedido() {
		if (this.compra != null) {
			this.compra.recalcularValorTotal();
		}
	}

	public void carregarProdutoPorSku() {
		if (StringUtils.isNotEmpty(this.sku)) {
			this.produtoLinhaEditavel = this.produtos.porSku(this.sku);
			this.carregarProdutoLinhaEditavel();
		}
	}

	public void carregarProdutoLinhaEditavel() {
		ItemCompra item = this.compra.getItens().get(0);

		if (this.produtoLinhaEditavel != null) {
			if (this.existeItemComProduto(this.produtoLinhaEditavel)) {
				FacesUtil.addErrorMessage("Já existe um item no pedido com o produto informado.");
			} else {
				item.setProduto(this.produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel.getValorCusto());

				this.compra.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.sku = null;

				this.compra.recalcularValorTotal();
			}
		}
	}

	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;

		for (ItemCompra item : this.getCompra().getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}

		return existeItem;
	}

	public List<Produto> completarProduto(String nome) {
		return this.produtos.porNome(nome);
	}

	public void atualizarQuantidade(ItemCompra item, int linha) {
//		if (item.getQuantidade() < 1) {
//			if (linha == 0) {
//				item.setQuantidade(1);
//			} else {
//				this.getCompra().getItens().remove(linha);
//			}
//		}

		this.compra.recalcularValorTotal();
	}

	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}

	public List<PessoaJuridica> completarCliente(String nome) {
		return this.clientes.porNome(nome);
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public List<Usuario> getVendedores() {
		return vendedores;
	}

	public boolean isEditando() {
		return this.compra.getId() != null;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

	@SKU
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

}