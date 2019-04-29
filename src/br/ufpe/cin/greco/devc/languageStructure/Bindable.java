package br.ufpe.cin.greco.devc.languageStructure;

import br.ufpe.cin.greco.devc.languageStructure.exception.BindException;

public interface Bindable {
	public void doBinds() throws BindException;
}
