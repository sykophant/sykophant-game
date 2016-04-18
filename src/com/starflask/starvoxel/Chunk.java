package com.starflask.starvoxel;

  

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
 
import org.lwjgl.opengl.GL11;

import com.badlogic.ashley.core.Entity;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import com.starflask.assets.AssetLibrary;
import com.starflask.renderable.NodeComponent;
import com.starflask.util.Vector3;
import com.starflask.util.Vector3Int;
import com.starflask.voxelmagica.VoxelMagicaImporter;
 
 

public class Chunk extends Entity{

	// Chunk properties
	private Vector3Int position;
	private Vector3Int size;
	
	// NOTE: this the same array as in the World class, so not all of these cubes belong to this chunk
	private int[][][] cubes;
	private Vector3Int worldSize;
	private Vector3f cubeSize;
	
	// GL list for slightly faster rendering
	private int glListIndex = 0;
	
	// Buffers
	private FloatBuffer vertexBuffer;
	private FloatBuffer normalBuffer;
	private FloatBuffer colorBuffer;
	//private FloatBuffer texCoordBuffer;
	
	// Draw textures?
	private boolean drawTextures;
	
	  boolean needToRebuild = true;
	private boolean threadedBuildFinished = false;
	
	VoxelTerrain terrain;
	
	public Chunk(VoxelTerrain terrain, Vector3Int position, Vector3Int size, int[][][] cubes, Vector3Int worldSize, Vector3f cubeSize) {
		this.position = position;
		this.size = size;
		this.cubes = cubes;
		this.worldSize = worldSize;
		this.cubeSize = cubeSize;
		
		this.terrain=terrain;
		
		this.add(new NodeComponent());
		}
	
	
	public void update(float tpf)
	{
		if(needToRebuild)
		{
			needToRebuild = false;
			
			terrain.getChunkMeshBuilder().queueBuild(this);
			
		}
		
	 
		if(threadedBuildFinished )
		{
			threadedBuildFinished = false;
			//attach the new mesh to my geometry
			attachNewGeometry();
			System.out.println("attached chunk geom " + this );
		}
		
		
	}
	
	


	public void setDrawTextures(boolean drawTextures) {
		this.drawTextures = drawTextures;
	}

	
	public void buildRenderData() {
		// Generate vertex data
		List<float[]> vertexArrays = new ArrayList<float[]>();
		List<float[]> normalArrays = new ArrayList<float[]>();
		List<float[]> colorArrays = new ArrayList<float[]>();
		//List<float[]> texCoordArrays = new ArrayList<float[]>();
		
		// Generate data for each cube
		for(int x = 0; x < size.x; x++) {
			for(int y = 0; y < size.y; y++) {
				for(int z = 0; z < size.z; z++) {
					int type = cubes[position.x + x][position.y + y][position.z + z];
					
					// Skip this cube if the type is EMPTY
					if(type == CubeType.EMPTY)
						continue;
					
					// Get the color and texture coords for this cube type
					Vector4f color = getColor(type);
					//Rectf textureCoordinates = getTextureCoordinates(type);
					
					Vector3f pos1 = new Vector3f(x * cubeSize.x, y * cubeSize.y, z * cubeSize.z);
					Vector3f pos2 = pos1.clone().add(  cubeSize);
					
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
						colorArrays.add(new float[] { color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w});
						
						// Texture coordinates
						/*texCoordArrays.add(new float[] { textureCoordinates.right, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.bottom,
														textureCoordinates.right, textureCoordinates.bottom});*/
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
						colorArrays.add(new float[] { color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w});
						
						// Texture coordinates
						/*texCoordArrays.add(new float[] { textureCoordinates.right, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.bottom,
														textureCoordinates.right, textureCoordinates.bottom});*/
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
						colorArrays.add(new float[] { color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w});
						
						// Texture coordinates
						/*texCoordArrays.add(new float[] { textureCoordinates.right, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.bottom,
														textureCoordinates.right, textureCoordinates.bottom});*/
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
						colorArrays.add(new float[] { color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w});
						
						// Texture coordinates
						/*texCoordArrays.add(new float[] { textureCoordinates.right, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.bottom,
														textureCoordinates.right, textureCoordinates.bottom});*/
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
						colorArrays.add(new float[] { color.x, color.y, color.z, color.w, //w is alpha
													color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w});
						
						// Texture coordinates
						/*texCoordArrays.add(new float[] { textureCoordinates.right, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.bottom,
														textureCoordinates.right, textureCoordinates.bottom});*/
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
						colorArrays.add(new float[] { color.x, color.y, color.z, color.w, //w is alpha
													color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w,
													color.x, color.y, color.z, color.w});
						
						// Texture coordinates
						/*texCoordArrays.add(new float[] { textureCoordinates.right, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.top,
														textureCoordinates.left, textureCoordinates.bottom,
														textureCoordinates.right, textureCoordinates.bottom});*/
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
		
	/*	for(float[] array : texCoordArrays) {
			numFloats += array.length;
		}
		
		texCoordBuffer = BufferUtils.createFloatBuffer(numFloats);
		
		for(float[] array : texCoordArrays) {
			texCoordBuffer.put(array);
		}
		
		texCoordBuffer.flip();*/
		
		mesh = generateNewMesh(vertexBuffer,normalBuffer,colorBuffer);
		//generateNewMesh(vertexArrays,normalArrays,colorArrays);  //this is all happening in the ChunkMeshBuilder thread so we use flags to handoff
		
		 if(mesh!=null)
		 {
			 threadedBuildFinished = true;
		 }
		
		
		// Delete old list (it will be recreated in the render method)
		if(glListIndex != 0) {
			GL11.glDeleteLists(glListIndex, 1);
			glListIndex = 0;
		}
	}
	
	
	private Mesh generateNewMesh(FloatBuffer vertexBuffer, FloatBuffer normalBuffer, FloatBuffer colorBuffer) {
		Mesh newMesh = new Mesh();
		
		 
		
		 newMesh.setBuffer(Type.Color, 4, VertexBuffer.Format.Float,	colorBuffer);
		  
		 newMesh.setBuffer(Type.Position, 3,VertexBuffer.Format.Float,  vertexBuffer );
		 
		 newMesh.setBuffer(Type.Normal, 3,VertexBuffer.Format.Float,  normalBuffer );
		 
		 return newMesh;
		
	}


	Mesh mesh;
	
	public Mesh generateNewMesh(List<float[]> vertices,List<float[]> normals ,List<float[]> colors  ) {

		Mesh newMesh = new Mesh();


		List<Byte> c5 = getByteArray( colors );

		Vector3f[] v3 = vertices.toArray(new Vector3f[vertices.size()]);
		
		
	//	int indx[] = VoxelRenderData.convertIntegers(getIndexes());

	
		final int bufferSize = c5.size();
		final ByteBuffer colorByteBuffer = BufferUtils
				.createByteBuffer(bufferSize);
		for (int i = 0; i < bufferSize; i++) {
			colorByteBuffer.put(c5.get(i));
		}
		colorByteBuffer.flip();
		if (colorByteBuffer != null) {
			newMesh.setBuffer(Type.Color, 
					4, 
					VertexBuffer.Format.Byte,
					colorByteBuffer);
		}

		newMesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(v3));
		
		/*if( this.drawTextures )
		{
			Vector3f[] n3 = getNormals().toArray(new Vector3f[getNormals().size()]);
			Vector2f[] t2 = getTexCoord().toArray(new Vector2f[getTexCoord().size()]);
			
			newMesh.setBuffer(Type.TexCoord, 2,
		 BufferUtils.createFloatBuffer(t2));
		 newMesh.setBuffer(Type.Normal, 2,
		 BufferUtils.createFloatBuffer(n3));
		}*/
		
	//	newMesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(indx));
		
		
		
		return newMesh;
	}
	
	 
	
	private void attachNewGeometry() {
		
		Geometry geo = new Geometry("Chunk", mesh); // using our custom mesh object
		Material mat = getAssetLibrary().findMaterial("terrain_material");
		geo.setMaterial(mat);
		getNode().attachChild(geo) ;
		 
	}
	

	public static  List<Byte> getByteArray( List<float[]> colors ) {
		List<Byte> ret = new ArrayList<Byte>();

				
		for (int i = 0; i < colors.size(); i++) {
			ret.add((byte) ((colors.get(i)[0] * 128)));
			ret.add((byte) ((colors.get(i)[1] * 128)));
			ret.add((byte) ((colors.get(i)[2] * 128)));
			ret.add((byte) ((colors.get(i)[3] * 128)));
		}
		
				
		return ret;
	}

	public Vector4f getColor(int type) {
		
		
		/*colors = VoxelMagicaImporter.VoxImporterListener
		switch(type) {
		case CubeType.DIRT:
			return new Vector4f(0.35f, 0.15f, 0.0f, 1.0f);
		case CubeType.GRASS:
			return new Vector4f(0.0f, 0.3f, 0.00f, 1.0f);
		case CubeType.STONE:
			return new Vector4f(0.3f, 0.3f, 0.3f, 1.0f);
		case CubeType.WATER:
			return new Vector4f(0.0f, 0.2f, 0.7f, 0.6f);
		}*/
		
		return new Vector4f(0.35f, 0.15f, 0.0f, 1.0f);
	}
	
/*	public Rectf getTextureCoordinates(char type) {
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
	}*/
	
	public Vector3Int getPosition() {
		return position;
	}


	public int getChunkLOD() { 
		
		float dist = getCoordinates().distance( terrain.getCameraPosition().getPos() );
		
		if(dist < 50)
		{
			return ChunkLOD.HIGH;  
		}
		if(dist < 100)
		{
			return ChunkLOD.LOW;  
		}
		
		return ChunkLOD.HIGH;   ///temp
	 
	}


	private Vector3f getCoordinates() {
		 
		return getPosition().toVector3f() ; //inefficient.. lots of garbage collection
	}


	public Spatial getSpatial() {
		 
		return this.getComponent(NodeComponent.class);
	}
	
	public Node getNode() {
		 
		return this.getComponent(NodeComponent.class);
	}
	
	public AssetLibrary getAssetLibrary()
	{
		return terrain.getAssetLibrary();
	}
	
	
}