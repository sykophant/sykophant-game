package com.starflask.starvoxel;

import game.Rectf;
import game.TextureStore;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.jme3.math.Vector4f;
import com.starflask.util.Vector3;
import com.starflask.util.Vector3Int;
 

public class Chunk {

	// Chunk properties
	private Vector3Int position;
	private Vector3Int size;
	
	// NOTE: this the same array as in the World class, so not all of these cubes belong to this chunk
	private char[][][] cubes;
	private Vector3Int worldSize;
	private Vector3 cubeSize;
	
	// GL list for slightly faster rendering
	private int glListIndex = 0;
	
	// Buffers
	private FloatBuffer vertexBuffer;
	private FloatBuffer normalBuffer;
	private FloatBuffer colorBuffer;
	private FloatBuffer texCoordBuffer;
	
	// Draw textures?
	private boolean drawTextures;
	
	public Chunk(Vector3Int position, Vector3Int size, char[][][] cubes, Vector3Int worldSize, Vector3 cubeSize) {
		this.position = position;
		this.size = size;
		this.cubes = cubes;
		this.worldSize = worldSize;
		this.cubeSize = cubeSize;
	}
	
	public void setDrawTextures(boolean drawTextures) {
		this.drawTextures = drawTextures;
	}
	
	public void buildRenderData() {
		// Generate vertex data
		List<float[]> vertexArrays = new ArrayList<float[]>();
		List<float[]> normalArrays = new ArrayList<float[]>();
		List<float[]> colorArrays = new ArrayList<float[]>();
		List<float[]> texCoordArrays = new ArrayList<float[]>();
		
		// Generate data for each cube
		for(int x = 0; x < size.x; x++) {
			for(int y = 0; y < size.y; y++) {
				for(int z = 0; z < size.z; z++) {
					char type = cubes[position.x + x][position.y + y][position.z + z];
					
					// Skip this cube if the type is EMPTY
					if(type == CubeType.EMPTY)
						continue;
					
					// Get the color and texture coords for this cube type
					Vector4f color = getColor(type);
					Rectf textureCoordinates = getTextureCoordinates(type);
					
					Vector3f pos1 = new Vector3f(x * cubeSize.x, y * cubeSize.y, z * cubeSize.z);
					Vector3f pos2 = Vector3f.add(pos1, cubeSize);
					
					// Top
					if((position.y + y == worldSize.y - 1) || (cubes[position.x + x][position.y + y + 1][position.z + z] == 0)) {
						// Vertex data
						vertexArrays.add(new float[] { pos2.x, pos2.y, pos1.z,
													pos1.x, pos2.y, pos1.z,
													pos1.x, pos2.y, pos2.z,
													pos2.x, pos2.y, pos2.z });
						
						// Normals
						normalArrays.add(new float[] { 0.0f, 1.0f, 0.0f,
													0.0f, 1.0f, 0.0f,
													0.0f, 1.0f, 0.0f,
													0.0f, 1.0f, 0.0f});
						
						// Colors
						colorArrays.add(new float[] { color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a});
						
						// Texture coordinates
						texCoordArrays.add(new float[] { textureCoordinates.right, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.bottom,
														textureCoordinates.right, textureCoordinates.bottom});
					}
					
					// Bottom
					if((position.y + y == 0) || (cubes[position.x + x][position.y + y - 1][position.z + z] == 0)) {
						// Vertex data
						vertexArrays.add(new float[] { pos2.x, pos1.y, pos2.z,
													pos1.x, pos1.y, pos2.z,
													pos1.x, pos1.y, pos1.z,
													 pos2.x, pos1.y, pos1.z });
						
						// Normals
						normalArrays.add(new float[] { 0.0f, -1.0f, 0.0f,
													0.0f, -1.0f, 0.0f,
													0.0f, -1.0f, 0.0f,
													0.0f, -1.0f, 0.0f});
						
						// Colors
						colorArrays.add(new float[] { color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a});
						
						// Texture coordinates
						texCoordArrays.add(new float[] { textureCoordinates.right, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.bottom,
														textureCoordinates.right, textureCoordinates.bottom});
					}
					
					// Front
					if((position.z + z == worldSize.z - 1) || (cubes[position.x + x][position.y + y][position.z + z + 1] == 0)) {
						// Vertex data
						vertexArrays.add(new float[] { pos2.x, pos2.y, pos2.z,
													pos1.x, pos2.y, pos2.z,
													pos1.x, pos1.y, pos2.z,
													pos2.x, pos1.y, pos2.z });
						
						// Normals
						normalArrays.add(new float[] { 0.0f, 0.0f, 1.0f,
													0.0f, 0.0f, 1.0f,
													0.0f, 0.0f, 1.0f,
													0.0f, 0.0f, 1.0f});
						
						// Colors
						colorArrays.add(new float[] { color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a});
						
						// Texture coordinates
						texCoordArrays.add(new float[] { textureCoordinates.right, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.bottom,
														textureCoordinates.right, textureCoordinates.bottom});
					}
					
					// Back
					if((position.z + z == 0) || (cubes[position.x + x][position.y + y][position.z + z - 1] == 0)) {
						// Vertex data
						vertexArrays.add(new float[] { pos1.x, pos2.y, pos1.z,
													pos2.x, pos2.y, pos1.z,
													pos2.x, pos1.y, pos1.z,
													pos1.x, pos1.y, pos1.z });
						
						// Normals
						normalArrays.add(new float[] { 0.0f, 0.0f, -1.0f,
													0.0f, 0.0f, -1.0f,
													0.0f, 0.0f, -1.0f,
													0.0f, 0.0f, -1.0f});
						
						// Colors
						colorArrays.add(new float[] { color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a});
						
						// Texture coordinates
						texCoordArrays.add(new float[] { textureCoordinates.right, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.bottom,
														textureCoordinates.right, textureCoordinates.bottom});
					}
					
					// Right
					if((position.x + x == worldSize.x - 1) || (cubes[position.x + x + 1][position.y + y][position.z + z] == 0)) {
						// Vertex data
						vertexArrays.add(new float[] { pos2.x, pos2.y, pos1.z,
													pos2.x, pos2.y, pos2.z,
													pos2.x, pos1.y, pos2.z,
													pos2.x, pos1.y, pos1.z });
						
						// Normals
						normalArrays.add(new float[] { 1.0f, 0.0f, 0.0f,
													1.0f, 0.0f, 0.0f,
													1.0f, 0.0f, 0.0f,
													1.0f, 0.0f, 0.0f});
						
						// Colors
						colorArrays.add(new float[] { color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a});
						
						// Texture coordinates
						texCoordArrays.add(new float[] { textureCoordinates.right, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.bottom,
														textureCoordinates.right, textureCoordinates.bottom});
					}
					
					// Left
					if((position.x + x == 0) || (cubes[position.x + x - 1][position.y + y][position.z + z] == 0)) {
						// Vertex data
						vertexArrays.add(new float[] {pos1.x, pos2.y, pos2.z,
													pos1.x, pos2.y, pos1.z,
													pos1.x, pos1.y, pos1.z,
													pos1.x, pos1.y, pos2.z });
						
						// Normals
						normalArrays.add(new float[] { -1.0f, 0.0f, 0.0f,
													-1.0f, 0.0f, 0.0f,
													-1.0f, 0.0f, 0.0f,
													-1.0f, 0.0f, 0.0f});
						
						// Colors
						colorArrays.add(new float[] { color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a,
													color.x, color.y, color.z, color.a});
						
						// Texture coordinates
						texCoordArrays.add(new float[] { textureCoordinates.right, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.bottom,
														textureCoordinates.right, textureCoordinates.bottom});
					}
				}
			}
		}
		
		// Create the float vertex buffer
		int numFloats = 0;
		
		for(float[] array : vertexArrays) {
			numFloats += array.length;
		}
		
		vertexBuffer = BufferUtils.createFloatBuffer(numFloats);
		
		for(float[] array : vertexArrays) {
			vertexBuffer.put(array);
		}
		
		vertexBuffer.flip();
		
		// Create the normal buffer
		numFloats = 0;
		
		for(float[] array : normalArrays) {
			numFloats += array.length;
		}
		
		normalBuffer = BufferUtils.createFloatBuffer(numFloats);
		
		for(float[] array : normalArrays) {
			normalBuffer.put(array);
		}
		
		normalBuffer.flip();
		
		// Create the color buffer
		numFloats = 0;
		
		for(float[] array : colorArrays) {
			numFloats += array.length;
		}
		
		colorBuffer = BufferUtils.createFloatBuffer(numFloats);
		
		for(float[] array : colorArrays) {
			colorBuffer.put(array);
		}
		
		colorBuffer.flip();
		
		// Create the tex coord buffer
		numFloats = 0;
		
		for(float[] array : texCoordArrays) {
			numFloats += array.length;
		}
		
		texCoordBuffer = BufferUtils.createFloatBuffer(numFloats);
		
		for(float[] array : texCoordArrays) {
			texCoordBuffer.put(array);
		}
		
		texCoordBuffer.flip();
		
		// Delete old list (it will be recreated in the render method)
		if(glListIndex != 0) {
			GL11.glDeleteLists(glListIndex, 1);
			glListIndex = 0;
		}
	}
	
	public void render() {
		if(glListIndex == 0) {
			// Create a gl list
			glListIndex = GL11.glGenLists(1);
			GL11.glNewList(glListIndex, GL11.GL_COMPILE);
				// Save the current matrix
				GL11.glPushMatrix();
				
				// Add the translation matrix
				GL11.glTranslatef(position.x * cubeSize.x, position.y * cubeSize.y, position.z * cubeSize.z);
				
				GL11.glVertexPointer(3, 0, vertexBuffer);
				GL11.glNormalPointer(0, normalBuffer);
				
				if(drawTextures)
					GL11.glTexCoordPointer(2, 0, texCoordBuffer);
				else
					GL11.glColorPointer(4, 0, colorBuffer);
				
				GL11.glDrawArrays(GL11.GL_QUADS, 0, vertexBuffer.limit() / 3);
				
				// Restore the matrix
				GL11.glPopMatrix();
			GL11.glEndList();
		}
		
		GL11.glCallList(glListIndex);
	}
	
	public Vector4f getColor(char type) {
		switch(type) {
		case CubeType.DIRT:
			return new Vector4f(0.35f, 0.15f, 0.0f, 1.0f);
		case CubeType.GRASS:
			return new Vector4f(0.0f, 0.3f, 0.00f, 1.0f);
		case CubeType.STONE:
			return new Vector4f(0.3f, 0.3f, 0.3f, 1.0f);
		case CubeType.WATER:
			return new Vector4f(0.0f, 0.2f, 0.7f, 0.6f);
		}
		
		return null;
	}
	
	public Rectf getTextureCoordinates(char type) {
		switch(type) {
		case CubeType.DIRT:
			return TextureStore.getTexRect(0, 1);
		case CubeType.GRASS:
			return TextureStore.getTexRect(0, 0);
		case CubeType.STONE:
			return TextureStore.getTexRect(0, 2);
		case CubeType.WATER:
			return TextureStore.getTexRect(0, 3);
		}

		return null;
	}
	
	public Vector3 getPosition() {
		return position;
	}
}