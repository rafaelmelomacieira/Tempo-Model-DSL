package doublem.tempo.dsl.ltl;

import rwth.i2.ltl2ba4j.model.IState;

public class BAState implements IState {
	
	private String label; 
	private boolean isInitial; 
	private boolean isFinal;
	
	public BAState(String label, boolean isInitial, boolean isFinal) {
		this.label = label;
		this.isInitial = isInitial;
		this.isFinal = isFinal;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public boolean isInitial() {
		return this.isInitial;
	}

	@Override
	public boolean isFinal() {
		return this.isFinal;
	}
}
