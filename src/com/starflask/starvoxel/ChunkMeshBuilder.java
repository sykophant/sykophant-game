package com.starflask.starvoxel;
 

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ChunkMeshBuilder extends Thread{

	
	
	@Override
	public void run()
	{
		
		initialize();
		
		while(true)
		{
			update();
		} 
		
	}

	private void initialize() {
		
		
	}

	private void update() {
		
		fulfillBuildRequests() ;
		 
		
		
	}

	
	
	
	/*
	 * Should do some sorting so meshes are built closest to the player first..
	 * 
	 * Also I believe that this is so highly synchronized that it makes the main thread lag a lot while waiting..
	 */
	private void fulfillBuildRequests() {
		
		ChunkBuildRequest nextRequest = null;
		Iterator<ChunkBuildRequest> iter = null;
			
		synchronized( buildRequests ) {
		
			iter = buildRequests.iterator();
		
		
			if(!buildRequests.isEmpty() && iter.hasNext())
			{
				nextRequest = iter.next().clone();		
				iter.remove();
			
			}
		}
		
		if(nextRequest!=null  && chunkStillNearView( nextRequest.chunk)   )
		{
		 	nextRequest.buildMesh();
	
		 
		}

		
	}

	
	/*
	 * Dont draw chunks that are not near the current view
	 * 
	 * 
	 */
	
	 private boolean chunkStillNearView(Chunk chunk) {
		 
		return chunk.getChunkLOD() != ChunkLOD.HIDDEN ;
	}

	List<ChunkBuildRequest> buildRequests = Collections.synchronizedList(new ArrayList<ChunkBuildRequest>());
//	List<ChunkBuildRequest> buildRequests= new ArrayList<ChunkBuildRequest>();
	
	public void queueBuild( Chunk chunk) {
		
		if( buildRequests.size() > 10000 )
		{
			System.err.println("Backlog of chunk generation is too large! Over 10000 chunks");
			return;
		}
		
		buildRequests.add(new ChunkBuildRequest( chunk ));
		
		
		//System.out.println("added new build request to queue of size " + buildRequests.size());
	}
	
	
	class ChunkBuildRequest
	{
		
		int levelOfDetail;
		Chunk chunk;
		 
		
		public ChunkBuildRequest(  Chunk chunk ) {
			
			this.chunk=chunk;
			
			this.levelOfDetail = chunk.getChunkLOD();
			 
		}


		public void buildMesh() {
			
			try{
				 
				chunk.buildRenderData(); 
			 		
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
		}
		
	 


		@Override
		protected ChunkBuildRequest clone()
		{
			return new ChunkBuildRequest( chunk );
		}
		
		
		
	}
	
	
	 
	
	
}
