package com.starflask.voxelmagica;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.starflask.util.Vector3Int;
import com.starflask.voxelmagica.VoxelMagicaImporter.VoxImporterListener;

public class VoxelMagicaModel implements VoxImporterListener{
	
	static VoxelMagicaImporter magicaImporter;
	
	public static final int VOXEL_SIZE = 4; //bytes
	String magicNumber;
	int version;
	
	static String quickPath = "C:\\Users\\Andy\\Documents\\dev\\UBBD\\MagicaVoxel-0.97.2\\vox\\castle.vox";
	
	public VoxelMagicaModel() 
	{
	
	}
	
	public void build(String filePath)
	{
		readVoxelMagicaModel(filePath);
	}
	
	public static void main(String args[])
	{ 
		if(quickPath == null)
		{
			importVoxelMagicaModel();
			
		}
		else
		{
			VoxelMagicaModel model = new VoxelMagicaModel();
			magicaImporter = new VoxelMagicaImporter(model,false );
			model.build(quickPath);
		}
	}
	
	
	public static void importVoxelMagicaModel()
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Select a file");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		    VoxelMagicaModel model = new VoxelMagicaModel();
		    magicaImporter = new VoxelMagicaImporter(model,false );
			model.build( selectedFile.getAbsolutePath() );
		} 
	 
	    frame.pack();
	    frame.setVisible(true);
	}
	
	
	public void readVoxelMagicaModel(String filePath)
	{	
		 DataInputStream is = null;
	      byte[] buffer=new byte[4];
	      char c;
	  
	    	   // new input stream created
	       
	         try {
	        	  is = new DataInputStream(new FileInputStream(filePath));
	 	         
				magicaImporter.readMagica(  is);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
	         
	   }
	
		List<BlockData> blocks = new ArrayList<BlockData>();
 
		@Override
		public void blockConstructed(int sizex, int sizey, int sizez, int x, int y, int z, int color) {
			blocks.add(new BlockData( sizex,  sizey,  sizez,  x,  y,  z,  color));
			System.out.println("placed block ");
			
		}
		
		class BlockData
		{
			Vector3Int size = new Vector3Int();
			Vector3Int loc = new Vector3Int();
			int color;
			
			public BlockData(int sizex, int sizey, int sizez, int x, int y, int z, int color)
			{
				size.set(sizex,sizey,sizez);
				loc.set(x,y,z);
				this.color=color;
			}
			
		}
	
	
}
