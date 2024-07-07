package doublem.tempo.dsl.codeGen;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import doublem.tempo.dsl.factory.MDDCSourceCodeFactory;
import doublem.tempo.dsl.model.DeviceModel;
import doublem.tempo.dsl.model.PlatformModel;
import doublem.tempo.utils.FileManagement;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class SystemVerilogCodeGen implements MDDCSourceCodeFactory {
	
	private Configuration cfg;

	@Override
	public String getFSMControllerSourceCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPlatformFSMSourceCode(PlatformModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDevicesFSMSourceCode(DeviceModel model) {
		try{
			Template temp = this.cfg.getTemplate("devicefsm.ftl");
			//Writer out = new OutputStreamWriter(System.out);
			temp.process(model, FileManagement.getMDDCWriteTarget(model.getName(), "DevFSM", null));
			System.out.println();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (TemplateException te) {
			te.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String getDevicesProtocolsSourceCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBSPISourceCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBSISourceCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConfiguration(Configuration cfg) {
		this.cfg = cfg;		
	}

}
