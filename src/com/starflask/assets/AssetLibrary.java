package com.starflask.assets;
 
import java.util.HashMap;
import com.badlogic.ashley.core.Component;
import com.jme3.font.BitmapFont;
import com.jme3.material.Material;

public class AssetLibrary implements Component{
	
	
	HashMap<String, Object> assetIndex = new HashMap<String, Object>();

	public void registerAsset(String key, Object asset) {		 
		assetIndex.put(key,asset);
	}
	
	public Object findAsset(String key)
	{
		return assetIndex.get(key);
	}

	public BitmapFont findFont(String key) {	
		
		return (BitmapFont) findAsset(key);
	}

	public Material findMaterial(String key) {
	 
		return (Material) findAsset(key);
	}
	
	
	

}
