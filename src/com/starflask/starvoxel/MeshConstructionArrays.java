package com.starflask.starvoxel;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import com.jme3.math.ColorRGBA;

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

}
