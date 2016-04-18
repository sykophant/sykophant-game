package com.starflask.networking;

import java.io.File;
import java.util.Calendar;

import mygame.Debugger;
import mygame.GameData;
import mygame.MainApp;
import mygame.SharedData;
import mygame.gameobjects.StructureAnimationEntity;
import mygame.gameobjects.selectables.Unit;
import mygame.networking.AbstractFileType;
import mygame.networking.datapackages.AbilityCastData;
import mygame.networking.datapackages.AbilityClickedData;
import mygame.networking.datapackages.AbstractFileStartData;
import mygame.networking.datapackages.BlockPlacementData;
import mygame.networking.datapackages.BuildingPlacementData;
import mygame.networking.datapackages.InitDataFromClient;
import mygame.networking.datapackages.PlayerData;
import mygame.networking.datapackages.RangedShotData;
import mygame.networking.datapackages.ServerListInfo;
import mygame.networking.datapackages.TerrainSliceData;
import mygame.networking.datapackages.UnitData;
import mygame.networking.messagetypes.AbilityCastMessage;
import mygame.networking.messagetypes.AbstractFileStartMessage;
import mygame.networking.messagetypes.BuildingPlacementMessage;
import mygame.networking.messagetypes.ChatMessage;
import mygame.networking.messagetypes.ClientInitDataMessage;
import mygame.networking.messagetypes.CreateBlockMessage;
import mygame.networking.messagetypes.PingMessage;
import mygame.networking.messagetypes.PlayerDataMessage;
import mygame.networking.messagetypes.RangedShotMessage;
import mygame.networking.messagetypes.GenericMessage;
import mygame.networking.messagetypes.ScriptMessage;
import mygame.networking.messagetypes.ServerInitDataMessage;
import mygame.networking.messagetypes.ServerListInfoMessage;
import mygame.networking.messagetypes.ServerListEntryRequestMessage;
import mygame.networking.messagetypes.TerrainSliceMessage;
import mygame.networking.messagetypes.UnitDataMessage;
import mygame.scriptobjects.Player;

import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

public class ServerListener implements MessageListener<HostedConnection> {
	

	public ServerListener(){
		
	}
	
	
	  public void messageReceived(HostedConnection source, Message message) {
		  
		  if (message instanceof ServerListEntryRequestMessage) {
			  
			  if(MainApp.clientsAllowedToConnect()){
			  ServerListEntryRequestMessage slerm = (ServerListEntryRequestMessage) message;			  
			  
			  reply(source,new ServerListInfoMessage(new ServerListInfo(slerm.getData(),"My Cool Server Name")));
			  }
		  }
		  
		  
		  
	    if (message instanceof ClientInitDataMessage) {
	      // do something with the message
	    	
	    	
	    	InitDataFromClient initdata =  ((ClientInitDataMessage) message).getMessage();	    
	    	
	    	Player newPlayer= MainApp.getGameState().playerJoinsServer(initdata.name, initdata.accountUsername);
	    	
	    	
	    	
		    	MainApp.getGameState().playerLoadedIntoServer(newPlayer);
		    	
		    	
		    if(!MainApp.getAuthClient().playerCanJoinServer(newPlayer, source)){
		    	//this immediately checks white/black lists, and sends a request to AuthServer, which must come back true in 2 seconds!
		    	
		    	//disconnect(source,"Account username not authorized by server.");		    	
		    	return;
		    }
		    	
	        reply(source,new ServerInitDataMessage()); 
	      
	        
	        addPlayerConnection(source,newPlayer); //matches up the player with the internet connection, very useful in Gamestate
	        
	        /*try {	//  THIS WAS JUST FOR TESTING - WORKS
				sendFile(source, new File(SharedData.pathToCustomSettingsFile()));
			} catch (Exception e) {
			
				e.printStackTrace();
			}*/


	      System.out.println("Added player to the game from " + source.getAddress());
	      
	   //   initdata.numberOfLocalPlayers

	      
	    	 // MainApp.getGameState().gamedata.initGlobalPlayer("noname",mainapp.MR.NUMBER_OF_GLOBAL_PLAYERS);
	    	
	      
	      
	      /*
	      //send unit data 
	  	for (int u = 0; u <  MainApp.getGameState().getGamedata().units.length; u++) {
			if ( MainApp.getGameState().getGamedata().units[u] != null) {
				if ( MainApp.getGameState().getGamedata().units[u].isActive()) {
					
					UnitDataMessage datames = new UnitDataMessage(new UnitData( MainApp.getGameState().getGamedata().units[u], u));
					datames.setReliable(true);
					reply(source,datames);
					// System.out.println("Sending Pos Mes "+u);
				}
			}
		}
	      */
	      
	      
	      //bugged.. should send the last loaded cubestrukt, not the one that defaulted when the server started up!!!!! ****
	     // Cubestrukt cs = MainApp.getGameState().getGamedata().myCubestrukt;
	   
	       /* 
	      try{	     	    	  
	    	MainApp.getGameState().getCubestruktManager().resaveTempCubestrukt();  //why does this take forever and a half?
	      	     
			MainApp.getAbstractFileManager().sendFile(source, new File(SharedData.pathToTempCubestruktFile()));
	      }catch(Exception e){
	    	  e.printStackTrace();
	      }
	      
	      
	    if(cs!=null){
	    	  reply(source,new CubestruktMessage(cs.filename));
	    	  
	    	  for(int i=0;i<cs.number_of_packets;i++){	    		 
	    	      reply(source,new CubestruktMessage(cs.packets[i]));//this is not working! causing crashing	    	    
	    	    }
	      }
	      
	      reply(source,new GenericMessage("FinishedSendingCubestrukt")); 
	     */
	      
	      
	      //Should be transferring this as file, not sending strings!
	     /* ScriptFile sf = MainApp.getGameState().getCurrentScriptFile();
	      	      
	    	      
	      for(int i=0;i<sf.codelines.size();i++){
	      reply(source,new ScriptMessage(sf.codelines.get(i)));//this is not working! causing crashing
	      MainApp.getDebugger().log("sent "+sf.codelines.get(i),"scriptfile");
	      }*/
	      	      
	      reply(source,new AbstractFileStartMessage( new AbstractFileStartData( MainApp.getGameMapManager().getCurrentMapFile().getName(), AbstractFileType.MAPFILE )   ));
	      
	     
	      
	      MainApp.getAbstractFileManager().sendFile(source , MainApp.getGameMapManager().getCurrentMapFile().getFile()   );
	      
	      
	      reply(source,new GenericMessage("FinishedSendingCodelines")); 
	     
	      
	     
	      
	      
	      	/*
	      
	      for(Unit unit: MainApp.getGameState().getGamedata().units){
  	    	  if(unit!=null){
  	    		  SpawnUnitMessage spawnMessage = new SpawnUnitMessage(new UnitData(unit));
  	    		  spawnMessage.data.pos = unit.getSpawningPosition();
  	    		  reply(source,spawnMessage);
  	    		  Debugger.log("SENDING " + spawnMessage,"units"); // this
  	    	  }
  	      }
	      */
	      
	      //send current terrain for the joiner
	     
	      TerrainSliceData tsd = null;
	      TerrainSliceMessage tsm = null;
	     for(int x=0;x<MainApp.getGameState().getBlockTerrain().getMapSize();x++){
	    	 for(int y=0;y<MainApp.getGameState().getBlockTerrain().getMapHeight();y++){
	    		 
	    		 tsd = new TerrainSliceData(MainApp.getGameState().getBlockTerrain().cubemap[x][y],x,y);
	    		 tsm = new TerrainSliceMessage( tsd );
	    		 
	    	  reply(source,tsm);
	    	
	    	 }
	     }
	      
	      
	    /*  for(int i=0;i<MainApp.getGameState().getBlockTerrain().getMapSize();i++){
	      reply(source,new TerrainSliceMessage(new TerrainSliceData(MainApp.getGameState().getBlockTerrain().cubemap,i)));
	      }*/
	      
	      
	      
	      
	      
	    } // else....
	    
	    
	    if (message instanceof ChatMessage) {

			String text = ((ChatMessage) message).getMessage();

			MainApp.getGameState().getChatMessage(text);

		}

	    
	    if (message instanceof GenericMessage){
	    	
	    	
	    	
	    	if(((GenericMessage) message).getMessage().equalsIgnoreCase("FinishedScriptInit")){
	    		//do whatever
	  	     
	  	      
	  	      
	    	}
	    	
	    	
	    	
		      
	    }
	    
	    
	    if (message instanceof UnitDataMessage) {
	    	
	    	UnitData ud =((UnitDataMessage) message).getData();
	    	
	    	
	    	int unitId = ud.unitId;
	    	if(MainApp.getGameState().getGamedata().gameEntities[unitId]!=null){
	    			    		
	    		((Unit) MainApp.getGameState().getGamedata().gameEntities[unitId]).getUnitPositionDataFromClient(ud);
	    		
	    		getPlayerFromHostedConnection(source).updatePing(ud.millis);
	    	}
	    	
	    }
	    

	    if (message instanceof CreateBlockMessage) {	    	
	    	BlockPlacementData bpd =((CreateBlockMessage) message).getData();	
	    	
	    	MainApp.getGameState().getServerProcessor().CubePlacementRequest(bpd);
	    	//MainApp.getGameState().QueueBlockChange(bpd);
	    	
	    	//MainApp.getGameState().CreateBlock(new Vector3Int(data[0],data[1],data[2]),data[3]);
	    }
	    
	   /* if (message instanceof DestroyBlockMessage) {
	    	BlockDestructionData bdd =((DestroyBlockMessage) message).getData();
	    		    	
	    	
	    	MainApp.getGameState().serverprocessor.DestroyBlock(bdd.loc, bdd.globalPlayerId,false);
	    	forwardToAllOthers(source,message);//do my own broadcasting
	    	
	    	
	    	
	    }
	    
	    
	    if (message instanceof BuildingHealthContributionMessage) {
	    	BuildingHealthContribution bhc =((BuildingHealthContributionMessage) message).getData();
	    	
	    	MainApp.getGameState().getServerProcessor().processBuildingHealthContribution(bhc);
	    	
	    	
	    	
	    }
	    
		
	    */
	    
	    
	    
	    
	    
	    if (message instanceof BuildingPlacementMessage) { //get a request from a client to place a building
	    	//check that teams resources. If they have enough, broadcast a building placement message to all people
	    	//if not enough, reply to asker that the construction attempt failed.
	    	
	    	BuildingPlacementData bpd =((BuildingPlacementMessage) message).getData();	    
	    	 MainApp.getGameState().getServerProcessor().BuildingPlacementRequest(bpd);
	    	
	    //	if(!success){reply...}	    	
	    }
	    
	    if (message instanceof AbilityCastMessage) {
	    	AbilityCastData acd =((AbilityCastMessage) message).getData();	  
	    	MainApp.getGameState().getServerProcessor().handleAbilityCast(acd);
	    }
	    
	    
	    
	    
	    
	  /*  if (message instanceof ShootProjectileMessage) {
	    	ShootProjectileData spd =((ShootProjectileMessage) message).getData();	    
	    		    	
	    	forwardToAllOthers(source,message);//pass it on to others 
	    	
	    	MainApp.getGameState().gamedata.SpawnProjectile( spd.start_pos, spd.start_dir,spd.projTypeId, spd.weaponId,spd.unitId);
	    	//for just art purposes
	    	
	    	
	    	float latency = source.getAttribute("latency");
	    	MainApp.getGameState().NewProjectileCollisionChecker(spd.start_pos, spd.start_dir,spd.projTypeId,spd.weaponId, spd.unitId, latency);

	    }*/
	    
	    if (message instanceof RangedShotMessage) {
	    	RangedShotData spd =((RangedShotMessage) message).getData();	
	    	
	    	//send to server processor for number crunching!!	    	
	    	MainApp.getGameState().getServerProcessor().getRangedShot( new RangedShotData(spd.start_pos,spd.start_dir,spd.globalPlayerId,spd.unitId,spd.weaponTypeId,spd.tick_number,spd.ms_since_tick)   );
	    }
	    
	    
	    if (message instanceof PingMessage) {
	    	long time_sent =((PingMessage) message).getMessage();	 
	    	long time_got = Calendar.getInstance().getTimeInMillis();
	    	
	    	long round_trip_time = time_got - time_sent;
	    	long latency = round_trip_time/2;

	    	source.setAttribute("latency", latency);
	    }
	    
	    if (message instanceof PlayerDataMessage) {
	    	PlayerData pd =((PlayerDataMessage) message).getData();	    
	    	
	    	/*forwardToAllOthers(source,message);
	    	
	    	MainApp.getGameState().playerJoinedTeam(MainApp.getGameState().players[pjtd.globalPlayerId],
					getGamedata().teams[pjtd.teamId]);*/
	    	
	    				
				MainApp.getGameState().getPlayerData(pd);
				
				
			
	    }
	    
	  /*  if (message instanceof PlayerGetsItemMessage) {
	    	PlayerGetsItemData data =((PlayerGetsItemMessage) message).getData();	    
	    		    	
	    	MainApp.getGameState().getServerProcessor().playerGetsItem(data);
	    }*/
   
   
	    
	    
	  }




	private void disconnect(HostedConnection source,String reason) {
		
		source.close(reason);
	}


	private void reply(HostedConnection source, Message msg) {
		MainApp.getServer().broadcast(Filters.in(source),msg);
	}
	 
	private void forwardToAllOthers(HostedConnection source, Message msg) {//important for client side guessing..speed
		MainApp.getServer().broadcast(Filters.notEqualTo(source),msg);
	}
	  
	private void broadcast( Message msg) {//important for client side guessing..speed
		MainApp.getServer().broadcast(msg);
	}
	
	private GameData getGamedata(){
		return MainApp.getGameState().getGamedata();
	}
	

	private Player getPlayerFromHostedConnection(HostedConnection source) {
		for(PlayerConnection conn : playerConnections){
			if(conn!=null){
				if(source.equals(conn.getHostedConnection())){
					return conn.getPlayer();
				}					
			}
		}
		return null;
	}
	
	
	private PlayerConnection[] playerConnections = new PlayerConnection[100];
	
	public  PlayerConnection[] getPlayerConnections(){
		return playerConnections;
	}

	void addPlayerConnection(HostedConnection conn, Player player){
		
		int next_available_id = -1;
		for (int i = 0; i < playerConnections.length; i++) {
			if (playerConnections[i] == null) {
				next_available_id = i;
				break;

			} else {
				/*if (!playerConnections[i].) {
					next_available_id = i;
					break;
				}*/
			}

		}

		if (next_available_id > -1) {
			playerConnections[next_available_id] = new PlayerConnection(conn,player);
			
		}
	}
	
	public class PlayerConnection{
		HostedConnection conn = null;
		Player player = null;
		
		PlayerConnection(HostedConnection conn, Player player){			
			this.conn = conn;
			this.player = player;
			
		}

		public HostedConnection getHostedConnection() {
			// TODO Auto-generated method stub
			return conn;
		}
		
		public Player getPlayer(){
			return player;
		}
		
	}

	  
}