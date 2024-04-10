package br.ufpe.cin.greco.devc.languageStructure.filetype.tdevice;

import br.ufpe.cin.greco.devc.languageStructure.FileDescriptor;
import br.ufpe.cin.greco.devc.languageStructure.TerminusFileDefinition;

public class TDevice extends FileDescriptor  {

	public TDevice(String projectName, FileType fileType) {
		super(projectName, fileType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setDefinition(TerminusFileDefinition tdef) {
		// TODO Auto-generated method stub
		
	}
	
	public void generateRegisters(){
		int i = 0;
		for (Register reg : registers.values()) {
			reg.buildFields(this);
			reg.checkIntegrity();
		}
		createRegistersDotFiles();
		/*for (String serv : bindings.getPosServices().keySet()) {
			System.out.println(serv + " [Pos]-> " + bindings.getPosServices().get(serv));
		}
		for (String serv : bindings.getPreServices().keySet()) {
			System.out.println(serv + " [Pre]-> " + bindings.getPreServices().get(serv));
		}
		for (String serv : bindings.getPosRequirments().keySet()) {
			System.out.println(serv + " [Pos]-> " + bindings.getPosRequirments().get(serv));
		}
		for (String serv : bindings.getPreRequirments().keySet()) {
			System.out.println(serv + " [Pre]-> " + bindings.getPreRequirments().get(serv));
		}
		for (String serv : bindings.getAlwaysRequiredRequirments().keySet()) {
			System.out.println(serv + " [Always]-> " + bindings.getAlwaysRequiredRequirments().get(serv));
		}
		for (String serv : bindings.getAlwaysRequiredServices().keySet()) {
			System.out.println(serv + " [Always]-> " + bindings.getAlwaysRequiredServices().get(serv));
		}*/
	}


}
