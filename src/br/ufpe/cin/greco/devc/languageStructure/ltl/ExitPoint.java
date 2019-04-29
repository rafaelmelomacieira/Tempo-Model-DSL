package br.ufpe.cin.greco.devc.languageStructure.ltl;

public class ExitPoint extends LTLRule {
	private String target;
	private IDevCState targetState;
	private boolean fromEntryPoint;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	public ExitPoint(String target, String ltlf, String z3ltlf) {
		super(ltlf,z3ltlf);
		this.target = target;
		this.fromEntryPoint = false;
	}
	
	public ExitPoint(String target, String ltlf, String z3ltlf, boolean fromEntryPoint) {
		super(ltlf, z3ltlf);
		this.target = target;
		this.fromEntryPoint = fromEntryPoint;
	}

	public IDevCState getTargetState() {
		return targetState;
	}

	public void setTargetState(IDevCState targetState) {
		this.targetState = targetState;
	}
	
	public boolean isFromEntryPoint(){
		return fromEntryPoint;
	}

	/*@Override
	public int compareTo(ExitPoint o) {
		if (o.getLtlf().equals(this.getLtlf())){
			if (o.getTarget().equals(this.getTarget())){
				return 0;
			}else{
				return 1;
			}
		}else{
			return 1;
		}
	}*/
}
