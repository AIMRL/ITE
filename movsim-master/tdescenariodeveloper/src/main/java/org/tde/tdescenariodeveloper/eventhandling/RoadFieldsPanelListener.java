package org.tde.tdescenariodeveloper.eventhandling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import org.movsim.network.autogen.opendrive.OpenDRIVE.Junction;
import org.movsim.network.autogen.opendrive.OpenDRIVE.Junction.Connection;
import org.movsim.network.autogen.opendrive.OpenDRIVE.Road;
import org.movsim.simulator.roadnetwork.RoadSegment;
import org.tde.tdescenariodeveloper.ui.JunctionsPanel;
import org.tde.tdescenariodeveloper.ui.RoadContext;
import org.tde.tdescenariodeveloper.updation.Conditions;
import org.tde.tdescenariodeveloper.updation.LanesUpdater;
import org.tde.tdescenariodeveloper.updation.LinkUpdater;
import org.tde.tdescenariodeveloper.updation.RoadFieldsUpdater;
import org.tde.tdescenariodeveloper.utils.GraphicsHelper;
import org.tde.tdescenariodeveloper.utils.RoadNetworkUtils;
import org.tde.tdescenariodeveloper.validation.RoadFieldsValidator;

public class RoadFieldsPanelListener implements DocumentListener,ActionListener,Blockable {
	RoadContext rdCxt;
	RoadFieldsUpdater roadFieldsUpdater;
	RoadFieldsValidator validator;
	private boolean blocked=true;
	public RoadFieldsPanelListener(RoadContext roadPropertiesPanel) {
		rdCxt=roadPropertiesPanel;
		this.roadFieldsUpdater=new RoadFieldsUpdater(rdCxt);
		validator=new RoadFieldsValidator(rdCxt);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(blocked)return;
		JComboBox<String>src=null;
		if(e.getSource() instanceof JComboBox<?>)src=(JComboBox<String>)e.getSource();
		JButton srcBtn=null;
		if(e.getSource() instanceof JButton)srcBtn=(JButton)e.getSource();
		if(src==rdCxt.getRdFldPnl().getCbJunction() && ((String)src.getSelectedItem())!=null && !((String)src.getSelectedItem()).equals("None")){
			boolean []isValid=validator.isJunctionValid();
			if(isValid[0]){
				if(isValid[1])
					roadFieldsUpdater.updateRoadJunction();
				else{
					GraphicsHelper.showToast("Road "+rdCxt.getSelectedRoad().userId()+" is not referenced in Junction "+rdCxt.getRdFldPnl().getCbJunction().getSelectedItem(), rdCxt.getToastDurationMilis());
					String jnc=rdCxt.getSelectedRoad().getOdrRoad().getJunction();
					if(jnc.equals("-1"))jnc="None";
					rdCxt.getRdFldPnl().getCbJunction().setSelectedItem(jnc);
				}
				
			}
			else{
				GraphicsHelper.showMessage("Junction doesn't exist");
			}
		}else if(srcBtn==rdCxt.getRdFldPnl().getAddRoad()){
			Road r=RoadNetworkUtils.getRoad(rdCxt.getRn().getOdrNetwork());
			r.getPlanView().getGeometry().get(0).setX(-300+Math.random()*600);
			r.getPlanView().getGeometry().get(0).setY(-300+Math.random()*600);
			if(rdCxt.getRn().getOdrNetwork().getRoad().add(r)){
				RoadNetworkUtils.refresh(rdCxt);
			}else GraphicsHelper.showToast("Road "+rdCxt.getSelectedRoad().userId()+" couldn't be remvoed", rdCxt.getToastDurationMilis());
		}else if(srcBtn==rdCxt.getRdFldPnl().getRemoveRoad())
			LinkUpdater.removeRoad(rdCxt);
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
		Document doc=e.getDocument();
		if(rdCxt.getRdFldPnl().getTfName().getDocument()==doc){
			if(!Conditions.isValid(rdCxt.getRdFldPnl().getTfName(), rdCxt.getSelectedRoad().getRoadName()))
				return;
			roadFieldsUpdater.updateRoadName();
			rdCxt.updateGraphics();
		}
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
}
