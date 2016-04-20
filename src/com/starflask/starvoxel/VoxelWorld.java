package com.starflask.starvoxel;

import com.badlogic.ashley.core.Entity;
import com.jme3.app.Application;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
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
		//System.out.println( System.getProperty("user.home") + "/workspace/UltraBlackBloodDeath/assets/monu9.vox" );
		  importer.readVoxelMagicaModel(System.getProperty("user.home") + "/workspace/UltraBlackBloodDeath/assets/monu1.vox");
		
		//blockConstructed( 1,1,1,1);
		//blockConstructed( 1,0,1,1); 
		  //left and right sides never render in greedy?
		  
		terrain.build();
		
		app.getTerminalState().log("Terrain built");
		
	/*	AmbientLight scatteredlight;
		scatteredlight = new AmbientLight();
		scatteredlight.setColor(ColorRGBA.White);
		 this.getComponent(NodeComponent.class).addLight(scatteredlight);
		 
		DirectionalLight sun = new DirectionalLight();
	    sun.setDirection(new Vector3f(1,1,-2).normalizeLocal());
	    sun.setColor(ColorRGBA.Red);
	    this.getComponent(NodeComponent.class).addLight(sun);
	    
	    DirectionalLight moon = new DirectionalLight();
	    moon.setDirection(new Vector3f(-1,0,2).normalizeLocal());
	    moon.setColor(ColorRGBA.Blue);
	    this.getComponent(NodeComponent.class).addLight(moon);
	    */
	    
	    
	    
	    FilterPostProcessor fpp=new FilterPostProcessor(app.getAssetManager());
        BloomFilter bf=new BloomFilter(BloomFilter.GlowMode.Objects);
        fpp.addFilter(bf);
        app.getViewPort().addProcessor(fpp);
        
        //make a fire-spark particle emitter on ''fire'' blocks and make those particles glow with 'glowcolor'
        
        
        //bake shadows into voxels like sands of osiris - then make objects cast dynamic shadows !
		
	}

	
	public void update(float tpf)
	{
		terrain.update(tpf);
	}
	
	public PositioningComponent getCameraPosition() {
	 
		return new PositioningComponent(); //for chunk LOD distance calcs.. should be the camera pos
	}

	
	
	
	@Override
	public void blockConstructed(  int x, int y, int z, int colorIndex) {
		 	byte blockTypeId = (byte)( colorIndex + 1); //so 0 is air
			terrain.setCubeType(x,y,z,blockTypeId);	
					 
			 
	}



	
	
	@Override
	public void setColorPalette(int[] voxcolors) {
		terrain.setColorPalette(voxcolors);
		
	}


	@Override
	public void setStructSize(int sizex, int sizey, int sizez) {
		// TODO Auto-generated method stub
		
	} 
	
	
	
	public AssetLibrary getAssetLibrary() {
		return app.getAssetLibrary();
	}
	
}
