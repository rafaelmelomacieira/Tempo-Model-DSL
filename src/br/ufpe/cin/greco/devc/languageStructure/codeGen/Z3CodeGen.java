package br.ufpe.cin.greco.devc.languageStructure.codeGen;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import br.ufpe.cin.greco.devc.languageStructure.factory.Z3CodeFactory;

public class Z3CodeGen implements Z3CodeFactory {

	//@Override
	public String getSourceCode(Configuration cfg, String template) {
		/*Map<String,Object> testeMap = new HashMap<>();
		testeMap.put("projeto", "rafael");
		cfg = new Configuration(Configuration.VERSION_2_3_22);
		cfg.setDirectoryForTemplateLoading(new File("templates"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		Template temp = cfg.getTemplate("test.ftl");
		Writer out = new OutputStreamWriter(System.out);
		temp.process(testeMap, out);// TODO Auto-generated method stub */
		return null;
	}

}
