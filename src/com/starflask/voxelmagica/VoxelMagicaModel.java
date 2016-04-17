package com.starflask.voxelmagica;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class VoxelMagicaModel {
	String magicNumber;
	int version;
	
	static String quickPath = "C:\\Users\\Andy\\Documents\\dev\\UBBD\\MagicaVoxel-0.97.2\\vox\\castle.vox";
	
	public VoxelMagicaModel(String filePath)
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
			VoxelMagicaModel model = new VoxelMagicaModel(quickPath);
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
		    VoxelMagicaModel model = new VoxelMagicaModel(selectedFile.getAbsolutePath());
	          
		} 
	 
	    frame.pack();
	    frame.setVisible(true);
	}
	
	
	public void readVoxelMagicaModel(String filePath)
	{	
		 InputStream is = null;
	      byte[] buffer=new byte[4];
	      char c;
	      
	      try{
	    	  //FOLLOW https://ephtracy.github.io/index.html?page=mv_vox_format
	    	  
	         // new input stream created
	         is = new FileInputStream(filePath);
	         
	         System.out.println("Characters printed:");
	         
	         magicNumber = bytesToString(readFourBytes( is ));
	         version = littleEndianBytesToInt(readFourBytes( is ));
	         
	         System.out.println("magic num is "+magicNumber);
	         System.out.println("version is "+version);
 
	         
	         RootVoxelChunk mainChunk = new RootVoxelChunk( is );
	         
	         while(is.read(buffer) != -1)
	         {
	        	 
	        	 
	        	
		       
	        	 
		        
	         }
	         
	        
	      }catch(Exception e){
	         
	         // if any I/O error occurs
	         e.printStackTrace();
	      }finally{
	         
	         // releases system resources associated with this stream
	         if(is!=null)
	         {
	            try {
					is.close();
				} catch (IOException e) { 
					e.printStackTrace();
				}
	         }
	      }
	   }

	private byte[] readFourBytes(InputStream is) throws Exception {
		 byte[] buffer=new byte[4];
		 
		 is.read(buffer);
		 
		 char c;
		 
		  // for each byte in the buffer.. little endian
        for(byte b:buffer)
        {
           // convert byte to character
           c=(char)b;
           
           // prints character 
           System.out.print(c + " - ");
           System.out.print(String.format("0x%02X", b));
           System.out.print("\n");
        }
        
        
        
		return buffer;
	}
	
	private static int littleEndianBytesToInt(byte b[])
	{		
		return   (  ((b[3] & 0xFF) << 24) | ((b[2] & 0xFF) << 16)
       	        | ((b[1] & 0xFF) << 8) | (b[0] & 0xFF));
				
	}
	
	
	private static String bytesToString(byte b[])
	{		
		return new String(b);
	}
	
	
	private class RootVoxelChunk
	{
		String chunkId;
		int n_sizeOfContents;
		int m_sizeOfChildren;
		
		VoxelData[] voxels;
		ChildVoxelChunk[] chunks;
		
		private RootVoxelChunk(InputStream is) throws Exception
		{
			
			  
			chunkId = bytesToString(readFourBytes( is ));
			n_sizeOfContents = littleEndianBytesToInt(readFourBytes( is ));
			m_sizeOfChildren = littleEndianBytesToInt(readFourBytes( is ));
			
			System.out.println("id is "+ chunkId);
			System.out.println("n is "+ n_sizeOfContents);
			System.out.println("m is "+ m_sizeOfChildren);
			
			voxels = new VoxelData[n_sizeOfContents];
			chunks = new ChildVoxelChunk[m_sizeOfChildren];
			
			//read chunk contents
	        for(int i =0;i<n_sizeOfContents;i++)
	        {
	        	voxels[i] = new VoxelData(is);
	        }
	        
	        for(int i =0;i<m_sizeOfChildren; i+=chunks[i].getSizeInBytes() )
	        {
	        	chunks[i] = new ChildVoxelChunk(is);
	        	System.out.println( i );
	        }
	        
	        
			
		}
		
	}
		
		private class ChildVoxelChunk
		{
			String chunkId;
			int numVoxels; 
			
			VoxelData[] voxels;
			
			private ChildVoxelChunk(InputStream is) throws Exception
			{
				
				chunkId = bytesToString(readFourBytes( is ));
				numVoxels = littleEndianBytesToInt(readFourBytes( is ));
				
				System.out.println("child chunk id "+chunkId);
				System.out.println("child chunk num voxels "+numVoxels);
				
				voxels = new VoxelData[numVoxels];
				
				 
		        for(int i =0;i<numVoxels;i++)
		        {
		       // 	voxels[i] = new VoxelData(is);
		        } 
				
			}

			public int getSizeInBytes() {
				 
				return 4 + 4 + numVoxels*4;
			} 
		
		
		
	}
	
		// each voxel : 1 byte x 4 : x, y, z, colorIndex ) x numVoxels

		private class VoxelData 
		{
			int x;
			int y;
			int z;
			int colorIndex;
			
			private VoxelData(InputStream is) throws Exception
			{
				x = littleEndianBytesToInt(readFourBytes( is ));
				y = littleEndianBytesToInt(readFourBytes( is ));
				z = littleEndianBytesToInt(readFourBytes( is ));
				colorIndex = littleEndianBytesToInt(readFourBytes( is ));
			}
		
		}
	
	
}
