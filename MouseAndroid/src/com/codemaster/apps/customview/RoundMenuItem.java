package com.codemaster.apps.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.codemaster.apps.utility.Geometry.Point2D;
import com.codemaster.apps.utility.ImageHelper;

public class RoundMenuItem {

	public interface ClickListener {
		public void onClick();
	}
	
	private RoundExpandableMenu parent;
	private Context context;

	private Bitmap image;
	private float itemSize;
	private float itemSizeDp;
	
	private ClickListener clickListener;
	private float top, left;
	private Point2D origin;

	/* Constructors */
	// ================================================================
	public RoundMenuItem(Context context, Bitmap image) {
		this.context = context;
		this.image = image;
		itemSize = parent.getItemSizePx();
		itemSizeDp = parent.getItemSizeDp();
		image = ImageHelper
				.resizeBitmap(context, image, itemSizeDp, itemSizeDp);
	}

	public RoundMenuItem(Context context, int resId, RoundExpandableMenu parent) {
		this.context = context;
		this.parent = parent;
		this.image = BitmapFactory.decodeResource(context.getResources(),
				resId);
		itemSize = parent.getItemSizePx();
		itemSizeDp = parent.getItemSizeDp();
		image = ImageHelper
				.resizeBitmap(context, image, itemSizeDp, itemSizeDp);
	}
	// ================================================================
	
	/* Menu parent get/set */
	// ================================================================
	public void setParent(RoundExpandableMenu parent) {
		this.parent = parent;
		itemSize = parent.getItemSizePx();
		itemSizeDp = parent.getItemSizeDp();
		image = ImageHelper
				.resizeBitmap(context, image, itemSizeDp, itemSizeDp);
	}
	public RoundExpandableMenu getParent() {
		return this.parent;
	}
	// ================================================================

	/* Image get/set*/
	// ================================================================
	public void setImage(Bitmap image) {
		this.image = image;
		image = ImageHelper
				.resizeBitmap(context, image, itemSizeDp, itemSizeDp);
	}

	public void setImage(int resId) {
		this.image = BitmapFactory.decodeResource(context.getResources(),
				resId);
		image = ImageHelper
				.resizeBitmap(context, image, itemSizeDp, itemSizeDp);
	}

	public Bitmap getImageBitmap() {
		return this.image;
	}
	// ================================================================
	
	/* top get/set*/
	// ================================================================
	public void setTop(float value) {
		this.top = value;
	}

	public float getTop() {
		return this.top;
	}
	// ================================================================
	
	/* top get/set*/
	// ================================================================
	public void setLeft(float value) {
		this.top = value;
	}

	public float getLeft() {
		return this.left;
	}
	// ================================================================
	
	/* origin get/set */
	// ================================================================
	public void setOrigin(Point2D point) {
		this.origin = point;
		this.top = this.origin.getX() - itemSize / 2;
		this.left = this.origin.getY() - itemSize / 2;
	}
	public void setOrigin(float x, float y) {
		this.origin.setX(x);
		this.origin.setY(y);
		this.top = this.origin.getX() - itemSize / 2;
		this.left = this.origin.getY() - itemSize / 2;
	}

	public Point2D getOrigin() {
		return origin;
	}
	// ================================================================
	
	/* Click listener get/set */
	// ================================================================
	public void setOnClickListener(ClickListener listener) {
		this.clickListener = listener;
	}
	
	public void Clicked() {
		if (this.clickListener != null) {
			this.clickListener.onClick();
		}
	}
	// ================================================================
	
	/* Is it selected? */
	// ================================================================
	public boolean isSelected(float x, float y) {
		if (x >= top && x <= top + itemSize && y >= left && y <= left + itemSize) {
			return true;
		}
		return false;
	}
	// ================================================================
}