/*******************************************************************************
 * Copyright (c) 2012 OpenLegacy Inc.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     OpenLegacy Inc. - initial API and implementation
 *******************************************************************************/
package org.openlegacy.designtime.newproject.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "theme")
public class ProjectTheme {

	private String name;
	private String displayName;
	private String imageFile;
	private byte[] imageData;
	private String mobileTheme;

	public ProjectTheme() {}

	public ProjectTheme(String name, String displayName, String imageFile, byte[] imageData, String mobileTheme) {
		this.name = name;
		this.displayName = displayName;
		this.imageFile = imageFile;
		this.imageData = imageData;
		this.mobileTheme = mobileTheme;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "display-name")
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@XmlElement(name = "image-file")
	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	@XmlElement(name = "mobile-theme")
	public String getMobileTheme() {
		return this.mobileTheme;
	}

	public void setMobileTheme(String mobileTheme) {
		this.mobileTheme = mobileTheme;
	}
}
