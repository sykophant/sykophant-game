package com.starflask.starvoxel;

import com.badlogic.ashley.core.Entity;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.starflask.assets.AssetLibrary;
import com.starflask.renderable.NodeComponent;
import com.starflask.renderable.PositioningComponent;
import com.starflask.util.Vector3Int;

public class VoxelTerrain extends Entity {

	// Sizes
		private Vector3Int size = new Vector3Int(256,256,256);
		private Vector3f cubeSize = new Vector3f(1f,1f,1f);
		
		// Array containing cube data
		private int[][][] cubes;
		
		// Chunks (used to render the cubes)
		private Chunk[][][] chunks;
		private Vector3Int chunkArraySize;
		private Vector3Int chunkSize = new Vector3Int(16,16,16);
		
		VoxelWorld world;
		
		ChunkMeshBuilder chunkMeshBuilder;
		
		public VoxelTerrain(VoxelWorld world)
		{
			this.world=world;
			
			this.add(new NodeComponent());
			
			cubes = new int[size.x][size.y][size.z];
			
			initChunks(); 
			
			
			
		}
		

		boolean firstBuildPassComplete = false;
		public void build() {
			 
			chunkMeshBuilder = new ChunkMeshBuilder();
			chunkMeshBuilder.start();
			
			firstBuildPassComplete = true;
			
		}
		
	 
		public void update(float tpf)
		{
			
			
			for(int x = 0; x < chunkArraySize.x; x++) {
				for(int y = 0; y < chunkArraySize.y; y++) {
					for(int z = 0; z < chunkArraySize.z; z++) {
						chunks[x][y][z].update(tpf);
					}
				}
			}
		}
		
		
		public void initChunks(  ) { 
			
			// Calculate required number of chunks
			chunkArraySize = new Vector3Int(((int)Math.ceil(((double)size.x) / ((double)chunkSize.x))),
										((int)Math.ceil(((double)size.y) / ((double)chunkSize.y))),
										((int)Math.ceil(((double)size.z) / ((double)chunkSize.z))));
			
			chunks = new Chunk[chunkArraySize.x][chunkArraySize.y][chunkArraySize.z];
			
			
			// Create the chunks
			for(int x = 0; x < chunkArraySize.x; x++) {
				for(int y = 0; y < chunkArraySize.y; y++) {
					for(int z = 0; z < chunkArraySize.z; z++) {
						// Make sure the chunk size does not go outside of the world size
						Vector3Int adaptedChunkSize = new Vector3Int(chunkSize.x, chunkSize.y, chunkSize.z);
						
						if(x * chunkSize.x + chunkSize.x > size.x)
							adaptedChunkSize.x = size.x - x * chunkSize.x;
						
						if(y * chunkSize.y + chunkSize.y > size.y)
							adaptedChunkSize.y = size.y - y * chunkSize.y;
						
						if(z * chunkSize.z + chunkSize.z > size.z)
							adaptedChunkSize.z = size.z - z * chunkSize.z;
						
						// Create the chunk
						chunks[x][y][z] = new Chunk(this, new Vector3Int(x * chunkSize.x, y * chunkSize.y, z * chunkSize.z),
													adaptedChunkSize, cubes, size, cubeSize);
						
						chunks[x][y][z].setDrawTextures(false);
						chunks[x][y][z].needToRebuild = true;
						
						getNode().attachChild(chunks[x][y][z].getSpatial()  );
					}
				}
			}
		}
		
		
		private Node getNode() {
			 
			return this.getComponent(NodeComponent.class);
		}


		public boolean solidAt(Vector3f coordinates) {
			// Calculate cube array coordinates
			Vector3Int arrayCoords = new Vector3Int((int)((coordinates.x) / cubeSize.x), (int)((coordinates.y) / cubeSize.y), (int)((coordinates.z) / cubeSize.z));
			
			// Is this within world bounds?
			if(arrayCoords.x >= 0 && arrayCoords.x < size.x &&
				arrayCoords.y >= 0 && arrayCoords.y < size.y &&
				arrayCoords.z >= 0 && arrayCoords.z < size.z) {
				
				if(cubes[arrayCoords.x][arrayCoords.y][arrayCoords.z] != 0)
					return true;
			}
			
			return false;
		}
		
		
		public void setCubeType(int x, int y, int z, int type) {
			// Is this within world bounds?
			if(x >= 0 && x < size.x &&
				y >= 0 && y < size.y &&
				z >= 0 && z < size.z) {
				
				 
				int previous_type = cubes[x][y][z];
				// Set the cube type
				cubes[x][y][z] = type;
				
				
					
				// Calculate which chunk this belongs to
				Vector3Int chunkArrayCoords = new Vector3Int(x / chunkSize.x, y / chunkSize.y, z / chunkSize.z);
				Chunk edited_chunk = chunks[chunkArrayCoords.x][chunkArrayCoords.y][chunkArrayCoords.z];
				
				// Rebuild render data for the chunk
				if(previous_type==0 && type!=0)
				{
					edited_chunk.numSolidBlocks++;
				}else if(previous_type!=0 && type==0)
				{
					edited_chunk.numSolidBlocks--; //make sure we hide chunks who go back to 0 solid blocks
				}
				
				
				edited_chunk.needToRebuild = true;
				
				if( firstBuildPassComplete )
				{					
				 
				// Calculate in-chunk coordinates
				Vector3Int chunkPosition = edited_chunk.getPosition();
				Vector3Int inChunkPosition = new Vector3Int(x - chunkPosition.x, y - chunkPosition.y, z - chunkPosition.z);
				
				// Check if any nearby chunk must be rebuilt
				if(inChunkPosition.x == 0 && chunkArrayCoords.x > 0) {
					chunks[chunkArrayCoords.x - 1][chunkArrayCoords.y][chunkArrayCoords.z].needToRebuild = true;
				} else if(inChunkPosition.x == chunkSize.x - 1 && chunkArrayCoords.x < chunkArraySize.x - 1) {
					chunks[chunkArrayCoords.x + 1][chunkArrayCoords.y][chunkArrayCoords.z].needToRebuild = true;
				}
				
				if(inChunkPosition.y == 0 && chunkArrayCoords.y > 0) {
					chunks[chunkArrayCoords.x][chunkArrayCoords.y - 1][chunkArrayCoords.z].needToRebuild = true;
				} else if(inChunkPosition.y == chunkSize.y - 1 && chunkArrayCoords.y < chunkArraySize.y - 1) {
					chunks[chunkArrayCoords.x][chunkArrayCoords.y + 1][chunkArrayCoords.z].needToRebuild = true;
				}
				
				if(inChunkPosition.z == 0 && chunkArrayCoords.z > 0) {
					chunks[chunkArrayCoords.x][chunkArrayCoords.y][chunkArrayCoords.z - 1].needToRebuild = true;
				} else if(inChunkPosition.z == chunkSize.z - 1 && chunkArrayCoords.z < chunkArraySize.z - 1) {
					chunks[chunkArrayCoords.x][chunkArrayCoords.y][chunkArrayCoords.z + 1].needToRebuild = true;
				}
				
				}
			}
		}
		
	
		protected PositioningComponent getCameraPosition()
		{
			return world.getCameraPosition();
		}
		
		protected ChunkMeshBuilder getChunkMeshBuilder()
		{
			return chunkMeshBuilder;
		}


		public AssetLibrary getAssetLibrary()
		{
			return world.getAssetLibrary();
		}


	 

}
