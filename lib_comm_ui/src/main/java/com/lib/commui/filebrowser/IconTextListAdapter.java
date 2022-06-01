 
package com.lib.commui.filebrowser;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.lib.commui.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 文件浏览list适配器
 *
 */
public class IconTextListAdapter extends BaseAdapter implements FileIconLoader.IconLoadFinishListener {
	private static final String t = "IconTextListAdapter";

	private FileIconLoader mIconLoader;

	private int[] colors = new int[] { R.color.comm_list_alternate_1, R.color.comm_list_alternate_2};

	private Context mContext;
	private List<IconText> mItems = new ArrayList<IconText>();
	
	private LinearLayout.LayoutParams p46x46;
	public static LinearLayout.LayoutParams p60x80;
	

	public IconTextListAdapter(Context context) {
		mContext = context;
		mIconLoader = new FileIconLoader(context, this);
		p46x46 = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 42), DensityUtil.dip2px(context, 42));
		p60x80 = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 42), DensityUtil.dip2px(context, 42));
	}

	public void addItem(IconText it) {
		mItems.add(it);
	}

	public void setListItems(List<IconText> lit) {
		mItems = lit;
	}

	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int position) {
		return mItems.get(position);
	}

	public boolean areAllItemsSelectable() {
		return false;
	}

	public long getItemId(int position) {
		return position;
	}

	/** @param convertView The old view to overwrite, if one is passed
	 * @returns a IconifiedTextView that holds wraps around an IconifiedText */
	public View getView(final int position, View convertView, ViewGroup parent) {
		IconTextView btv;

		if(convertView == null){
			btv = new IconTextView(mContext);
		}else{
			btv = (IconTextView)convertView;
		}
		
		final IconText it = mItems.get(position);
		
		
		// 背景颜色
		int colorPos = position % colors.length;
		btv.setBackgroundResource(colors[colorPos]);

		//名称
		btv.getTextViewName().setText(it.getText());
		
		btv.getCheckBoxSelect().setChecked(it.isSelect());
		
		//Log.d(t, "getPath=" + it.getPath() + ", getText=" + it.getText());
		
		if (!it.getPath().equals("") && !it.isDirectory()) {
			btv.getCheckBoxSelect().setVisibility(View.VISIBLE);
			
			//Log.d(t, "it.isSelect()=" + it.isSelect() + ",it.getPath()=" + it.getPath());
			
			/*btv.getCheckBoxSelect().setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					Log.d(t, " onCheckedChanged isChecked=" + isChecked + ",position=" + position);
					it.setSelect(isChecked);
				}
			});*/
			
			//此处不能使用setOnCheckedChangeListener 事件，否则会导致滑动列表后选项丢失。
			btv.getCheckBoxSelect().setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					CheckBox check = (CheckBox)v;
					Log.d(t, " onCheckedChanged isChecked=" + check.isChecked() + ",position=" + position);
					
					it.setSelect(check.isChecked());
					//check.setChecked(!check.isChecked());
				}
			});
			
			//设置图标
			mIconLoader.cancelRequest(btv.getImageViewIcon());
			boolean set = mIconLoader.loadIcon(btv.getImageViewIcon(), it.getPath(), 0);
			//
			
			if (!set){
				btv.getImageViewIcon().setLayoutParams(p46x46);
				btv.getImageViewIcon().setImageDrawable(it.getIcon());
			}else{
				btv.getImageViewIcon().setLayoutParams(p60x80);
				//btv.getImageViewIcon().setLayoutParams(p46x46);
			}
			
		}else{
			btv.getImageViewIcon().setLayoutParams(p46x46);
			btv.getImageViewIcon().setImageDrawable(it.getIcon()); 
			btv.getCheckBoxSelect().setVisibility(View.GONE);
		} 

		return btv;
	}

	@Override
	public void onIconLoadFinished(ImageView view) {
		// TODO Auto-generated method stub
		
	}
	
}

