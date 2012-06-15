// WARNING: DO NOT EDIT THIS FILE.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.openlegacy.terminal.samples.model;

import java.util.*;
import org.openlegacy.terminal.ScreenEntity;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

privileged @SuppressWarnings("unused") aspect DemoEnvironment_Aspect {
    
    declare @type: DemoEnvironment : @Component;
	declare @type: DemoEnvironment : @Scope("prototype");
    

    declare parents: DemoEnvironment implements ScreenEntity;
    private String DemoEnvironment.focusField;
    
	

	

    

    public Integer DemoEnvironment.getCompany(){
    	return this.company;
    }
    
    public void DemoEnvironment.setCompany(Integer company){
    	this.company = company;
    }



    public Integer DemoEnvironment.getMenuSelection(){
    	return this.menuSelection;
    }
    
    public void DemoEnvironment.setMenuSelection(Integer menuSelection){
    	this.menuSelection = menuSelection;
    }




    public String DemoEnvironment.getFocusField(){
    	return focusField;
    }
    public void DemoEnvironment.setFocusField(String focusField){
    	this.focusField = focusField;
    }
    
}