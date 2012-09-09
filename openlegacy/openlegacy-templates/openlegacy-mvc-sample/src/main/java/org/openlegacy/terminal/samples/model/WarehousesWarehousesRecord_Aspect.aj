// WARNING: DO NOT EDIT THIS FILE.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.openlegacy.terminal.samples.model;

import java.util.List;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.openlegacy.terminal.samples.model.Warehouses.WarehousesRecord;

privileged @SuppressWarnings("unused") aspect WarehousesRecordTable_Aspect {
    
    public Integer WarehousesRecord.getAction_(){
    	return this.action_;
    }
    
    public void WarehousesRecord.setAction_(Integer action_){
    	this.action_ = action_;
    }

    public String WarehousesRecord.getDescription(){
    	return this.description;
    }
    

    public Integer WarehousesRecord.getWhse(){
    	return this.whse;
    }
    


    public int WarehousesRecord.hashCode(){
		return HashCodeBuilder.reflectionHashCode(this);
    }

    public boolean WarehousesRecord.equals(Object other){
    	// TODO exclude terminal fields
		return EqualsBuilder.reflectionEquals(this,other);
    }
}
