package com.codemaster.apps.utility;

public class Geometry {
	
	public static class Point2D {
		float x, y;
		
		public Point2D() {
			x = 0; y = 0;
		}
		public Point2D(float X, float Y) {
			x = X; y = Y;
		}
		
		public void setX(float value) {
			x = value;
		}
		public float getX() {
			return x;
		}
		
		public void setY(float value) {
			y = value;
		}
		public float getY() {
			return y;
		}
	}
}