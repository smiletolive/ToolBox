/**
 * Copyright (C) 2009 Android OS Community Inc (http://androidos.cc/bbs).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lib.commui.filebrowser;

import android.graphics.drawable.Drawable;

/**
 * This class for a item contain icon,text and selectable
 * @author Wang Baoxi
 * @version 1.0
 *
 */
public class IconText implements Comparable<IconText>{
	
	private String mText = "";
	private Drawable mIcon;
	private boolean mSelect = false;
	private int sort;//排序值
	private String path = "";
	private boolean isDirectory;
	
	public IconText(String text, Drawable bullet, int sort, String path, boolean isDirectory) {
		mIcon = bullet;
		mText = text;
		this.sort = sort;
		this.path = path;
		this.isDirectory = isDirectory;
	}
	
	
	
	public boolean isDirectory() {
		return isDirectory;
	}



	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}



	public boolean isSelect() {
		return mSelect;
	}
	
	public void setSelect(boolean selectable) {
		mSelect = selectable;
	}
	
	public String getText() {
		return mText;
	}
	
	public void setText(String text) {
		mText = text;
	}
	
	public void setIcon(Drawable icon) {
		mIcon = icon;
	}
	
	public Drawable getIcon() {
		return mIcon;
	}

	/**
	 * @return the sort
	 */
	public int getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

	@Override
	public int compareTo(IconText other) {
		if(this.sort < other.getSort())
			return -1;
		else if(this.sort == other.getSort())
			return 0;
		else 
			return 1;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
