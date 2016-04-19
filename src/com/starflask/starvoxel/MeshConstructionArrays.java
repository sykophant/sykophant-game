package com.starflask.starvoxel;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import com.jme3.math.ColorRGBA;
import com.jme3.util.BufferUtils;

public class MeshConstructionArrays {

	List<float[]> vertexArrays = new ArrayList<float[]>();
	List<int[]> indexArrays = new ArrayList<int[]>();
	List<float[]> normalArrays = new ArrayList<float[]>();
	List<float[]> colorArrays = new ArrayList<float[]>();
	List<float[]> texCoordArrays = new ArrayList<float[]>();
	
	
	public List<float[]> getVertices() {
		 
		return vertexArrays;
	}


	public List<int[]> getIndexes() {
		 
		return indexArrays;
	}


	public List<float[]> getNormals() {
		 
		return normalArrays;
	}


	public List<float[]> getColors() {
		 
		return colorArrays;
	}


	public MeshConstructionBuffers toMeshBuffers(boolean drawTextures) {
		 
		MeshConstructionBuffers buffers = new MeshConstructionBuffers();
		
		// Create the float vertex buffer
				int numVertices = 0;
				
				for(float[] array : vertexArrays) {
					numVertices += array.length;
				}
				
				buffers.vertexBuffer = BufferUtils.createFloatBuffer(numVertices);
				
				System.out.println("vertices:"+numVertices);
				for(float[] array : vertexArrays) {
					for(float f : array)
					{
						System.out.println("vertex " + f);
					}
					buffers.vertexBuffer.put(array);
				}
				
				buffers.vertexBuffer.flip();
				
				//create index buffer
				int numIndices = 0;
				
				for(int[] array : indexArrays) {
					numIndices += array.length;
				}
				buffers.indexBuffer = BufferUtils.createIntBuffer(numIndices);
				
				System.out.println("indices:"+numIndices);
				for(int[] array : indexArrays) {	
					
					for(int i : array)
					{
						System.out.println("index " + i);
					}
					
					buffers.indexBuffer.put(array);
				}
				
				buffers.indexBuffer.flip();
				
				// Create the normal buffer
				int numNormals = 0;
				
				for(float[] array : normalArrays) {
					numNormals += array.length;
				}
				
				buffers.normalBuffer = BufferUtils.createFloatBuffer(numNormals);
				
				for(float[] array : normalArrays) {
					buffers.normalBuffer.put(array);
				}
				
				buffers.normalBuffer.flip();
				
				// Create the color buffer
				int numColors = 0;
				
				for(float[] array : colorArrays) {
					numColors += array.length;
				}
				
				buffers.colorBuffer = BufferUtils.createFloatBuffer(numColors);
				
				for(float[] array : colorArrays) {
					buffers.colorBuffer.put(array);
				}
				
				buffers.colorBuffer.flip();
				
				// Create the tex coord buffer
				int numTexCoords = 0;
				
			 	for(float[] array : texCoordArrays) {
			 		numTexCoords += array.length;
				}
				
			 	if(drawTextures)
			 	{
			 		buffers.texCoordBuffer = BufferUtils.createFloatBuffer(numTexCoords);
				
				for(float[] array : texCoordArrays) {
					buffers.texCoordBuffer.put(array);
				}
				
				buffers.texCoordBuffer.flip(); 
			 	}
			 	
			 	
		if(numVertices > 0 && numIndices> 0){
			return buffers;
		}
		
		return null;
		
	}

}
