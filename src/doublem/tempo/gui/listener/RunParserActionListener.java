package doublem.tempo.gui.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import doublem.tempo.gui.Editor;

public class RunParserActionListener implements ActionListener {

	private Component parent;

	public RunParserActionListener(Component parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
			((Editor) this.parent).doParse();
			

			//*************************************************
			//((Editor) this.parent).generateTreeNodes();
			//DDFA ddfa = ((Editor) this.parent).generateDDFA();
			System.out.println("descomentar em actionPerformed(ActionEvent arg0)");
			//*************************************************
			
			/*for (String ptrn : TDCCheckerGenerator.getInstance().getPatterns().keySet()) {
				System.out.println(ptrn + " -> " + TDCCheckerGenerator.getInstance().getPatterns().get(ptrn).getValue());
			}*/
			//((Editor) this.parent).createServiceMenu(ddfa);
		}catch (Exception e) {
			
			//e.printStackTrace();
		}
		
	}

}
