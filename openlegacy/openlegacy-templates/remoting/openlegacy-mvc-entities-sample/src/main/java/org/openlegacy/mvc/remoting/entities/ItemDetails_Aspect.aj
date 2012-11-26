// WARNING: DO NOT EDIT THIS FILE.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.openlegacy.mvc.remoting.entities;

import org.openlegacy.mvc.remoting.entities.ItemDetails.StockGroup;
import org.openlegacy.terminal.ScreenEntity;

import java.io.Serializable;

privileged @SuppressWarnings("unused")
aspect ItemDetails_Aspect {

	declare parents: ItemDetails implements ScreenEntity, Serializable;
	private String ItemDetails.focusField;

	public String ItemDetails.getAlphaSearch() {
		return this.alphaSearch;
	}

	public void ItemDetails.setAlphaSearch(String alphaSearch) {
		this.alphaSearch = alphaSearch;
	}

	public String ItemDetails.getItemDescription() {
		return this.itemDescription;
	}

	public void ItemDetails.setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public ItemDetails2 ItemDetails.getItemDetails2() {
		return this.itemDetails2;
	}

	public Integer ItemDetails.getItemNumber() {
		return this.itemNumber;
	}

	public void ItemDetails.setItemNumber(Integer itemNumber) {
		this.itemNumber = itemNumber;
	}

	public Integer ItemDetails.getItemWeight() {
		return this.itemWeight;
	}

	public void ItemDetails.setItemWeight(Integer itemWeight) {
		this.itemWeight = itemWeight;
	}

	public String ItemDetails.getManufacturersItemNo() {
		return this.manufacturersItemNo;
	}

	public void ItemDetails.setManufacturersItemNo(String manufacturersItemNo) {
		this.manufacturersItemNo = manufacturersItemNo;
	}

	public Boolean ItemDetails.getPalletLabelRequired() {
		return this.palletLabelRequired;
	}

	public void ItemDetails.setPalletLabelRequired(Boolean palletLabelRequired) {
		this.palletLabelRequired = palletLabelRequired;
	}

	public StockGroup ItemDetails.getStockGroup() {
		return this.stockGroup;
	}

	public void ItemDetails.setStockGroup(StockGroup stockGroup) {
		this.stockGroup = stockGroup;
	}

	public String ItemDetails.getSubstituteItemNumber() {
		return this.substituteItemNumber;
	}

	public void ItemDetails.setSubstituteItemNumber(String substituteItemNumber) {
		this.substituteItemNumber = substituteItemNumber;
	}

	public String ItemDetails.getSupercedingItemfrom() {
		return this.supercedingItemfrom;
	}

	public void ItemDetails.setSupercedingItemfrom(String supercedingItemfrom) {
		this.supercedingItemfrom = supercedingItemfrom;
	}

	public String ItemDetails.getSupercedingItemto() {
		return this.supercedingItemto;
	}

	public void ItemDetails.setSupercedingItemto(String supercedingItemto) {
		this.supercedingItemto = supercedingItemto;
	}

	public String ItemDetails.getFocusField() {
		return focusField;
	}

	public void ItemDetails.setFocusField(String focusField) {
		this.focusField = focusField;
	}

}
