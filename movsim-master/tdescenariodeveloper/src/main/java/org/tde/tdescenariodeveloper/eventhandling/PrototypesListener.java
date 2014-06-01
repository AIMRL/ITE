package org.tde.tdescenariodeveloper.eventhandling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import org.movsim.autogen.VehiclePrototypeConfiguration;
import org.tde.tdescenariodeveloper.ui.MovsimConfigContext;
import org.tde.tdescenariodeveloper.updation.Conditions;
import org.tde.tdescenariodeveloper.utils.GraphicsHelper;

public class PrototypesListener implements ActionListener, DocumentListener {
	private boolean blocked=true;
	private MovsimConfigContext mvCxt;
	private JButton removePrototype=null;
	JTextField label;
	private VehiclePrototypeConfiguration vpc;
	private JTextField lengthTf,widthTf,maxDec;
	public PrototypesListener(MovsimConfigContext mvCxt) {
		this.mvCxt=mvCxt;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(blocked)return;
		JButton srcBtn=null;
		if(e.getSource() instanceof JButton)srcBtn=(JButton)e.getSource();
		if(srcBtn==removePrototype){
			mvCxt.getMovsim().getVehiclePrototypes().getVehiclePrototypeConfiguration().remove(vpc);
			mvCxt.updatePanels();
		}
	}

	public void setRemovePrototype(JButton removePrototype) {
		this.removePrototype = removePrototype;
	}

	public void setLabel(JTextField label) {
		this.label=label;
	}

	public void setVpc(VehiclePrototypeConfiguration vpc) {
		this.vpc = vpc;
	}

	public void setLengthTf(JTextField lengthTf) {
		this.lengthTf=lengthTf;
	}

	public void setWidthTf(JTextField widthTf) {
		this.widthTf=widthTf;
	}

	public void setMaxDec(JTextField maxDec) {
		this.maxDec=maxDec;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		textChanged(e);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		textChanged(e);		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		textChanged(e);		
	}
	public void textChanged(DocumentEvent e){
		if(blocked)return;
		Document src=e.getDocument();
		if(src==label.getDocument()){
			final String newlbl=label.getText();
			if(!Conditions.existsLabelInVPC(newlbl,mvCxt)){
				String oldlbl=vpc.getLabel();
				vpc.setLabel(newlbl);
				mvCxt.getSimulation().updateTrafficCompostionLabel(oldlbl,newlbl);
			}
			else
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						label.setText(newlbl+((int)(Math.random()*1000)));
					}
				});
		}
		else if(src==lengthTf.getDocument()){
			try{
				double d=Double.parseDouble(lengthTf.getText());
				GraphicsHelper.makeBlack(lengthTf);
				vpc.setLength(d);
			}catch(NumberFormatException e2){
				GraphicsHelper.makeRed(lengthTf);
			}
		}else if(src==widthTf.getDocument()){
			try{
				double d=Double.parseDouble(widthTf.getText());
				GraphicsHelper.makeBlack(widthTf);
				vpc.setWidth(d);
			}catch(NumberFormatException e2){
				GraphicsHelper.makeRed(widthTf);
			}
		}else if(src==maxDec.getDocument()){
			try{
				double d=Double.parseDouble(maxDec.getText());
				GraphicsHelper.makeBlack(maxDec);
				vpc.setMaximumDeceleration(d);
			}catch(NumberFormatException e2){
				GraphicsHelper.makeRed(maxDec);
			}
		}
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

}
