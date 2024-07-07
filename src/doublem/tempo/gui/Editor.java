package doublem.tempo.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import doublem.tempo.gui.listener.DeviceTreeClickListener;
import doublem.tempo.gui.listener.NewFileListener;
import doublem.tempo.gui.listener.OpenProjectListener;
import doublem.tempo.gui.listener.QuitListener;
import doublem.tempo.gui.listener.RunParserActionListener;
import doublem.tempo.gui.listener.SaveListener;
import jsyntaxpane.DefaultSyntaxKit;

public class Editor extends JFrame {
	
	static{
		instance = new Editor();
	}

	private static Editor instance;
	private TerminusProject currentProject;
	private JDesktopPane desktop;
	private JEditorPane codeEditor;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newMenu;
	private JMenuItem openMenu;
	private JMenuItem saveMenu;
	private JMenuItem saveAsMenu;
	private JMenuItem newFileMenu;
	private JMenuItem openFileMenu;
	private JMenuItem addFileMenu;
	private JMenuItem quitMenu;
	private JMenu devc;
	private JMenuItem parseMenu;
	private JMenu ddriver;
	private JMenuItem kernelMenu;
	private JMenuItem gendrvMenu;
	private JMenu autom;
	private JMenu langMenu;
	private JTree elementsTree;
	private JSplitPane mainPane;
	private JSplitPane terminalPane;
	private static TextArea terminal;
	private static DefaultTreeModel model;
	private DefaultMutableTreeNode rootElem;
	
	private ButtonGroup group;
	private JRadioButtonMenuItem Clng;
	private JRadioButtonMenuItem Cpplng;
	private JRadioButtonMenuItem veriloglng;
	private JRadioButtonMenuItem svlng;
	private JRadioButtonMenuItem vhdllng;
	private JMenuItem genMenu;
	private JMenuItem mainMenu;
	//private JMenuItem servsMenu;
	//private JMenu servMenu;
	private JMenu platform;
	private JMenuItem dbaMenu;
	private JMenuItem kpMenu;
	private JMenuItem vppMenu;
	private JMenuItem gsMenu;
	private JMenu about;
	private static JLabel statusBar;

	private HashMap<String, TDevCCodeEditor> currentTDevCCodeEditor;
	
	
	//private DDFA currentDDFA;
	private HashMap<String, JInternalFrame> windows = new HashMap<String, JInternalFrame>();
	
	//private 

	/*public DDFA getCurrentDDFA() {
		return currentDDFA;
	}*/
	
	public static Editor getInstance(){
		return instance;
	}
	
	private static void setStatus(String text){
		statusBar.setText(text);
	}

	public String getStatus(){
		return statusBar.getText();
	}

	private Editor() {
		this.setTitle(generateTitle());
		createLayout();
		enableOnStartEditor();
	}

	public String generateTitle(){
		if (currentProject == null){
			return "Macieira";
		}else{
			return "Macieira :: " + currentProject.getName(); 
		}
	}

	public void setUnsavedTitle(){
		this.setTitle("*" + this.generateTitle());
	}

	public void setSavedTitle(){
		this.setTitle(this.generateTitle());
	}

	public void enableOnOpenFile(){
		devc.setEnabled(true);
		parseMenu.setEnabled(true);
		ddriver.setEnabled(false);
		autom.setEnabled(false);
		platform.setEnabled(false);
		saveMenu.setEnabled(false);
		saveAsMenu.setEnabled(false);
	}

	public void enableOnStartEditor(){
		devc.setEnabled(false);
		ddriver.setEnabled(false);
		autom.setEnabled(false);
		platform.setEnabled(false);
		saveMenu.setEnabled(false);
		saveAsMenu.setEnabled(false);
	}

	public void enableOnChangeEditor(){
		devc.setEnabled(false);
		ddriver.setEnabled(false);
		autom.setEnabled(false);
		platform.setEnabled(false);
		saveMenu.setEnabled(true);
		saveAsMenu.setEnabled(true);
	}
	
	public void enableOnParser(){
		devc.setEnabled(true);
		ddriver.setEnabled(true);
		autom.setEnabled(true);
		platform.setEnabled(true);
		saveMenu.setEnabled(true);
		saveAsMenu.setEnabled(true);
	}

	private JMenuBar createMenu(){
		menuBar = new JMenuBar();
		fileMenu = new JMenu("Project");
		newMenu = new JMenuItem("New");
		newMenu.addActionListener(new NewFileListener(this));
		openMenu = new JMenuItem("Open");
		openMenu.addActionListener(new OpenProjectListener(this));
		saveMenu = new JMenuItem("Save");
		saveMenu.addActionListener(new SaveListener(this));
		saveAsMenu = new JMenuItem("Save as...");
		newFileMenu = new JMenuItem("New File");
		openFileMenu = new JMenuItem("Open File");
		addFileMenu = new JMenuItem("Add File");
		quitMenu = new JMenuItem("Quit");
		quitMenu.addActionListener(new QuitListener(this));
		devc = new JMenu("TDevC");
		parseMenu = new JMenuItem("Run Parser");
		parseMenu.addActionListener(new RunParserActionListener(this));
		ddriver = new JMenu("Device Driver");
		kernelMenu = new JMenuItem("Kernel Version");
		gendrvMenu = new JMenuItem("Generate Code");
		autom = new JMenu("Device");
		langMenu = new JMenu("Language");
		group = new ButtonGroup();
		Clng = new JRadioButtonMenuItem("C");
		Clng.setSelected(true);
		Cpplng = new JRadioButtonMenuItem("C++");
		veriloglng = new JRadioButtonMenuItem("Verilog");
		svlng = new JRadioButtonMenuItem("SystemVerilog");
		vhdllng = new JRadioButtonMenuItem("VHDL");
		group.add(Clng);
		group.add(Cpplng);
		group.add(veriloglng);
		group.add(svlng);
		group.add(vhdllng);
		genMenu = new JMenuItem("Generate Code");
		mainMenu = new JMenuItem("View Device FSM");
		//mainMenu.addActionListener(new ViewDeviceGraphListener(this));
		//servsMenu = new JMenuItem("View Device-DFA (DDFA)");
		//servsMenu.addActionListener(new ViewServicesGraphListener(this));
		//servMenu = new JMenu("View State Machines");
		platform = new JMenu("Platform");
		dbaMenu = new JMenuItem("Set Device Base Address...");
		kpMenu = new JMenuItem("Set Kernel Path...");
		vppMenu = new JMenuItem("Set Virtual Platform Path...");
		gsMenu = new JMenuItem("General Settings...", new ImageIcon("imgs/rodad.png"));
		about = new JMenu("About");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		newMenu.setMnemonic(KeyEvent.VK_N);
		openMenu.setMnemonic(KeyEvent.VK_O);
		saveMenu.setMnemonic(KeyEvent.VK_S);
		saveAsMenu.setMnemonic(KeyEvent.VK_A);
		quitMenu.setMnemonic(KeyEvent.VK_Q);
		menuBar.add(fileMenu);
		fileMenu.add(newMenu);
		fileMenu.add(openMenu);
		fileMenu.add(new Separator());
		fileMenu.add(saveMenu);
		fileMenu.add(saveAsMenu);
		fileMenu.addSeparator();
		fileMenu.add(newFileMenu);
		fileMenu.add(openFileMenu);
		fileMenu.add(addFileMenu);
		fileMenu.add(new Separator());
		fileMenu.add(quitMenu);

		menuBar.add(devc);
		devc.setMnemonic(KeyEvent.VK_D);
		parseMenu.setMnemonic(KeyEvent.VK_P);
		devc.add(parseMenu);

		menuBar.add(ddriver);
		ddriver.add(gendrvMenu);
		ddriver.add(kernelMenu);

		menuBar.add(autom);
		autom.add(genMenu);
		autom.add(mainMenu);
		//autom.add(servsMenu);
		//autom.add(servMenu);
		autom.add(langMenu);
		langMenu.add(Clng);
		langMenu.add(Cpplng);
		langMenu.add(veriloglng);
		langMenu.add(svlng);
		langMenu.add(vhdllng);

		menuBar.add(platform);
		platform.add(dbaMenu);
		platform.add(kpMenu);
		platform.add(vppMenu);
		platform.addSeparator();
		platform.add(gsMenu);

		menuBar.add(about);
		return menuBar;
	}
	
	/*public void createServiceMenu(DDFA ddfa){
		//servMenu.removeAll();
		//SDFAMenu serv;
		JMenu serv;
		JMenu pos;
		JMenu pre;
		SDFA sdfa;
		for (String sdfaName : ddfa.getStates().keySet()) {
			if (ddfa.getStates().get(sdfaName) instanceof SDFA){
				sdfa = (SDFA) ddfa.getStates().get(sdfaName);
				if (sdfa.isIdle()) continue;
				 
			//}
			
			/*for (String service : tdevcGen.getServices().keySet()) {* /
				serv = new JMenu(sdfa.getService().getName());
				serv.add(new SDFAMenu(sdfa,this));//new JMenu(service);
				if (sdfa.getService().getPreServices().size()>0){
					pre =  new JMenu("Pre Services");
					serv.add(pre);
					for (String preServ : sdfa.getService().getPreServices().keySet()) {
						pre.add(new JMenuItem(preServ));
					}
				}
				if (sdfa.getService().getPosServices().size()>0){
					pos = new JMenu("Pos Services");
					for (String posServ : sdfa.getService().getPosServices().keySet()) {
						pos.add(new JMenuItem(posServ));
					}
					serv.add(pos);
				}
				//servMenu.add(serv);
			}
		}
	}*/

	private void createLayout(){
		desktop = new JDesktopPane();
		////codeEditor = new JEditorPane();
		////scrPane = new JScrollPane(codeEditor);
		////codeEditor.addCaretListener(new ChangeFileListener(this));
		////codeEditor.addKeyListener(new ChangeFileListener(this));
		mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		terminalPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		/*left.setLayout(new BorderLayout());
		right.setLayout(new BorderLayout());*/
 		elementsTree = new JTree();
		model = (DefaultTreeModel) elementsTree.getModel();
		rootElem = new DefaultMutableTreeNode("Project...");
		model.setRoot(rootElem);
		Container c = this.getContentPane();
		mainPane.setResizeWeight(0.20);
		mainPane.setOneTouchExpandable(true);
		mainPane.setContinuousLayout(true);
		//c.add(mainPane);
		mainPane.setLeftComponent(new JScrollPane(elementsTree));
		mainPane.setRightComponent(desktop);
		
		terminal = new TextArea();
		terminal.setEditable(false);
		terminalPane.setResizeWeight(0.95);
		terminalPane.setOneTouchExpandable(true);
		terminalPane.setContinuousLayout(true);
		c.add(terminalPane);
		terminalPane.setLeftComponent(mainPane);
		terminalPane.setRightComponent(terminal);		

		elementsTree.setVisible(true);
		elementsTree.addMouseListener(new DeviceTreeClickListener(elementsTree, this));
		DefaultSyntaxKit.initKit();
		////c.add(scrPane);
		statusBar = new JLabel();
		c.add(statusBar,BorderLayout.SOUTH);
		this.terminalMsgln("Welcome to the TDevC Editor!");
		this.setJMenuBar(createMenu());
		c.doLayout();
		////codeEditor.setContentType("text/devc");
		//codeEditor.setText("");
		//public static void main(String[] args) {\n}");

		this.setExtendedState(MAXIMIZED_BOTH);
		this.setVisible(true);  
	}
	

	public void openProject(File file){
		if (currentProject == null){
			currentProject = new TerminusProject(file);
			currentTDevCCodeEditor = new HashMap<String, TDevCCodeEditor>();
			this.setTitle("Macieira TDevCGen :: Project " + currentProject.getName());
			terminalMsgln("Project " + currentProject.getName() + " opened successfully");
			autom.setText(currentProject.getName());
			rootElem = currentProject.generateProjectTree();
			model.setRoot(rootElem);
			model.nodeChanged(rootElem);
		}else{}
	}
	
	public void addDevCFile(TDevCFile tDevCfile){
		JInternalFrame iFrame;
		Container iFrameC;
		JScrollPane scrPane;
		Container c = this.getContentPane();
		try {
			if (tDevCfile.getFile()!=null){
				/*StringBuffer sb = new StringBuffer();
				this.currentTDevCFile.put(tDevCfile.getName(), tDevCfile);
				BufferedReader br = new BufferedReader(new FileReader(tDevCfile.getFile()));
				while (br.ready()){
					sb.append(br.readLine()+"\n");
				}*/
				iFrame = new JInternalFrame();
				iFrame.setResizable(true);
				iFrame.setMaximizable(true);
				iFrame.setTitle(tDevCfile.getName());
				iFrameC = iFrame.getContentPane();
				desktop.add(iFrame);
				codeEditor = new JEditorPane();
				scrPane = new JScrollPane(codeEditor);
				iFrameC.add(scrPane);
				iFrame.setSize(300, 300);
				iFrame.setMaximum(true);
				iFrame.setVisible(true);
				codeEditor.setContentType("text/devc");
				codeEditor.setVisible(true);
				
				codeEditor.setText(tDevCfile.getSource());
				//DevCCodeEditor tdevcEdt = new TDevCCodeEditor(tDevCfile);
				//codeEditor.addKeyListener(new ChangeFileListener(this, tdevcEdt));
				//this.currentTDevCFile.put(tdevcEdt.gettDevCfile().getName(), tdevcEdt);
				setSavedTitle();
				c.doLayout();
			}else{
				//this.codeEditor.setText("");
				this.setTitle(generateTitle());
				this.terminalMsgln("New DevC");
			}
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
	
	public void saveProject(){
		try{
			this.currentProject.saveFiles();
			enableOnOpenFile();
			terminalMsgln("Project " + currentProject.getName() + " saved successfully.");
		}catch(IOException e){
			terminalMsgln("Fail during saving project " + currentProject.getName() + ": " + e.getMessage());
		}
	}

	public String getcodeEditorContent(String editorName){
		return this.currentTDevCCodeEditor.get(editorName).getCodeEditor().getText();
	}

	/*public File getCurrentDevCFile() {
		return currentTDevCFile;
	}*/

	public void doParse(){
		try{
			this.currentProject.doParse();
			this.terminalMsgln("Success!");
			this.enableOnParser();
		}catch (Exception e){
			this.terminalMsgln("Errors during parsing: " + e.getMessage());
			e.printStackTrace();
		}
		generateProjectTree();
	}
	
	public void generateProjectTree(){
		this.rootElem = currentProject.generateProjectTree();
		model.setRoot(this.rootElem);
		model.nodeChanged(this.rootElem);
	}
	
	public static void refreshProjectTree(DefaultMutableTreeNode node){
		model.reload(node);
	}
	
	public static void refreshProjectTree(){
		model.reload();
	}
	
	/*@Deprecated
	public void showDeviceFSMGraph(DDFA ddfa){
		try{
			//tdevcGen.generateServiceDependenciesGraph(this.currentDevCFile.getAbsolutePath() + ".dot");
			Process p = Runtime.getRuntime().exec("devc_samples/clear devc_samples");  
			p.waitFor();
			FileManagement.createDotFile(ddfa.getDFAid() , "devc_samples", ddfa.toString(), true);
			p = Runtime.getRuntime().exec("devc_samples/gengraph");
			//new GraphViewer(tdevcGen.generateServiceDependenciesGraph(this.currentDevCFile.getAbsolutePath() + ".dot"));
		}catch(IOException ioe){
			ioe.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Deprecated
	public void showMainsGraph(DDFA ddfa){
		try{
			//tdevcGen.generateServiceDependenciesGraph(this.currentDevCFile.getAbsolutePath() + ".dot");
			Process p = Runtime.getRuntime().exec("devc_samples/clear devc_samples");  
			p.waitFor();
			DDFA expe;
			expe = ddfa.generateExpandedDDFA(); 
			FileManagement.createDotFile(expe.getDFAid() + "_expanded" , "devc_samples", expe.toString(), true);
			expe.NDFAtoDFA();
			FileManagement.createDotFile(expe.getDFAid() + "_DFA" , "devc_samples", expe.toString(), true);
			p = Runtime.getRuntime().exec("devc_samples/gengraph");
			//new GraphViewer(tdevcGen.generateServiceDependenciesGraph(this.currentDevCFile.getAbsolutePath() + ".dot"));
		}catch(IOException ioe){
			ioe.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/	
	
	
	public static void main(String[] args) {
		getInstance();
	}
	
	public void closeWindow(JInternalFrame frame){
		//pergunta se quer fechar!
		this.windows.remove(frame.getTitle());
		this.desktop.remove(frame);
	}
	
	public void openWindow(JInternalFrame frame){
		try{
			if (this.windows.containsKey(frame.getTitle())){
				//pergunta se quer atualizar
				this.windows.get(frame.getTitle()).setSelected(true);
			}else{
				this.windows.put(frame.getTitle(), frame);
				if (frame instanceof TDevCCodeEditor){
					this.currentTDevCCodeEditor.put(((TDevCCodeEditor) frame).gettDevCfile().getName(), (TDevCCodeEditor) frame);
				}
				this.desktop.add(frame);
			}
		}catch (PropertyVetoException pve) {
			pve.printStackTrace();
		}
	}
	
	public static void terminalMsg(String msg, boolean status) {
		terminal.append(msg);
		if (status) {setStatus(msg);}
		terminal.setCaretPosition(terminal.getText().length() - 1);
		//terminal.update(terminal.getGraphics());
		terminal.doLayout();
		terminal.repaint();
	}
	
	public static void terminalMsg(String msg) {
		terminalMsg(msg,false);
	}
	
	public static void terminalMsgln(String msg) {
		terminalMsg(msg + "\n",false);
	}
	
	public static void terminalMsgln(String msg, boolean status) {
		terminalMsg(msg + "\n");
	}

}
