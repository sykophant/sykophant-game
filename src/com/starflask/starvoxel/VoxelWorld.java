package com.starflask.starvoxel;

import com.badlogic.ashley.core.Entity;
import com.jme3.app.Application;
import com.starflask.MonkeyApplication;
import com.starflask.assets.AssetLibrary;
import com.starflask.renderable.NodeComponent;
import com.starflask.renderable.PositioningComponent;
import com.starflask.states.TerminalState;
import com.starflask.voxelmagica.VoxelMagicaImporter;
import com.starflask.voxelmagica.VoxelMagicaImporter.VoxImporterListener;

public class VoxelWorld extends Entity implements VoxImporterListener{
	
	MonkeyApplication app;
	
	public VoxelWorld(Application app )
	{
		this.app= (MonkeyApplication) app;
	}
	
	VoxelTerrain terrain;
	
	public void build() {
		
		this.add(new NodeComponent() );
		terrain = new VoxelTerrain(this);
		
		this.getComponent(NodeComponent.class).attachChild( terrain.getComponent(NodeComponent.class) );
		
		VoxelMagicaImporter importer = new VoxelMagicaImporter(this );
		importer.readVoxelMagicaModel("");
		
		blockConstructed(5,3,2,1,1,1,1);
		 
		terrain.build();
		
		app.getTerminalState().log("Terrain built");
		
		
		
	}

	
	public void update(float tpf)
	{
		terrain.update(tpf);
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

	public AssetLibrary getAssetLibrary() {
		return app.getAssetLibrary();
	} 
	
	
}
