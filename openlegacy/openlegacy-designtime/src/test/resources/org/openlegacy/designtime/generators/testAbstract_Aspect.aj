// WARNING: DO NOT EDIT THIS FILE.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.someorg.examples.screens;

import java.util.List;
import org.openlegacy.terminal.ScreenEntity;
import org.openlegacy.terminal.TerminalField;
import org.openlegacy.terminal.TerminalSnapshot;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

privileged @SuppressWarnings("unused") aspect AbstractScreen_Aspect {
    
    

    
	
    private TerminalField AbstractScreen.messageField;
    

    public String AbstractScreen.getMessage(){
    	return this.message;
    }
    

    public TerminalField AbstractScreen.getMessageField(){
    	return messageField;
    }

    
}