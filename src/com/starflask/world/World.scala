package com.starflask.world;

import com.jme3.math._;
import com.jme3.light._;
import com.jme3.scene.Node;


import com.badlogic.ashley.core.Entity;

import com.jme3.math.ColorRGBA;

import com.starflask.world.GameEngine._ 
import com.starflask.voxelmagica._
import com.starflask.assets._

import com.starflask.renderable._
import com.starflask.starvoxel._
import com.starflask.units.HardUnit
import com.starflask.events.GameActionPublisher.CustomGameAction

class World() extends Entity{
  
  
		 this.add(new NodeComponent());
		 
		 val node = this.getComponent(classOf[NodeComponent]);
		 
 

  var myPlayerId = 0 
  
  var gamedata =  ReactiveGameData( 0  )//gets replaced by a new copy each time
  
  
  val terrain = new VoxelTerrain();
  
  var gameActionExecutor = new GameActionExecutor(gamedata);
   
   def build( node: Node, assetLibrary: AssetLibrary )   =  new Node( )
   {
     
     
     	var importer = new VoxelMagicaImporter( terrain  );
		//println( System.getProperty("user.home") + "/workspace/UltraBlackBloodDeath/assets/monu9.vox" );
		  importer.readVoxelMagicaModel(System.getProperty("user.home") + "/git/UltraBlackBloodDeath/assets/monu9.vox");
		  
		  terrain.build( assetLibrary )
		  
		  
		  node.attachChild( terrain.getComponent( classOf[NodeComponent])  )
     
     
		 node.addLight( new AmbientLight(ColorRGBA.White) ); 
		 
		 	var sun = new DirectionalLight();
	    sun.setDirection(new Vector3f(1,1,-2).normalizeLocal());
	    sun.setColor(ColorRGBA.Red);
	    node.addLight(sun);
	    
	    var moon = new DirectionalLight();
	    moon.setDirection(new Vector3f(-1,0,2).normalizeLocal());
	    moon.setColor(ColorRGBA.Blue);
	    node.addLight(moon);
		 
		 		 
   }
  
  
 /* def getAction(action: GameAction) = action match
  {
    case 
  }
    //val in = getClass.getResourceAsStream("rootPage")
    for {
      name <- Option(action.getParameter("name"))
      action <- Option(action.getParameter("action")) if (action == "join" || action == "move" || action == "shoot")
      posStr <- Option(action.getParameter("position"))
      position = Integer.parseInt(posStr)
    } yield action match {
      case "join" => Join(name, "")
      case "move" => Move(name, position)
      case "shoot" => ShotFired(name, position)
    }
    */
   
   
   
   
    def update(tpf: Float )
    {
      var playerPosComponent = new PositioningComponent();
      
      terrain.update(tpf,playerPosComponent)
      
      //gamedata 
      
    }
    
    
    def processGameAction(action: CustomGameAction)
    {
      println("world processing game action " + action.toString() );
      
    }
    
}