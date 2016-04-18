package com.starflask.voxel;

import com.starflask.util.Vector3;

public class Air extends Block {

	public Air(Vector3 a, float dimension) {
		super(a, dimension, null);
	}
	
	@Override
	public boolean visible() {
		return false;
	}
}
