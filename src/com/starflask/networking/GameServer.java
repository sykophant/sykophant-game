package com.starflask.networking;

import com.starflask.util.DebugCategory;
import com.starflask.util.DebugLogger; 

//read https://developer.valvesoftware.com/wiki/Source_Multiplayer_Networking
public class GameServer implements Runnable  {
	
	Thread runner;
	GameServerProcess serverProcess;
	
	public GameServer()
	{
		serverProcess= new GameServerProcess();
		
		runner = new Thread(serverProcess, "game server thread"); 
		runner.start();
		
		
	}
	

	public static void main(String args[])
	{
		new GameServer(); //for testing
	}


	long last_millis;

	public void run() { 
		last_millis = System.currentTimeMillis();

		while (true) {

			long new_millis = System.currentTimeMillis();
			float tpf = (float) (new_millis - last_millis) / 1000f;
			last_millis = new_millis;

			if (tpf > 0.2f) {

				DebugLogger.log("Server proc loop lagged: " + tpf, DebugCategory.PERFORMANCE);
			}

			try {

				// this does not cause the stuttering
				update(tpf);


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void update(float tpf) {
		 System.out.println("update loop for server proc");
		
	}
	

}
