// WARNING: DO NOT EDIT THIS FILE.
// You may push code into the target .java compilation unit if you wish to edit any member(s).
package org.openlegacy.as400.menus.web;

import java.util.*;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.openlegacy.as400.menus.*;

import java.io.IOException;
import org.openlegacy.modules.table.Table;
import org.openlegacy.modules.table.TableWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import org.openlegacy.terminal.ScreenEntity;
import org.openlegacy.terminal.TerminalSession;
import org.openlegacy.terminal.actions.TerminalActions;
import org.openlegacy.terminal.services.ScreenEntitiesRegistry;
import org.openlegacy.terminal.json.JsonSerializationUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;

import org.openlegacy.as400.menus.SendMessagesndmsg;

privileged @SuppressWarnings("unused") aspect SendMessagesndmsgController_Aspect {

	@Inject
	private TerminalSession SendMessagesndmsgController.terminalSession;

	@Inject
	private ScreenEntitiesRegistry SendMessagesndmsgController.screenEntitiesRegistry;

	
	// handle page initial display
    @RequestMapping(method = RequestMethod.GET)
    public String SendMessagesndmsgController.show(Model uiModel) {
    	SendMessagesndmsg sendMessagesndmsg = terminalSession.getEntity(SendMessagesndmsg.class);
	uiModel.addAttribute("sendMessagesndmsg", sendMessagesndmsg);
	// show the resulting page for SendMessagesndmsg
        return "SendMessagesndmsg";
    }

	
    @RequestMapping(value="/help", method = RequestMethod.GET)
    public @ResponseBody String SendMessagesndmsgController.systemHelp(HttpServletRequest request) throws IOException {
    	URL resource = request.getSession().getServletContext().getResource("/help/SendMessagesndmsg.html");
    	String result = "";
    	if (resource != null){
    		result = IOUtils.toString(resource.openStream());
    	}
    	return result;
    }

	// handle submit action
    @RequestMapping(method = RequestMethod.POST)
    public String SendMessagesndmsgController.submit(SendMessagesndmsg sendMessagesndmsg, Model uiModel, HttpServletRequest httpServletRequest) {
	ScreenEntity resultScreenEntity = terminalSession.doAction(TerminalActions.ENTER(), sendMessagesndmsg);
	// go to the controller for the resulting screen name
		if (resultScreenEntity != null){
			String screenEntityName = screenEntitiesRegistry.get(resultScreenEntity.getClass()).getEntityClassName();
			if (httpServletRequest.getParameter("partial") != null){
				sendMessagesndmsg = terminalSession.getEntity(SendMessagesndmsg.class);
		    	uiModel.addAttribute("sendMessagesndmsg", sendMessagesndmsg);
		        return "SendMessagesndmsg";
			}
        	return "redirect:" + screenEntityName;
		}
    	return "redirect:/";
		
    }
    
	// handle exit action
    @RequestMapping(params="action=exit", method = RequestMethod.POST)
    public String SendMessagesndmsgController.exit(SendMessagesndmsg sendMessagesndmsg, Model uiModel, HttpServletRequest httpServletRequest) {
	ScreenEntity resultScreenEntity = terminalSession.doAction(TerminalActions.F3(), sendMessagesndmsg);
	// go to the controller for the resulting screen name
		if (resultScreenEntity != null){
			String screenEntityName = screenEntitiesRegistry.get(resultScreenEntity.getClass()).getEntityClassName();
			if (httpServletRequest.getParameter("partial") != null){
				sendMessagesndmsg = terminalSession.getEntity(SendMessagesndmsg.class);
		    	uiModel.addAttribute("sendMessagesndmsg", sendMessagesndmsg);
		        return "SendMessagesndmsg";
			}
        	return "redirect:" + screenEntityName;
		}
    	return "redirect:/";
		
    }
	// handle prompt action
    @RequestMapping(params="action=prompt", method = RequestMethod.POST)
    public String SendMessagesndmsgController.prompt(SendMessagesndmsg sendMessagesndmsg, Model uiModel, HttpServletRequest httpServletRequest) {
	ScreenEntity resultScreenEntity = terminalSession.doAction(TerminalActions.F4(), sendMessagesndmsg);
	// go to the controller for the resulting screen name
		if (resultScreenEntity != null){
			String screenEntityName = screenEntitiesRegistry.get(resultScreenEntity.getClass()).getEntityClassName();
			if (httpServletRequest.getParameter("partial") != null){
				sendMessagesndmsg = terminalSession.getEntity(SendMessagesndmsg.class);
		    	uiModel.addAttribute("sendMessagesndmsg", sendMessagesndmsg);
		        return "SendMessagesndmsg";
			}
        	return "redirect:" + screenEntityName;
		}
    	return "redirect:/";
		
    }
	// handle refresh action
    @RequestMapping(params="action=refresh", method = RequestMethod.POST)
    public String SendMessagesndmsgController.refresh(SendMessagesndmsg sendMessagesndmsg, Model uiModel, HttpServletRequest httpServletRequest) {
	ScreenEntity resultScreenEntity = terminalSession.doAction(TerminalActions.F5(), sendMessagesndmsg);
	// go to the controller for the resulting screen name
		if (resultScreenEntity != null){
			String screenEntityName = screenEntitiesRegistry.get(resultScreenEntity.getClass()).getEntityClassName();
			if (httpServletRequest.getParameter("partial") != null){
				sendMessagesndmsg = terminalSession.getEntity(SendMessagesndmsg.class);
		    	uiModel.addAttribute("sendMessagesndmsg", sendMessagesndmsg);
		        return "SendMessagesndmsg";
			}
        	return "redirect:" + screenEntityName;
		}
    	return "redirect:/";
		
    }
	// handle additionalParameters action
    @RequestMapping(params="action=additionalParameters", method = RequestMethod.POST)
    public String SendMessagesndmsgController.additionalParameters(SendMessagesndmsg sendMessagesndmsg, Model uiModel, HttpServletRequest httpServletRequest) {
	ScreenEntity resultScreenEntity = terminalSession.doAction(TerminalActions.F10(), sendMessagesndmsg);
	// go to the controller for the resulting screen name
		if (resultScreenEntity != null){
			String screenEntityName = screenEntitiesRegistry.get(resultScreenEntity.getClass()).getEntityClassName();
			if (httpServletRequest.getParameter("partial") != null){
				sendMessagesndmsg = terminalSession.getEntity(SendMessagesndmsg.class);
		    	uiModel.addAttribute("sendMessagesndmsg", sendMessagesndmsg);
		        return "SendMessagesndmsg";
			}
        	return "redirect:" + screenEntityName;
		}
    	return "redirect:/";
		
    }
	// handle cancel action
    @RequestMapping(params="action=cancel", method = RequestMethod.POST)
    public String SendMessagesndmsgController.cancel(SendMessagesndmsg sendMessagesndmsg, Model uiModel, HttpServletRequest httpServletRequest) {
	ScreenEntity resultScreenEntity = terminalSession.doAction(TerminalActions.F12(), sendMessagesndmsg);
	// go to the controller for the resulting screen name
		if (resultScreenEntity != null){
			String screenEntityName = screenEntitiesRegistry.get(resultScreenEntity.getClass()).getEntityClassName();
			if (httpServletRequest.getParameter("partial") != null){
				sendMessagesndmsg = terminalSession.getEntity(SendMessagesndmsg.class);
		    	uiModel.addAttribute("sendMessagesndmsg", sendMessagesndmsg);
		        return "SendMessagesndmsg";
			}
        	return "redirect:" + screenEntityName;
		}
    	return "redirect:/";
		
    }
	
	
	@InitBinder
	public void SendMessagesndmsgController.initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
}