 
package com.lib.commui.filebrowser;
 
import android.content.Context;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 自定义列表view，文件浏览用
 *
 */
public class IconTextView extends LinearLayout {
	private static final String t = "IconTextView";
	private TextView textViewName;
	private ImageView imageViewIcon;
	private CheckBox checkBoxSelect;
	 
	/*public IconTextView(Context context, IconText iconText, int position) {
		super(context);
		this.setOrientation(HORIZONTAL);
		this.setGravity(Gravity.CENTER_VERTICAL);
		this.setPadding(2, 2, 2, 2);
		this.setMinimumHeight(DensityUtil.dip2px(context, 48));
		this.iconText = iconText;
		
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); 

		mIcon = new ImageView(context);
		mIcon.setTag(position);
		mIcon.setPadding(4, 4, 4, 4);
		mIcon.setScaleType(ScaleType.CENTER_CROP);
		
		if (!iconText.getPath().equals("")) {
			mCheckBox = new CheckBox(context);
			mCheckBox.setFocusable(false);
			mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					//Log.d(t, " onCheckedChanged isChecked=" + isChecked + ", " + IconTextView.this.iconText.getText());
					
					IconTextView.this.iconText.setSelect(isChecked);
				}
			});
			addView(mCheckBox, param);
			
			//Log.d(t, " this.iconText.isSelect()=" + this.iconText.isSelect());
			mCheckBox.setChecked(this.iconText.isSelect());
			
			//Log.d(t, "is picture." + iconText.getPath());
			
			//显示缩略图，在部分低端机会出现响应过慢
			Bitmap cachedImage = new AsyncImageLoaderLocal(context, options).loadDrawable(iconText.getPath(), null, mIcon, position); 
			mIcon.setImageBitmap(cachedImage);
			addView(mIcon,  new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 100), DensityUtil.dip2px(context, 70)));
			
			
			mIcon.setImageDrawable(iconText.getIcon());
			addView(mIcon,  new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 46), DensityUtil.dip2px(context, 46)));

		}else{
			mIcon.setImageDrawable(iconText.getIcon());
			addView(mIcon,  new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 46), DensityUtil.dip2px(context, 46)));
		} 
		
		mText = new TextView(context);
		mText.setText(iconText.getText());
		addView(mText, param);
	}*/
	
	public IconTextView(Context context) {
		super(context);
		this.setOrientation(HORIZONTAL);
		this.setGravity(Gravity.CENTER_VERTICAL);
		this.setPadding(6, 2, 2, 2);
		this.setMinimumHeight(DensityUtil.dip2px(context, 42));
		
		LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		imageViewIcon = new ImageView(context);
		imageViewIcon.setPadding(4, 4, 4, 4);
		//imageViewIcon.setScaleType(ScaleType.CENTER_CROP);

		checkBoxSelect = new CheckBox(context);
		checkBoxSelect.setFocusable(false);

		textViewName = new TextView(context);
		//textViewName.setTextColor(context.getResources().getColor(R.color.list_text_color_title));

		addView(checkBoxSelect, new LayoutParams(DensityUtil.dip2px(context, 40), LayoutParams.FILL_PARENT));
		addView(imageViewIcon, new LayoutParams(DensityUtil.dip2px(context, 38), DensityUtil.dip2px(context, 38)));
		addView(textViewName, param);
		
	}
	
	public TextView getTextViewName(){
		return textViewName;
	}
	
	public CheckBox getCheckBoxSelect(){
		return checkBoxSelect;
	}
	
	public ImageView getImageViewIcon(){
		return imageViewIcon;
	}

	/*public void setText(String words) {
		textViewName.setText(words);
	}
	
	public void setIcon(Drawable bullet) { 
		mIcon.setImageDrawable(bullet);
	}
	
	public void setTextColor(int color){
		textViewName.setTextColor(color);
	}
	
	public void setContext(Context context){
		this.context = context;
	}*/
}