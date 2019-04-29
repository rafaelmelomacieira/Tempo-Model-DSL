package br.ufpe.cin.greco.devc.languageStructure.factory;

import br.ufpe.cin.greco.devc.languageStructure.model.DeviceModel;
import br.ufpe.cin.greco.devc.languageStructure.model.PlatformModel;
import freemarker.template.Configuration;

public interface MDDCSourceCodeFactory {
	//public String getSystemVerilogCode();
	public String getFSMControllerSourceCode();
	public String getPlatformFSMSourceCode(PlatformModel model);
	public String getDevicesFSMSourceCode(DeviceModel model);
	public String getDevicesProtocolsSourceCode();
	public String getBSPISourceCode();
	public String getBSISourceCode();
	public void setConfiguration(Configuration cfg);
}
