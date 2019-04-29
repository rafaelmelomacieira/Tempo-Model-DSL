package br.ufpe.cin.greco.devc.ui.listener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import br.ufpe.cin.greco.devc.ui.Editor;

public class InternalFrameCloseListener implements InternalFrameListener {
	
	private JInternalFrame frame;
	private Editor editor;
	
	public InternalFrameCloseListener(JInternalFrame frame, Editor editor) {
		this.editor = editor;
		this.frame = frame;
	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		editor.closeWindow(frame);
		
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
