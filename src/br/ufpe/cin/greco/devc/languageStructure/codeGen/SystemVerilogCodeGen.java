package br.ufpe.cin.greco.devc.languageStructure.codeGen;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import br.ufpe.cin.greco.devc.languageStructure.factory.MDDCSourceCodeFactory;
import br.ufpe.cin.greco.devc.languageStructure.model.DeviceModel;
import br.ufpe.cin.greco.devc.languageStructure.model.PlatformModel;
import br.ufpe.cin.greco.devc.utils.FileManagement;

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
