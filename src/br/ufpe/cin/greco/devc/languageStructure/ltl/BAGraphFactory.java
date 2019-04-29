package br.ufpe.cin.greco.devc.languageStructure.ltl;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphFactory;
import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.IState;
import rwth.i2.ltl2ba4j.model.ITransition;


public class BAGraphFactory implements IGraphFactory{

	@Override
	public IState State(String label, boolean isInitial, boolean isFinal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITransition Transition(Set<IGraphProposition> labels,
			IState sourceState, IState targetState) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGraphProposition Proposition(String label, boolean isNegated) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGraphProposition SigmaProposition() {
		// TODO Auto-generated method stub
		return null;
	}

}
