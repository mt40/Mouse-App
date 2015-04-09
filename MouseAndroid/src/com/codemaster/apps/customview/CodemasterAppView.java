package com.codemaster.apps.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.codemaster.apps.utility.Geometry.Point2D;

public class CodemasterAppView extends View {

	float top, left, width, height;
	Point2D origin;

	public CodemasterAppView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/* Origin get/set */
	// ================================================================
	public void setOrigin(Point2D point) {
		origin = point;
		top = origin.getX() - height / 2;
		left = origin.getY() - width / 2;
	}

	public void setOrigin(float x, float y) {
		origin.setX(x);
		origin.setY(y);
		top = origin.getX() - height / 2;
		left = origin.getY() - width / 2;
	}

	public Point2D getOrigin() {
		return origin;
	}

	// ================================================================

	/* Top get/set */
	// ================================================================
	public void setTopPosition(float value) {
		top = value;
		setOrigin(top, left);
	}

	public float getTopPosition() {
		return top;
	}

	// ================================================================

	/* Left get/set */
	// ================================================================
	public void setLeftPosition(float value) {
		left = value;
		setOrigin(top, left);
	}

	public float getLeftPosition() {
		return left;
	}

	// ================================================================

	/* Width get/set */
	// ================================================================
	public void setWidthDimension(float value) {
		width = value;
		setOrigin(top, left);
	}

	public float getWidthDimension() {
		return width;
	}

	// ================================================================

	/* Height get/set */
	// ================================================================
	public void setHeightDimension(float value) {
		height = value;
		setOrigin(top, left);
	}

	public float getHeightDimension() {
		return height;
	}

	// ================================================================

	public boolean isSelected(float x, float y) {
		if (x >= left && x <= left + width && y >= top && y <= top + width) {
			return true;
		}
		return false;
	}
}