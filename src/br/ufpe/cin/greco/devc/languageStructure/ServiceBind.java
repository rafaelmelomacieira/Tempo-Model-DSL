package br.ufpe.cin.greco.devc.languageStructure;

public class ServiceBind {
	private String serviceName;
	private String varFieldID;
	private boolean driverService;
	
		
	public String getServiceName() {
		return serviceName;
	}

	public String getVarFieldID() {
		return varFieldID;
	}

	public boolean isDriverService() {
		return driverService;
	}

	public ServiceBind(String serviceName, String varFieldID, boolean driverService) {
		this.serviceName = serviceName;
		this.varFieldID = varFieldID;
		this.driverService = driverService;
	}
}
