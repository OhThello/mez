package com.mez.controller;

import com.mez.model.Compra;

public class CompraAlteradoEvent {

	private Compra compra;

	public CompraAlteradoEvent(Compra compra) {
		this.compra = compra;
	}

	public Compra getCompra() {
		return compra;
	}

}
