package org.openlegacy.modules.support.menu;

import org.openlegacy.modules.menu.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class SimpleMenuItem implements MenuItem {

	private Class<?> targetEntity;
	private List<MenuItem> menuItems = new ArrayList<MenuItem>();
	private String displayName;

	public SimpleMenuItem(Class<?> targetEntity, String displayName) {
		this.targetEntity = targetEntity;
		this.displayName = displayName;
	}

	public Class<?> getTargetEntity() {
		return targetEntity;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public String getDisplayName() {
		return displayName;
	}

}