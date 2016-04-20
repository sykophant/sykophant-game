package com.starflask.world;

import com.jme3.math._;
import com.jme3.light._;
import com.jme3.scene.Node;

import com.jme3.math.ColorRGBA;

class World(){
  
  
   //val entities = new mutable.HashMap[Int, Entity]()
  
  //attach lighting ??
   
   def build( node: Node )   =  new Node( )
   {
     
		 node.addLight( new AmbientLight(ColorRGBA.White) ); 
		 
		 	var sun = new DirectionalLight();
	    sun.setDirection(new Vector3f(1,1,-2).normalizeLocal());
	    sun.setColor(ColorRGBA.Red);
	    node.addLight(sun);
	    
	    var moon = new DirectionalLight();
	    moon.setDirection(new Vector3f(-1,0,2).normalizeLocal());
	    moon.setColor(ColorRGBA.Blue);
	    node.addLight(moon);
		 
		 println("added light usin scala");
   }
   
}