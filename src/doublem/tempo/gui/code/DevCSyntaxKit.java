package doublem.tempo.gui.code;

import jsyntaxpane.DefaultSyntaxKit;

public class DevCSyntaxKit extends DefaultSyntaxKit {

		public DevCSyntaxKit() {
			super(new DevCLexer());
		}
	
}
