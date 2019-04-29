package br.ufpe.cin.greco.devc.ui.code;

import jsyntaxpane.DefaultSyntaxKit;

public class DevCSyntaxKit extends DefaultSyntaxKit {

		public DevCSyntaxKit() {
			super(new DevCLexer());
		}
	
}
