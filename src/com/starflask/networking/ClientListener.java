package com.starflask.networking;

import java.util.Random;

import mygame.Debugger;
import mygame.GameData;
import mygame.MainApp;
import mygame.SharedData;
import mygame.Debugger.DebugCategory;
import mygame.frontmenu.FrontMenuGUI;
import mygame.gameobjects.selectables.Building;
import mygame.gameobjects.selectables.Unit;
import mygame.networking.datapackages.AbstractFileStartData;
import mygame.networking.datapackages.BlockDestructionData;
import mygame.networking.datapackages.BuildingData;
import mygame.networking.datapackages.BuildingDestructionData;
import mygame.networking.datapackages.BlockPlacementData;
import mygame.networking.datapackages.BuildingPlacementData;
import mygame.networking.datapackages.DestroyItemData;
import mygame.networking.datapackages.GFXEffectData;
import mygame.networking.datapackages.PlayerData;
import mygame.networking.datapackages.ServerGameData;
import mygame.networking.datapackages.ProjectileData;
import mygame.networking.datapackages.SpawnItemData;
import mygame.networking.datapackages.TeamResourcesPackage;
import mygame.networking.datapackages.TerrainSliceData;
import mygame.networking.datapackages.UnitAnimationData;
import mygame.networking.datapackages.SelectableDamageData;
import mygame.networking.datapackages.UnitData;
import mygame.networking.messagetypes.AbstractFileStartMessage;
import mygame.networking.messagetypes.BuildingDataMessage;
import mygame.networking.messagetypes.BuildingPlacementMessage;
import mygame.networking.messagetypes.ChatMessage;
import mygame.networking.messagetypes.CreateBlockMessage;
import mygame.networking.messagetypes.LoadMapFileMessage;
import mygame.networking.messagetypes.DestroyBlockMessage;
import mygame.networking.messagetypes.DestroyBuildingMessage;
import mygame.networking.messagetypes.DestroyItemMessage;
import mygame.networking.messagetypes.DestroyProjectileMessage;
import mygame.networking.messagetypes.GFXEffectMessage;
import mygame.networking.messagetypes.GenericMessage;
import mygame.networking.messagetypes.PingMessage;
import mygame.networking.messagetypes.PlayStateMessage;
import mygame.networking.messagetypes.PlayUnitAnimationMessage;
import mygame.networking.messagetypes.PlayerDataMessage;
import mygame.networking.messagetypes.ScriptMessage;
import mygame.networking.messagetypes.ServerGameDataMessage;
import mygame.networking.messagetypes.ServerInitDataMessage;
import mygame.networking.messagetypes.ServerListInfoMessage;
import mygame.networking.messagetypes.ShootProjectileMessage;
import mygame.networking.messagetypes.SpawnItemMessage;
import mygame.networking.messagetypes.TeamResourcesMessage;
import mygame.networking.messagetypes.TerrainSliceMessage;
import mygame.networking.messagetypes.UnitDataMessage;
import mygame.networking.messagetypes.DestroySelectableMessage;
import mygame.scriptobjects.Player;
import mygame.states.GameState;
import mygame.states.substates.GameplayState;

import com.jme3.app.Application;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

public class ClientListener implements MessageListener<Client> {

	Application mainapp;

	
	
	
	public ClientListener(Application mainapp) {
		this.mainapp = mainapp;
	}

	public void messageReceived(Client source, Message message) {

		if (message instanceof ServerListInfoMessage) {
			ServerListInfoMessage sled = (ServerListInfoMessage) message;

			FrontMenuGUI.getJoinMenu().receiveServerListEntryInfo(sled.getData());

			System.out.println("got servername" + sled.getData().servername); // WORKS!!!!
		}

		if (message instanceof ServerInitDataMessage) {

			ServerInitDataMessage rsm = (ServerInitDataMessage) message;
			
			
			MainApp.getGameState().MYSEED = rsm.getMessage().SEED;
			MainApp.getGameState().MYRANDOM = new Random(
					MainApp.getGameState().MYSEED);
			
			MainApp.getGameState().getExistingPlayers(rsm.getMessage().ExistingPlayers);
					
			MainApp.getGameState().localplayercontroller.setPlayer( rsm.getMessage().numberOfDefinedPlayers-1 );
			
			System.out.println("Got player number " +( rsm.getMessage().numberOfDefinedPlayers-1));

			MainApp.getGameState().ReceivedRandomSeedFromHost = true;
			
			MainApp.getGameState().getPlayStateManager().setStateTo( rsm.getMessage().playstate );

		}

		
		if (message instanceof PlayStateMessage) {
			
			PlayStateMessage playStateMessage = (PlayStateMessage) message;
			
			MainApp.getGameState().getPlayStateManager().setStateTo( playStateMessage.getData() );
			
		}
		
		
		/*The map file is send using the abstractfile downloader class now!
		 * 
		 * if (message instanceof ScriptMessage) {

			// do something with the message
			ScriptMessage mycodelinesMessage = (ScriptMessage) message;
			String s = mycodelinesMessage.getMessage();
			// SF = new ScriptFile(mycodelinesMessage.getMessage());

			if(s.length() > 0){
			
			MainApp.getMainRegister().codelines[MainApp.getMainRegister().codelines_received] = mycodelinesMessage
					.getMessage();
			System.out.println("Got codeline "
					+ MainApp.getMainRegister().codelines[MainApp.getMainRegister().codelines_received]);
			MainApp.getMainRegister().codelines_received++;
			
			}

		}*/
		
		
		
		if (message instanceof TerrainSliceMessage) {
			TerrainSliceData tsd = ((TerrainSliceMessage) message).getData();
			MainApp.getGameState().getTerrainSlice(tsd);
			
		}
		
		
		if (message instanceof PlayStateMessage) {
			GameplayState newstate = (GameplayState) ((PlayStateMessage) message).getData();			
						
			GameState.getPlayStateManager().setStateTo( newstate );			
		}


	/*	if (message instanceof GenericMessage) {
	

			if (((GenericMessage) message).getMessage().equalsIgnoreCase(
					"FinishedSendingCodelines")) {

				MainApp.getMainRegister().currentgamescriptfile = new ScriptFile(	MainApp.getMainRegister().codelines);
			}

		
			
		}*/
		
		
		
		if( message instanceof AbstractFileStartMessage){
			
			AbstractFileStartData data =  ((AbstractFileStartMessage) message).getData();
			
			MainApp.getAbstractFileManager().recieveFile(data.name, data.getType() );
			
		}
					
		
		

	/*	if (message instanceof MapEditMessage) {
			PlacementData bpd = ((MapEditMessage) message).getData();
			// MapEdit me = new MapEdit(MapEditMessage.getMessage().loc,1);

			// do something with the message
			// MainApp.getGameState().mapedits[MainApp.getGameState().number_of_mapedits]
			// = mapedit;
			// MainApp.getGameState().number_of_mapedits++;

		

			MainApp.getGameState().QueueBlockChange(bpd);

			} */
		
	
		 
		

		/*
		 * if (message instanceof PositionMessage) {
		 * 
		 * //MainApp.getGameState().UnitInterpolationTween = 0f;
		 * 
		 * UnitPositionData upd =((PositionMessage) message).getData(); int
		 * unitId = upd.unitId; Unit unit =
		 * MainApp.getGameState().gamedata.units[unitId]; if(unit!=null){
		 * if(unit.isActive){
		 * 
		 * boolean unit_is_local = false; try{ for(int
		 * i=0;i<mainapp.MR.NUMBER_OF_LOCAL_PLAYERS;i++){ if(unit.owner ==
		 * mainapp
		 * .myGameState.localplayers[i].globalPlayerId){unit_is_local=true;}
		 * 
		 * } }catch(Exception e){unit_is_local= true;System.err.println(e);}
		 * 
		 * if(!unit_is_local){//clients solely determine the pos of their own
		 * controlled units. They know best.
		 * //MainApp.getGameState().gamedata.units[unitId].future_pos = upd.pos;
		 * //MainApp.getGameState().gamedata.units[unitId].future_facing =
		 * upd.facing;
		 * MainApp.getGameState().gamedata.units[unitId].updateFuturePosition
		 * (upd.pos,upd.facing); }
		 * 
		 * }}
		 * 
		 * }
		 */

		/*if(message instanceof SpawnUnitMessage){
			UnitData ud = ((SpawnUnitMessage) message).getData();
			
			Debugger.log("GOT " + ud, "units"); // this
			
			getGamedata().DiscoverUnit(ud);
		}*/
		
		
		if (MainApp.getGameState().readyToUpdate()) {
			
			if (message instanceof ChatMessage) {

				String text = ((ChatMessage) message).getMessage();

				MainApp.getGameState().getChatMessage(text);

			}

			
			if (message instanceof UnitDataMessage) {
				UnitData ud = ((UnitDataMessage) message).getData();
				int unitId = ud.unitId;
				
				Unit unit = (Unit) getGamedata().gameEntities[unitId];
				
				
				if (unit == null)  {
					
					//UnitData ud = ((UnitDataMessage) message).getData();
					
					MainApp.getDebugger().log("discovering " + ud, DebugCategory.MULTIPLAYER); // this
					
					getGamedata().discoverUnit(ud);
					
				}else{
					unit.updateUnitData(ud);// update the units health, etc

					if (unit.isActive()) {

						boolean unit_is_local = false;
					
						
						if (getPlayer().equals(  unit.getOwner() )) {
								unit_is_local = true;
						}

						
						if (!unit_is_local) {// clients solely determine the pos
												// of their own controlled
												// units. They know best.
							// MainApp.getGameState().gamedata.units[unitId].future_pos
							// = upd.pos;
							// MainApp.getGameState().gamedata.units[unitId].future_facing
							// = upd.facing;
							unit.getOrientationComponent().updateSlaveFuturePosition(ud.pos, ud.facing);
						}

					}else{
						getGamedata().discoverUnit(ud);
					}

				}

			}
			
			if (message instanceof BuildingDataMessage) {
				BuildingData bd = ((BuildingDataMessage) message).getData();
				int buildingId = bd.buildingId;
				
				Building building = (Building) getGamedata().gameEntities[buildingId];
				
				
				if (building == null)  {
					
					//UnitData ud = ((UnitDataMessage) message).getData();
					
					MainApp.getDebugger().log("discovering  " + bd, DebugCategory.MULTIPLAYER); // this
					
					getGamedata().discoverBuilding(bd);
					
				}else{
					building.updateBuildingData(bd);// update the units health, etc

					if (building.isActive()) {

						boolean unit_is_local = false;					
						
						if (MainApp.getGameState().localplayercontroller.getPlayer().equals(  building.getOwner() )) {
								unit_is_local = true;
						}
						
						if (!unit_is_local) {
							//building.updateFuturePosition(bd.pos);
						}

					}else{
						
						getGamedata().discoverBuilding(bd);
					}

				}

			}

			if (message instanceof CreateBlockMessage) {
				BlockPlacementData bpd = ((CreateBlockMessage) message)
						.getData();
				mainapp.getStateManager().getState(GameState.class)
						.QueueBlockChange(bpd);
				// MainApp.getGameState().CreateBlock(new
				// Vector3Int(data[0],data[1],data[2]),data[3]);
			}

			if (message instanceof DestroyBlockMessage) {
				BlockDestructionData bdd = ((DestroyBlockMessage) message)
						.getData();
				MainApp.getGameState().QueueBlockChange(new BlockPlacementData(
						bdd.loc, null, 0, getPlayer()));
				// MainApp.getGameState().DestroyBlock(new
				// Vector3Int(data[0],data[1],data[2]));
			}

			if (message instanceof BuildingPlacementMessage) { // get a request
																// from a client
																// to place a
																// building
				// check that teams resources. If they have enough, broadcast a
				// building placement message to all people
				// if not enough, reply to asker that the construction attempt
				// failed.

				BuildingPlacementData bpd = ((BuildingPlacementMessage) message).getData();

				// start building the building at the specified location for the
				// specified team!

				MainApp.getGameState().placeNewBuilding(bpd);

			}

			if (message instanceof ServerGameDataMessage) {

				ServerGameData sgd = ((ServerGameDataMessage) message)
						.getData();
				MainApp.getMainRegister().game_timer = sgd.gameTimer;
				MainApp.getMainRegister().NUMBER_OF_NETWORK_TICKS = sgd.numberOfNetworkTicks;
				// mainapp.MR.seconds_until_round_start =
				// sgd.secondsUntilRoundStart;

				MainApp.getMainRegister().TIME_SINCE_LAST_TICK = 0f;
			}

			if (message instanceof TeamResourcesMessage) { // get a request from
															// a client to place
															// a building
				// check that teams resources. If they have enough, broadcast a
				// building placement message to all people
				// if not enough, reply to asker that the construction attempt
				// failed.

				TeamResourcesPackage trp = ((TeamResourcesMessage) message)
						.getData();

				// start building the building at the specified location for the
				// specified team!

				// MainApp.getGameState().PlaceNewBuilding(bpd.loc,
				// bpd.buildingtype_id,bpd.owner_id,bpd.team_id);

				int number_of_teams = getGamedata().number_of_defined_teams;
				int number_of_resources = getGamedata().number_of_defined_resources;

				for (int i = 0; i < number_of_teams; i++) {
					for (int r = 0; r < number_of_resources; r++) {
						// try{
						getGamedata().teams[i].resource_amount[r] = trp.teamresources[i][r];
						// }catch(Exception
						// e){System.err.println("error updating resources");System.err.println(e);}
					}
				}

			}

			if (message instanceof DestroyBuildingMessage) {

				BuildingDestructionData bdd = ((DestroyBuildingMessage) message)
						.getData();

				MainApp.getGameState().destroyBuilding(bdd.buildingId,
						bdd.globalPlayerId);

			}

			if (message instanceof DestroySelectableMessage) {
				SelectableDamageData udd = ((DestroySelectableMessage) message).getData();
				MainApp.getGameState().onSelectableDeath(udd);

			}

			if (message instanceof ShootProjectileMessage) {
				ProjectileData spd = ((ShootProjectileMessage) message)
						.getData();

				MainApp.getGameState().spawnProjectile(spd);
				// for just art purposes
			}

			if (message instanceof PingMessage) {
				MainApp.getClient().send(message); // sent it right back..
			}

			if (message instanceof PlayerDataMessage) {
				PlayerData pd = ((PlayerDataMessage) message).getData();
				
				MainApp.getGameState().getPlayerData(pd);
				
				
			}

			if (message instanceof SpawnItemMessage) {
				SpawnItemData data = ((SpawnItemMessage) message).getData();
				MainApp.getGameState().spawnItem(data);
			}
			if (message instanceof DestroyItemMessage) {
				DestroyItemData data = ((DestroyItemMessage) message).getData();
				MainApp.getGameState().destroyItem(data);
			}
			
			if (message instanceof PlayUnitAnimationMessage) {
				UnitAnimationData uad = ((PlayUnitAnimationMessage)message).getData();
				Unit unit = (Unit) MainApp.getGameState().getGamedata().gameEntities[uad.unitId] ;
					
				//unit.playAnimation(uad.animName);
							
			}
			
			if (message instanceof DestroyProjectileMessage) {
				int id = ((DestroyProjectileMessage) message).getData();
				MainApp.getGameState().destroyProjectile(id);
			}

			if (message instanceof GFXEffectMessage) {
				GFXEffectData ged = ((GFXEffectMessage) message).getData();

				MainApp.getGameState().createGFXEffect(ged);
			}
			
			
			
			
			

		}

	}
	
	
	private Player getPlayer() {
		return GameState.getPlayerController().getPlayer();
	}

	private GameData getGamedata(){
		return MainApp.getGameState().getGamedata();
	}
}