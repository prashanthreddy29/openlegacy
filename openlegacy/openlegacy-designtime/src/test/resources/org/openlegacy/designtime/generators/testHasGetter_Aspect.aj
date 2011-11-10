// WARNING: DO NOT EDIT THIS FILE.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.someorg.examples.screens;

import java.util.List;
import org.openlegacy.terminal.ScreenEntity;
import org.openlegacy.terminal.TerminalField;
import org.openlegacy.terminal.TerminalScreen;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

privileged @SuppressWarnings("unused") aspect SignOn_Aspect {
    
    declare @type: SignOn : @Component;
	declare @type: SignOn : @Scope("prototype");
    
    private TerminalScreen SignOn.terminalScreen;

    declare parents: SignOn implements ScreenEntity;
    private String SignOn.focusField;
    
	
    private TerminalField SignOn.passwordField;
	
    private TerminalField SignOn.userField;
    
    public TerminalScreen SignOn.getTerminalScreen(){
		return terminalScreen;
    }

    public String SignOn.getPassword(){
    	return this.password;
    }
    

    public TerminalField SignOn.getPasswordField(){
    	return passwordField;
    }
    

    public TerminalField SignOn.getUserField(){
    	return userField;
    }

    public String SignOn.getFocusField(){
    	return focusField;
    }
    public void SignOn.setFocusField(String focusField){
    	this.focusField = focusField;
    }
    
}
