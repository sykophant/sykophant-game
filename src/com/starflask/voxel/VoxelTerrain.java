package com.starflask.voxel;

import java.util.HashSet;

public class VoxelTerrain {
		
	
	ChunkContainer chunkContainer;
	
	
	int renderDistance = 100;
	
	
	public VoxelTerrain()
	{
		chunkContainer = new ChunkContainer();
		
	}
	
	public HashSet<Chunk> toRender;
	
	
	/**
	 * Renders the world once
	 */
	public void render() {		
		// check if the model is currently being written to and skip a frame if it is
		if(!model.locked) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	

			// adjust camera angle
			glLoadIdentity();
			GLU.gluLookAt(this.model.camera.eye.x, this.model.camera.eye.y, this.model.camera.eye.z,
					this.model.camera.focal.x, this.model.camera.focal.y, this.model.camera.focal.z,
					0.0f, 1.0f, 0.0f);

			
//			this.model.requestChunk(new Vector3(0, 0, 0));
			
			for(int i = -this.renderDistance; i <= this.renderDistance; i++) {
				for(int j = -this.renderDistance; j <= this.renderDistance; j++) {
					Chunk chunk = this.model.chunks.getChunk(this.model.camera.eye.add(new Vector3(i, 0, j).scale(16f)));
					chunk.drawBlocks();
				}
			}
			
			Display.update();
		}
	}

	
	
}
