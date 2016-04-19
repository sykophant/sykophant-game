package Textures.Effects;

/**
 * 
 * These are usually used for the particle emitters
 * 
 * @author Andy
 *
 */
public enum EffectTexture {

	FLAME("flame.png", 2,2),
	LIGHTNINGBALL("lightningball.png", 4,4),
	NOVA("nova.png",1,10),
	CLOUDS("clouds.png",5,2),
	SMOKE("smoke.png",15,1),
	LEAF("leaf.png",3,3),
	
	;
	
	String filename; 
	int xImages; 
	int yImages;
	EffectTexture(String filename, int xImages, int yImages)
	{
		this.filename=filename;
		this.xImages = xImages;
		this.yImages = yImages;
		
		
	}
	public String getFileName() {
		return filename;
	}
	public int getXImages() {
		return xImages;
	}
	public int getYImages() {
		return yImages;
	}
	
}
