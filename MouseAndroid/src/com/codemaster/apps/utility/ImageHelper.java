package com.codemaster.apps.utility;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.TypedValue;

public class ImageHelper {
	
	public static Bitmap resizeBitmap(Context context, Bitmap input, float newWidthDP, float newHeightDP) {
		int width = input.getWidth();
		int height = input.getHeight();
		
		Resources r = context.getResources();
		float newWidthPx = TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, newWidthDP,
				r.getDisplayMetrics());
		float newHeightPx = TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, newHeightDP,
				r.getDisplayMetrics());

		float scaleWidth = (newWidthPx) / width;
		float scaleHeight = (newHeightPx) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(
				input, 0, 0, width, height,
				matrix, true);
		
		return resizedBitmap;
	}
}