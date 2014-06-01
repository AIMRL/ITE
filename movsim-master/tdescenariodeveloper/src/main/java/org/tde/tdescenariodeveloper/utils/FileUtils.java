package org.tde.tdescenariodeveloper.utils;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.xml.bind.JAXBException;

import org.movsim.input.network.OpenDriveReader;
import org.movsim.xml.MovsimInputLoader;
import org.tde.tdescenariodeveloper.ui.MovsimConfigContext;
import org.tde.tdescenariodeveloper.ui.RoadContext;
import org.xml.sax.SAXException;

public class FileUtils {
	public static File chooseFile(final String type){
		JFileChooser fc=new JFileChooser(".");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return type+" Files";
			}
			
			@Override
			public boolean accept(File arg0) {
				return arg0.isDirectory() || arg0.getAbsolutePath().toLowerCase().endsWith("."+type);
			}
		});
		if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
			return fc.getSelectedFile();
		return null;
	}
	
	public static File saveFile(final String type){
		JFileChooser fc=new JFileChooser("..");
		fc.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return type+" Files";
			}
			
			@Override
			public boolean accept(File arg0) {
				return arg0.isDirectory() || arg0.getAbsolutePath().toLowerCase().endsWith("."+type);
			}
		});
		if(fc.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
			if(fc.getSelectedFile().getName().endsWith("."+type))
				return fc.getSelectedFile();
			else return new File(fc.getSelectedFile().getAbsolutePath()+"."+type);
		}
		return null;
	}
}
