// WARNING: DO NOT EDIT THIS FILE.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package apps.inventory.screens;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

privileged @SuppressWarnings("unused") aspect ItemDetails2StockInfoTable_Aspect {
    
    declare @type: ItemDetails2.StockInfo : @Component;
	declare @type: ItemDetails2.StockInfo : @Scope("prototype");
    
	
	
    
    public String ItemDetails2.StockInfo.getListPrice(){
    	return this.listPrice;
    }
    
    public void ItemDetails2.StockInfo.setListPrice(String listPrice){
    	this.listPrice = listPrice;
    }

    public String ItemDetails2.StockInfo.getStandardUnitCost(){
    	return this.standardUnitCost;
    }
    
    public void ItemDetails2.StockInfo.setStandardUnitCost(String standardUnitCost){
    	this.standardUnitCost = standardUnitCost;
    }

}