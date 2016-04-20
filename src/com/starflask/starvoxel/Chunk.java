package com.starflask.starvoxel;

  

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
import com.starflask.MonkeyApplication;
import com.starflask.assets.AssetLibrary;
import com.starflask.renderable.NodeComponent;
import com.starflask.starvoxel.ChunkMeshBuilder.RenderDataBuilder;
import com.starflask.util.Vector3;
import com.starflask.util.Vector3Int;
import com.starflask.util.noise.PerlinNoiseGenerator;
import com.starflask.voxelmagica.VoxelMagicaImporter;
 
 

public class Chunk extends Entity{

	// Chunk properties
	private Vector3Int position;
	private Vector3Int size;
	
	// NOTE: this the same array as in the World class, so not all of these cubes belong to this chunk
	private byte[][][] cubes;
	private Vector3Int worldSize;
	private Vector3f cubeSize;
	
	// GL list for slightly faster rendering
	//private int glListIndex = 0;
	
	private boolean greedy = true;
	private boolean noiseBased = false;
	
	// Buffers
	/*private FloatBuffer vertexBuffer;
	private IntBuffer indexBuffer;
	private FloatBuffer normalBuffer;
	private FloatBuffer colorBuffer;
	 private FloatBuffer texCoordBuffer;
	*/
	
	// Draw textures?
	boolean drawTextures;
	
	  boolean needToRebuild = true;
	//boolean threadedBuildFinished = false;
	
	VoxelTerrain terrain;
	
	protected int numSolidBlocks = 0;
	 
	
	public Chunk(VoxelTerrain terrain, Vector3Int position, Vector3Int size, byte[][][] cubes, Vector3Int worldSize, Vector3f cubeSize) {
		this.position = position;
		this.size = size;
		this.cubes = cubes;
		this.worldSize = worldSize;
		this.cubeSize = cubeSize;
		
		this.terrain=terrain;
		
		this.add(new ChunkPhysicsComponent());
		this.add(new NodeComponent());
		}
	
	
	
	//The future that is used to check the execution status:
	 
	 
	Future<Chunk> futureBuiltChunk = null;
	
	public void update(float tpf)
	{
		
		
		
		if(needToRebuild && numSolidBlocks>0)
		{
			needToRebuild = false;
			
			//terrain.getChunkMeshBuilder().queueBuild(this);
			
			RenderDataBuilder buildTask = new RenderDataBuilder(this);
			 futureBuiltChunk = getExecutor().submit(     buildTask   );
			  
			  
		}
		
	 
		if(futureBuiltChunk!=null && futureBuiltChunk.isDone()  )
		{  
			
			 
			//attach the new mesh to my geometry
			 
			try {
				
				Chunk futureChunk = futureBuiltChunk.get();
				 
				attachNewGeometry( futureChunk.getMesh()   ); 
				futureBuiltChunk = null;
				
			} catch (InterruptedException e) {
			 
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}
		
		
	}
	
	


	private Mesh getMesh() {
	 
		return mesh;
	}




	public void setDrawTextures(boolean drawTextures) {
		this.drawTextures = drawTextures;
	}

	
	
		
	
	Rectf getTextureCoordinates(int type) {
		 
		return new Rectf(0,0,1,1);
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
	
	 
	
	private void attachNewGeometry(Mesh newMesh) {
		mesh = newMesh;
		
		
		Geometry geo = new Geometry("Chunk", mesh); // using our custom mesh object
		
		if(this.drawTextures)
		{
			Material mat = getAssetLibrary().findMaterial("terrain_material_textured");
			geo.setMaterial(mat);
		}
		else
		{
			Material mat = getAssetLibrary().findMaterial("terrain_material_untextured");
			geo.setMaterial(mat);
		}
		
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
	 
		
		return terrain.getColorPalette().getColorOfBlockType(type); 
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
		
		if(dist < 500)
		{
			return ChunkLOD.HIGH;  
		}
		if(dist < 1000)
		{
			return ChunkLOD.HIGH;  
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


	public Vector3Int getSize() {
		 
		return size;
	}

 


	public Vector3f getCubeSize() {
		 
		return cubeSize;
	}


	public Vector3Int getWorldSize() {
		 
		return worldSize;
	}


	public void setMesh(Mesh m) {
		mesh = m;
	}


	public int getBlockTypeFromLocalPosition(int x, int y, int z) {
		
		Vector3Int worldPos = position.clone().add(x,y,z);
		 
		
		if(withinWorldBounds(worldPos))
		{	
			int type = cubes[worldPos.x][worldPos.y][worldPos.z];
			if(noiseBased && type > 0)
			{
				return blockTypeFromNoise(x,y,z);
			}else{
				return type;
			}
		}
		return 0;
	}

	/*
	 * Return perlin value from this coordinate
	 * Decorative blocks are 50 to 70
	 */
	private int blockTypeFromNoise(int x, int y, int z) {
		
	//	public static double getNoise(double x, double y, double z, int octaves, double frequency, double amplitude) {
	  //      return instance.noise(x, y, z, octaves, frequency, amplitude);
	  //  }
		
		int noise_value = (int) perlinNoise.getNoise(x,y,z, 4,4,100);
		
		if(noise_value>80 )
		{
			int decoration_type_id = noise_value - 80;
		
			return 50 + decoration_type_id;
		}
				
		return 0;
		
	}


	private boolean withinWorldBounds(Vector3Int pos) {
		 
		return pos.x>=0 && pos.x<worldSize.x && pos.y>=0 && pos.y<worldSize.y && pos.z>=0 && pos.z<worldSize.z;
	}

	/*
	 * Use greedy rendering?  Cant use textures if so!  
	 */
	public boolean isGreedy() {
	 
		return greedy;
	}


	public void setGreedy(boolean b) {
		greedy = b;
		
	}


	public void setNoiseBased(boolean b) {
		noiseBased = b;
		 
	}

	PerlinNoiseGenerator perlinNoise;
	public void generateNoise() {
		 perlinNoise = new PerlinNoiseGenerator( getDeterministicSeed() ); 
		
	}


	private Random getDeterministicSeed() {
		 return new Random(this.numSolidBlocks);
	}
	
	private static ExecutorService getExecutor()
	{
		return MonkeyApplication.getConcurrencyExecutor();
	}
}