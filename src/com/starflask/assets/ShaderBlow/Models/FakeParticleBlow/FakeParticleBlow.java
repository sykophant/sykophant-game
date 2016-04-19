package ShaderBlow.Models.FakeParticleBlow;

import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.TangentBinormalGenerator;

import mygame.MainApp;
import mygame.SharedData;

public class FakeParticleBlow extends Node{
	
  MainApp mainapp;
	public FakeParticleBlow(MainApp mainapp,String type){
		this.mainapp=mainapp;
		init(type);
		
	}

	private void init(String type) {
		/*// Create the material
        final Material mat = new Material(mainapp.getAssetManager(),"Models/FakeParticleBlow/FakeParticleBlow.j3md");
 
        // Create the mask texture to use
        final Texture maskTex = mainapp.getAssetManager().loadTexture("Models/FakeParticleBlow/mask.png");
        mat.setTexture("MaskMap", maskTex);
 
        // Create the texture to use for the spatial.
        final Texture aniTex = mainapp.getAssetManager().loadTexture("Models/FakeParticleBlow/particles.png");
        aniTex.setWrap(WrapMode.MirroredRepeat); // NOTE: Set WrapMode = MirroredRepeat in order to animate the texture
        mat.setTexture("AniTexMap", aniTex); // Set texture
 
        mat.setFloat("TimeSpeed", 2); // Set animation speed
 
        mat.setColor("BaseColor", ColorRGBA.Green.clone()); // Set base color to apply to the texture
 
      
        mat.setBoolean("Change_Direction", true); // Change direction of the texture animation
 
        mat.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off); // Allow to see both sides of a face
      
        
       // mat.getAdditionalRenderState().setBlendMode(BlendMode.Additive);
 
       // final ColorRGBA fogColor = ColorRGBA.Black.clone();
        
         mat.getAdditionalRenderState().setBlendMode(BlendMode.AlphaAdditive);
         ColorRGBA fogColor = ColorRGBA.Blue.clone();
         
         int size = 3;
         
         
         if(type.equalsIgnoreCase("fireball")){
        	 mat.setBoolean("Animation_X", true);// Enable X axis animation
        	 mat.setBoolean("Animation_Y", true);
        	 fogColor = ColorRGBA.Orange.clone();
        	 size = 5;
         }
            
        // mat.setBoolean("Animation_Y", true); // Enable Y axis animation
         
        
        
        fogColor.a = 10; // fogColor's alpha value is used to calculate the intensity of the fog (distance to apply fog)
        mat.setColor("FogColor", fogColor); // Set fog color to apply to the spatial.
 
        final Quad quad = new Quad(size, size); // Create an spatial. A plane in this case
        final Geometry geom = new Geometry("Particle", quad);
        geom.setMaterial(mat); // Assign the material to the spatial
        TangentBinormalGenerator.generate(geom);
        geom.setQueueBucket(Bucket.Transparent); // Remenber to set the queue bucket to transparent for the spatial
        
        geom.setLocalRotation(SharedData.QuaternionFromRotation(Vector3f.UNIT_Y));
     
        
        this.attachChild(geom);*/
		
		
			Spatial spatial = mainapp.getAssetManager().loadModel("Models/FakeParticleBlow/FakeParticleBlow.j3o");
						
	       Material mat = mainapp.getAssetManager().loadMaterial("Materials/FakeParticleBlow/fireball.j3m");
	       
	       if(type.equalsIgnoreCase("fireball")){
	    	   mat = mainapp.getAssetManager().loadMaterial("Materials/FakeParticleBlow/fireball.j3m");
	    	   spatial.scale(3);
		       spatial.setLocalRotation(SharedData.QuaternionFromRotation(Vector3f.UNIT_Y));
	    	   
	       }
	       
	       if(type.equalsIgnoreCase("wind")){
	    	   mat = mainapp.getAssetManager().loadMaterial("Materials/FakeParticleBlow/wind.j3m");
	    	   spatial.scale(2);
		       spatial.setLocalRotation(SharedData.QuaternionFromRotation(Vector3f.UNIT_Y));
	    	   
	       }
	       
	       
	       mat.getAdditionalRenderState().setDepthTest(true);
	       mat.getAdditionalRenderState().setDepthWrite(false);
	       spatial.setMaterial(mat);
	       spatial.setQueueBucket(Bucket.Transparent); 
	      attachChild(spatial);
	}

}
