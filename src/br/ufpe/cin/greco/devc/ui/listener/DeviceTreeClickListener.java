package br.ufpe.cin.greco.devc.ui.listener;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import br.ufpe.cin.greco.devc.languageStructure.Register;
import br.ufpe.cin.greco.devc.languageStructure.TDCCheckerGenerator;
import br.ufpe.cin.greco.devc.languageStructure.ltl.Behavior;
import br.ufpe.cin.greco.devc.languageStructure.ltl.OrthoRegion;
import br.ufpe.cin.greco.devc.languageStructure.ltl.Violation;
import br.ufpe.cin.greco.devc.ui.Editor;
import br.ufpe.cin.greco.devc.ui.TDevCCodeEditor;
import br.ufpe.cin.greco.devc.ui.TDevCFile;

public class DeviceTreeClickListener implements MouseListener
{
	
	private Editor editor;
	private JTree tree;
	
	public DeviceTreeClickListener(JTree tree, Editor editor) {
		this.editor = editor;
		this.tree = tree;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try{
			if(e.getClickCount() == 2){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (node.getUserObject() instanceof OrthoRegion){
					OrthoRegion reg = (OrthoRegion) node.getUserObject();
					JInternalFrame img = new JInternalFrame(reg.getRegionName());
					BufferedImage myPicture = ImageIO.read(new File("./dotFiles/orthoReg_" + reg.getRegionName() + ".png"));
					JLabel picLabel = new JLabel(new ImageIcon(myPicture));
					img.setLayout(new BorderLayout());
					img.setSize(myPicture.getWidth() + 30,myPicture.getHeight() + 30);
					img.add(picLabel,BorderLayout.CENTER);
					img.addInternalFrameListener(new InternalFrameCloseListener(img, editor));
					img.setVisible(true);
					img.setClosable(true);
					this.editor.openWindow(img);
					img.setSelected(true);
				}else if(node.getUserObject() instanceof Register){
					Register reg = (Register) node.getUserObject();
					JInternalFrame img = new JInternalFrame(reg.getAlias());
					File regImg = new File("./dotFiles/register_" + reg.getAlias() + ".png");
					/*if (!regImg.exists()){
						TDCCheckerGenerator.getInstance().createRegisterDotFiles(reg);
					}
					while(!regImg.exists());*/
					BufferedImage myPicture = ImageIO.read(regImg);
					JLabel picLabel = new JLabel(new ImageIcon(myPicture));
					img.setLayout(new BorderLayout());
					img.setSize(myPicture.getWidth() + 30,myPicture.getHeight()+30);
					img.add(picLabel,BorderLayout.CENTER);
					img.addInternalFrameListener(new InternalFrameCloseListener(img, editor));
					img.setVisible(true);
					img.setClosable(true);
					this.editor.openWindow(img);
					img.setSelected(true);
				}else if(node.getUserObject() instanceof Violation){
					Violation reg = (Violation) node.getUserObject();
					JInternalFrame img = new JInternalFrame(reg.getName());
					File regImg = new File("./dotFiles/violation_" + reg.getName() + ".png");
					/*if (!regImg.exists()){
						TDCCheckerGenerator.getInstance().createRegisterDotFiles(reg);
					}
					while(!regImg.exists());*/
					BufferedImage myPicture = ImageIO.read(regImg);
					JLabel picLabel = new JLabel(new ImageIcon(myPicture));
					img.setLayout(new BorderLayout());
					img.setSize(myPicture.getWidth() + 30,myPicture.getHeight()+30);
					img.add(picLabel,BorderLayout.CENTER);
					img.addInternalFrameListener(new InternalFrameCloseListener(img, editor));
					img.setVisible(true);
					img.setClosable(true);
					this.editor.openWindow(img);
					img.setSelected(true);
				}else if(node.getUserObject() instanceof Behavior){
					Behavior reg = (Behavior) node.getUserObject();
					JInternalFrame img = new JInternalFrame(reg.getName());
					File regImg = new File("./dotFiles/behavior_" + reg.getName() + ".png");
					/*if (!regImg.exists()){
						TDCCheckerGenerator.getInstance().createRegisterDotFiles(reg);
					}
					while(!regImg.exists());*/
					BufferedImage myPicture = ImageIO.read(regImg);
					JLabel picLabel = new JLabel(new ImageIcon(myPicture));
					img.setLayout(new BorderLayout());
					img.setSize(myPicture.getWidth() + 30,myPicture.getHeight()+30);
					img.add(picLabel,BorderLayout.CENTER);
					img.addInternalFrameListener(new InternalFrameCloseListener(img, editor));
					img.setVisible(true);
					img.setClosable(true);
					this.editor.openWindow(img);
					img.setSelected(true);
				}else if(node.getUserObject() instanceof TDevCFile){
					JEditorPane codeEditor = new JEditorPane();
					TDevCFile tdevc = (TDevCFile) node.getUserObject();
					TDevCCodeEditor iFrame = new TDevCCodeEditor(tdevc,codeEditor);
					this.editor.openWindow(iFrame);
					Container iFrameC = iFrame.getContentPane();
					JScrollPane scrPane = new JScrollPane(codeEditor);
					iFrameC.add(scrPane);
					iFrame.setSize(300, 300);
					iFrame.setMaximum(true);
					iFrame.setVisible(true);
					codeEditor.setContentType("text/devc");
					codeEditor.setVisible(true);
					codeEditor.setText(tdevc.getSource());
					codeEditor.addKeyListener(new ChangeFileListener(this.editor,iFrame));
					//c.doLayout();
				}
			}
		}catch (PropertyVetoException pve) {
			pve.printStackTrace();
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
