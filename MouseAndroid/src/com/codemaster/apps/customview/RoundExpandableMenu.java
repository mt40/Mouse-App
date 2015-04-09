package com.codemaster.apps.customview;

import java.util.ArrayList;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.codemaster.apps.mouse.R;
import com.codemaster.apps.utility.Geometry.Point2D;
import com.codemaster.apps.utility.ImageHelper;

public class RoundExpandableMenu extends CodemasterAppView {

	private enum MENU_STATE {
		IDLE, EXPANDED, EXPANDING, COMPRESSING
	}

	private MENU_STATE menuState;

	private Bitmap coreImage;

	private float expandRadius;
	private float coreSizeDp;
	private float coreSize;
	private float itemSizeDp;
	private float itemSize;
	private int animationSmoothness;
	private int duration;

	private OnClickListener clickListener;
	private ArrayList<RoundMenuItem> items;

	final Resources r = getResources();

	public RoundExpandableMenu(Context context, AttributeSet attrs,
			int coreResId) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.RoundExpandableMenu, 0, 0);
		coreSizeDp = a.getDimension(R.styleable.RoundExpandableMenu_coreSize,
				50);
		itemSizeDp = a.getDimension(R.styleable.RoundExpandableMenu_itemSize,
				50);

		coreImage = BitmapFactory.decodeResource(context.getResources(),
				coreResId);
		coreImage = ImageHelper.resizeBitmap(context, coreImage,
				coreSizeDp, coreSizeDp);

		coreSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				coreSizeDp, r.getDisplayMetrics());
		itemSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				itemSizeDp, r.getDisplayMetrics());
		
		width = coreSize;
		height = coreSize;
		
		menuState = MENU_STATE.IDLE;

		expandRadius = coreSize + itemSize;
		animationSmoothness = 20;
		duration = 300;
		origin = new Point2D();
		items = new ArrayList<RoundMenuItem>();
		a.recycle();
	}

	public RoundExpandableMenu(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.RoundExpandableMenu, 0, 0);
		coreSizeDp = a.getDimension(R.styleable.RoundExpandableMenu_coreSize,
				50);
		itemSizeDp = a.getDimension(R.styleable.RoundExpandableMenu_itemSize,
				50);

		coreImage = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_launcher);
		coreImage = ImageHelper.resizeBitmap(context, coreImage,
				coreSizeDp, coreSizeDp);
		
		coreSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				coreSizeDp, r.getDisplayMetrics());
		itemSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				itemSizeDp, r.getDisplayMetrics());
		
		width = coreSize;
		height = coreSize;
		
		menuState = MENU_STATE.IDLE;

		expandRadius = coreSize + itemSize;
		animationSmoothness = 20;
		duration = 300;
		origin = new Point2D();
		items = new ArrayList<RoundMenuItem>();
		a.recycle();
	}

	/* Core Image Get/Set */
	// ================================================================
	public void setCoreImage(Bitmap image) {
		coreImage = image;
		coreImage = ImageHelper.resizeBitmap(this.getContext(),
				coreImage, coreSizeDp, coreSizeDp);
		
		this.invalidate();
	}

	public void setCoreImage(Context context, int resId) {
		coreImage = BitmapFactory.decodeResource(context.getResources(),
				resId);
		coreImage = ImageHelper.resizeBitmap(this.getContext(),
				coreImage, coreSizeDp, coreSizeDp);
		
		this.invalidate();
	}
	// ================================================================
	
	/* animationSmoothness get/set */
	// ================================================================
	public void setAnimationSmoothness(int value) {
		this.animationSmoothness = value;
	}
	public int getAnimationSmoothness() {
		return this.animationSmoothness;
	}
	// ================================================================
	
	/* duration get/set */
	// ================================================================
	public void setDuration(int value) {
		this.duration = value;
	}
	public int getDuration() {
		return this.duration;
	}
	// ================================================================

	/* coreSize get/set */
	// ================================================================
	public void setCoreSizeDp(float value) {
		this.coreSizeDp = value;
		coreImage = ImageHelper.resizeBitmap(this.getContext(),
				coreImage, coreSizeDp, coreSizeDp);

		this.coreSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				coreSizeDp, r.getDisplayMetrics());
		
		width = coreSize;
		height = coreSize;
	}

	public float getCoreSizeDp() {
		return this.coreSizeDp;
	}
	public float getCoreSizePx() {
		return this.coreSize;
	}
	// ================================================================

	/* itemSize get/set */
	// ================================================================
	public void setItemSizeDp(float value) {
		this.itemSizeDp = value;
		this.itemSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				coreSizeDp, r.getDisplayMetrics());
		
	}

	public float getItemSizeDp() {
		return this.itemSizeDp;
	}
	public float getItemSizePx() {
		return this.itemSize;
	}
	// ================================================================
	
	/* Click listener set */
	// ================================================================
	public void setOnClickListener(View.OnClickListener listener) {
		this.clickListener = listener;
	}
	// ================================================================

	/* Add/Remove item to menu */
	// ================================================================
	public void addMenuItem(RoundMenuItem item) {
		items.add(item);
	}
	
	public void removeMenuItem(RoundMenuItem item) {
		if (items.contains(item)) items.remove(item);
	}
	
	public void removeMenuItem(int index) {
		if (index < items.size()) items.remove(index);
	}
	// ================================================================

	/* Align the items of the menu equally on a circle */
	// ================================================================
	private void alignMenuItem(int progress) {
		float angle = (float) (2 * Math.PI / items.size());
		float r = 0;

		if (menuState == MENU_STATE.EXPANDING) {
			r = expandRadius * progress / animationSmoothness;
		} else if (menuState == MENU_STATE.COMPRESSING) {
			r = expandRadius - expandRadius * progress / animationSmoothness;
		} else if (menuState == MENU_STATE.EXPANDED) {
			r = expandRadius;
		}

		Point2D k = new Point2D(0, r);
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setOrigin(
					new Point2D((float) (this.origin.getX() + k.getX()),
							(float) (this.origin.getY() + k.getY())));
			k.setX(r * (float) Math.sin(angle * (i + 1)));
			k.setY(r * (float) Math.cos(angle * (i + 1)));
		}

	}
	// ================================================================

	
	/* Draw the menu to the layout */
	// ================================================================
	@Override
	protected void onDraw(Canvas canvas) {
		Canvas g = canvas;

		if (coreImage != null) {
			g.drawBitmap(coreImage, top, left, null);
			
			if (menuState != MENU_STATE.IDLE) {
				for (int i = 0; i < items.size(); i++) {
					g.drawBitmap(items.get(i).getImageBitmap(),
							items.get(i).getTop(), items.get(i).getLeft(), null);
				}
			}
		}
		super.onDraw(canvas);
	}
	// ================================================================

	/* Define the dimension of the menu */
	// ================================================================
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int desiredWidth = (int) (expandRadius * 3);
		int desiredHeight = (int) (expandRadius * 3);

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		int viewWidth;
		int viewHeight;

		// Measure Width
		if (widthMode == MeasureSpec.EXACTLY) {
			// Must be this size
			viewWidth = widthSize;
		} else if (widthMode == MeasureSpec.AT_MOST) {
			// Can't be bigger than...
			viewWidth = Math.min(desiredWidth, widthSize);
		} else {
			// Be whatever you want
			viewWidth = desiredWidth;
		}

		// Measure Height
		if (heightMode == MeasureSpec.EXACTLY) {
			// Must be this size
			viewHeight = heightSize;
		} else if (heightMode == MeasureSpec.AT_MOST) {
			// Can't be bigger than...
			viewHeight = Math.min(desiredHeight, heightSize);
		} else {
			// Be whatever you want
			viewHeight = desiredHeight;
		}

		this.setOrigin(viewWidth/2, viewHeight/2);
		// MUST CALL THIS
		setMeasuredDimension(viewWidth, viewHeight);
	}
	// ================================================================

	/* Handle onTouch event */
	// ================================================================
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getActionMasked();
		float x = event.getX();
		float y = event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			
			// Click for submenu item
			for (Integer i = 0; i < items.size(); i++) {
				if (items.get(i).isSelected(x, y)) {
					items.get(i).Clicked();
					break;
				}
			}
			// Perform user custom click
			if (clickListener != null && this.isSelected(x, y)) {
				this.clickListener.onClick(this);
			}
			
			this.invalidate();
			break;

		default:
			break;
		}

		return super.onTouchEvent(event);
	}
	// ================================================================

	
	/* toggle the menu to expand/compress */
	// ================================================================
 	@SuppressLint("NewApi")
	public void toggle() {
 		if (menuState == MENU_STATE.IDLE) {
 			menuState = MENU_STATE.EXPANDING;
 		} else if (menuState == MENU_STATE.EXPANDED){
 			menuState = MENU_STATE.COMPRESSING;
 		}
 		
 		ValueAnimator anim = ValueAnimator.ofInt(0, animationSmoothness);
 		anim.setDuration(duration);
 		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int i = (Integer) animation.getAnimatedValue();
				if (menuState == MENU_STATE.EXPANDING || menuState == MENU_STATE.COMPRESSING)
	            {
	                if (i == animationSmoothness)
	                {
	                    if (menuState == MENU_STATE.COMPRESSING) menuState = MENU_STATE.IDLE;
	                    if (menuState == MENU_STATE.EXPANDING) menuState = MENU_STATE.EXPANDED;
	                }
	                alignMenuItem(i);
	            }
				invalidate();
			}
 		    
 		});
 		anim.start();
 	}
 	// ================================================================
 	
 	public boolean isSelected(float x, float y) {
		if (x >= top && x <= top + itemSize && y >= left && y <= left + itemSize) {
			return true;
		}
		return false;
	}
}