package br.ufpe.cin.greco.devc.languageStructure;

public class Return {
	private Integer size; 
	private boolean signed;
	public Return(Integer size, boolean signed) {
		this.size = size;
		this.signed = signed;
	}
	public Integer getSize() {
		return size;
	}
	public boolean isSigned() {
		return signed;
	}
	
	
}
