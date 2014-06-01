package org.tde.tdescenariodeveloper.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.movsim.autogen.VehicleType;
import org.tde.tdescenariodeveloper.eventhandling.SimulationListener;
import org.tde.tdescenariodeveloper.updation.DataToViewerConverter;

public class SimulationPanel extends JPanel {
	private static final long serialVersionUID = -786294065529819367L;
	MovsimConfigContext mvCxt;
	JCheckBox withSeed,crashExit;
	JTextField timeStep,duration,seed;
	JPanel trafficCompositionPnl,roadsPnl;
	JButton add,addRoad;
	public SimulationPanel(MovsimConfigContext movsimConfigPane) {
		setLayout(new GridBagLayout());
		add=new JButton("<html><i>New type</i></html>",TDEResources.getResources().getAddIcon());
		addRoad=new JButton("<html><i>New Road</i></html>",TDEResources.getResources().getAddIcon());
		this.mvCxt=movsimConfigPane;
		timeStep=new JTextField(10);
		timeStep.setToolTipText("Tell simulator what time-jump size should be");
		duration=new JTextField(10);
		duration.setToolTipText("Running time of the simulation");
		seed=new JTextField(10);
		seed.setToolTipText("An integer used for randomization");
		withSeed=new JCheckBox("Use seed");
		crashExit=new JCheckBox("Exit on crash");
		crashExit.setToolTipText("Stop simulation if traffic crash occurs");
		trafficCompositionPnl=new JPanel(new GridBagLayout());
		roadsPnl=new JPanel(new GridBagLayout());
		JPanel prop=new JPanel(new GridBagLayout());
		
		JPanel trPnl=new JPanel(new GridBagLayout());
		JPanel rdPnl=new JPanel(new GridBagLayout());
		
		JScrollPane pr=new JScrollPane(prop),rds=new JScrollPane(rdPnl),trfc=new JScrollPane(trPnl);
		pr.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1, true),"Simulation properties" , TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, Color.DARK_GRAY));
		rds.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1, true),"Road customizations" , TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, Color.DARK_GRAY));
		trfc.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1, true),"Generic Traffic composition" , TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, Color.DARK_GRAY));
		
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		gbc.insets=new Insets(2, 10, 2, 10);
		gbc.weightx=1;
		
		
		prop.add(new JLabel("Time step"),gbc);
		gbc.weightx=4;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		prop.add(timeStep,gbc);

		
		gbc.weightx=1;
		gbc.gridwidth=1;
		prop.add(new JLabel("Duration"),gbc);
		gbc.weightx=4;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		prop.add(duration,gbc);

		gbc.weightx=1;
		gbc.gridwidth=1;
		prop.add(new JLabel("Seed (any number)"),gbc);
		gbc.weightx=4;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		prop.add(seed,gbc);

		gbc.weightx=0;
		gbc.gridwidth=1;
		gbc.weighty=1;
		gbc.gridheight=GridBagConstraints.REMAINDER;
		gbc.anchor=GridBagConstraints.NORTH;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		prop.add(withSeed,gbc);
		
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		prop.add(crashExit,gbc);
		
		GridBagConstraints g2=new GridBagConstraints();
		g2.anchor=GridBagConstraints.NORTH;
		g2.weightx=1;
		g2.weighty=1;
		g2.fill=GridBagConstraints.BOTH;
		g2.gridwidth=GridBagConstraints.REMAINDER;
		trPnl.add(trafficCompositionPnl,g2);
		rdPnl.add(roadsPnl,g2);
		g2.fill=GridBagConstraints.HORIZONTAL;
		g2.gridheight=GridBagConstraints.REMAINDER;
		trPnl.add(add,g2);
		rdPnl.add(addRoad,g2);
		
		
		
		gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		gbc.insets=new Insets(5, 5, 5, 5);
		gbc.weightx=1;
		
		add(pr,gbc);
		gbc.weightx=1;
		add(trfc,gbc);
		gbc.weightx=2;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		add(rds,gbc);
		trfc.setMinimumSize(new Dimension(100,150));
		SimulationListener sl=new SimulationListener(mvCxt,timeStep, duration, seed, crashExit, withSeed,add,addRoad);
		timeStep.getDocument().addDocumentListener(sl);
		duration.getDocument().addDocumentListener(sl);
		seed.getDocument().addDocumentListener(sl);
		crashExit.addItemListener(sl);
		withSeed.addItemListener(sl);
		add.addActionListener(sl);
		addRoad.addActionListener(sl);
		sl.setBlocked(false);
	}
	public void updateSimPanel(){
		duration.setText(mvCxt.getMovsim().getScenario().getSimulation().isSetDuration()?mvCxt.getMovsim().getScenario().getSimulation().getDuration()+"":"");
		timeStep.setText(mvCxt.getMovsim().getScenario().getSimulation().isSetTimestep()?mvCxt.getMovsim().getScenario().getSimulation().getTimestep()+"":"");
		seed.setText(mvCxt.getMovsim().getScenario().getSimulation().isSetSeed()?mvCxt.getMovsim().getScenario().getSimulation().getSeed()+"":"");
		crashExit.setSelected(mvCxt.getMovsim().getScenario().getSimulation().isSetCrashExit()?mvCxt.getMovsim().getScenario().getSimulation().isCrashExit():false);
		withSeed.setSelected(mvCxt.getMovsim().getScenario().getSimulation().isSetWithSeed()?mvCxt.getMovsim().getScenario().getSimulation().isWithSeed():false);
		
		((SimulationListener)add.getActionListeners()[0]).setRdList(mvCxt.getMovsim().getScenario().getSimulation().getRoad());
		((SimulationListener)addRoad.getActionListeners()[0]).setTc(mvCxt.getMovsim().getScenario().getSimulation().getTrafficComposition());
		
		DataToViewerConverter.fillTrafficCompositionPnl(mvCxt, trafficCompositionPnl,mvCxt.getMovsim().getScenario().getSimulation().getTrafficComposition());
		DataToViewerConverter.fillroadsPnl(mvCxt,roadsPnl,mvCxt.getMovsim().getScenario().getSimulation().getRoad());
	}

	
	public void updateTrafficCompostionLabel(String oldlbl, String newlbl) {
		for(VehicleType vt:mvCxt.getMovsim().getScenario().getSimulation().getTrafficComposition().getVehicleType())
			if(vt.getLabel().equals(oldlbl))vt.setLabel(newlbl);
		updateSimPanel();
	}
}
