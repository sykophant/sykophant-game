package com.starflask.starvoxel;

public class Rectf {

	public float left, top, right, bottom;
	
	public Rectf(float left, float top, float right, float bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}
	
	public float width() {
		return right - left;
	}
	
	public float height() {
		return bottom - top;
	}
	
	public boolean contains(float x, float y) {
		if(x >= left && x < right && y >= top && y < bottom) {
			return true;
		} else {
			return false;
		}
	}
	
}
