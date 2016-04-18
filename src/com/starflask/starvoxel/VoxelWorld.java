package com.starflask.starvoxel;

import com.badlogic.ashley.core.Entity;
import com.starflask.renderable.NodeComponent;
import com.starflask.renderable.PositioningComponent;
import com.starflask.voxelmagica.VoxelMagicaImporter;
import com.starflask.voxelmagica.VoxelMagicaImporter.VoxImporterListener;

public class VoxelWorld extends Entity implements VoxImporterListener{
	
	
	
	VoxelTerrain terrain;
	
	public void build() {
		this.add(new NodeComponent() );
		terrain = new VoxelTerrain(this);
		
		VoxelMagicaImporter importer = new VoxelMagicaImporter(this );
		importer.readVoxelMagicaModel("");
		 
		terrain.build();
		
		
		
		
	}

	public PositioningComponent getCameraPosition() {
	 
		return new PositioningComponent(); //for chunk LOD distance calcs.. should be the camera pos
	}

	
	
	
	@Override
	public void blockConstructed(int sizex, int sizey, int sizez, int x, int y, int z, int colorIndex) {
		
		for(int dx=0;dx<sizex;dx++)
		{
			for(int dy=0;dy<sizey;dy++)
			{
				for(int dz=0;dz<sizez;dz++)
				{  
					 terrain.setCubeType(x+dx,y+dy,z+dz,colorIndex);		 
				}
			}
			
		}
		
	}

	@Override
	public void setColorPalette(int[] voxcolors) {
		// TODO Auto-generated method stub
		
	} 
	
	
}
