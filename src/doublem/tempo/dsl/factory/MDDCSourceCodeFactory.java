package doublem.tempo.dsl.factory;

import doublem.tempo.dsl.model.DeviceModel;
import doublem.tempo.dsl.model.PlatformModel;
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
