package com.starflask.assets;

import com.badlogic.ashley.core.Entity;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.material.Material;
import com.starflask.MonkeyApplication;
import com.starflask.util.EntityAppState;

public class AssetLoadState extends EntityAppState   {
	
	
	 
	
	@Override
    public void initialize(AppStateManager stateManager, Application app) {
      super.initialize(stateManager, app); 
      
       this.add(new AssetLibrary());
      
      
      load();
   }
 
	 
	
	public void load()
	{
		  
		 
		getAssetLibrary().registerAsset("console_font", getAssetManager().loadFont("Interface/Fonts/Default.fnt"));
		 
		getAssetLibrary().registerAsset("console_background",  new Material(getAssetManager(),  "Common/MatDefs/Misc/Unshaded.j3md")  );

		Material colorVoxels =  new Material(getAssetManager(),  "com/starflask/assets/MatDefs/Voxels/LowResColor.j3md");
		//colorVoxels.setBoolean("UseMaterialColors", true); 
		getAssetLibrary().registerAsset("terrain_material_untextured",  colorVoxels );
		
		getAssetLibrary().registerAsset("terrain_material_textured",  new Material(getAssetManager(),  "com/starflask/assets/MatDefs/Voxels/LowResTex.j3md")  );

	}



	protected AssetLibrary getAssetLibrary() {		 
		return this.getComponent(AssetLibrary.class);
	}
	

}
