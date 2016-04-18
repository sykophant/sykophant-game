package com.starflask.renderable.gui;
 

import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.MagFilter;
import com.jme3.texture.Texture.MinFilter;
import com.jme3.util.BufferUtils;

/**
 * 
 * 
 * Width and height are percentage based
 *
 */
public class GUIFilledRect extends GUINode {

	
	public GUIFilledRect(){
		
		this(50,50,ColorRGBA.Black);//defaults
	}
	
	public GUIFilledRect(float width, float height, ColorRGBA color){
		System.out.println("DIMS " + getDisplayDimensions().x);
		this.width=(width /100f) * this.getDisplayDimensions().x;
		this.height=(height /100f) * this.getDisplayDimensions().y;
		
		this.color=color;	
		
			 
	}
	
	float width = 0;
	float height = 0;
	public void setSize(float width, float height){
		this.width=(width /100f) * this.getDisplayDimensions().x;
		this.height=(height /100f) * this.getDisplayDimensions().y;
		
		
		fillmesh = new Quad(width,height);	
		fillmesh.updateBound();
		geo.setMesh(fillmesh);
		geo.updateModelBound();
		//refresh();
	}
	
	Quad fillmesh;
	Geometry geo;
	ColorRGBA color = new ColorRGBA(0,0,0,0);
	
	public void setColor(ColorRGBA color){
		this.color=color;
		
		 mat.setColor("Color", getColor());
		 geo.setMaterial(mat);
		//refresh();
	}
	
	
	private ColorRGBA getColor() { 
		return color;
	}

	Material mat;
	public void build(Material mat){
		this.mat=mat;
		
		fillmesh = new Quad(width,height);
		
		geo = new Geometry("Quad",fillmesh);
		
	    	 
	    	    mat.setColor("Color", color);
	    	  //  geo.setQueueBucket(Bucket.Transparent);  	    	    
	    	    mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
	    	    //mat.setTransparent(true);
	    	    	    	    
	    	   
	    	geo.setMaterial(mat);
	    	geo.setLocalTranslation(0,0,1f);//needs to be in front
	    	
	    this.attachChild(geo);
	    
	}

	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
 

	
}
