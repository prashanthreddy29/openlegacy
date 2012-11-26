// WARNING: DO NOT EDIT THIS FILE.
// You may push code into the target .java compilation unit if you wish to edit any member(s).
package org.openlegacy.mvc.frontend.controllers;

import org.apache.commons.io.IOUtils;
import org.openlegacy.modules.table.Table;
import org.openlegacy.modules.table.TableWriter;
import org.openlegacy.mvc.remoting.entities.Warehouses;
import org.openlegacy.terminal.ScreenEntity;
import org.openlegacy.terminal.TerminalSession;
import org.openlegacy.terminal.actions.TerminalActions;
import org.openlegacy.terminal.services.ScreenEntitiesRegistry;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

privileged aspect WarehousesController_Aspect {

	@Inject
	private TerminalSession WarehousesController.terminalSession;

	@Inject
	private ScreenEntitiesRegistry WarehousesController.screenEntitiesRegistry;

	@Inject
	private TableWriter WarehousesController.tableWriter;

	// handle page initial display
	@RequestMapping(method = RequestMethod.GET)
	public String WarehousesController.show(Model uiModel) {
		Warehouses warehouses = terminalSession.getEntity(Warehouses.class);
		uiModel.addAttribute("warehouses", warehouses);
		// show the resulting page for Warehouses
		return "Warehouses";
	}

	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public @ResponseBody
	String WarehousesController.systemHelp(HttpServletRequest request) throws IOException {
		URL resource = request.getSession().getServletContext().getResource("/help/Warehouses.html");
		String result = "";
		if (resource != null) {
			result = IOUtils.toString(resource.openStream());
		}
		return result;
	}

	// handle submit action
	@RequestMapping(method = RequestMethod.POST)
	public String WarehousesController.submit(Warehouses warehouses, Model uiModel, HttpServletRequest httpServletRequest) {
		ScreenEntity resultScreenEntity = terminalSession.doAction(TerminalActions.ENTER(), warehouses);
		// go to the controller for the resulting screen name
		if (resultScreenEntity != null) {
			String screenEntityName = screenEntitiesRegistry.get(resultScreenEntity.getClass()).getEntityClassName();
			if (httpServletRequest.getParameter("partial") != null) {
				warehouses = terminalSession.getEntity(Warehouses.class);
				uiModel.addAttribute("warehouses", warehouses);
				return "Warehouses";
			}
			return "redirect:" + screenEntityName;
		}
		return "redirect:/";

	}

	// handle help action
	@RequestMapping(params = "action=help", method = RequestMethod.POST)
	public String WarehousesController.help(Warehouses warehouses, Model uiModel, HttpServletRequest httpServletRequest) {
		ScreenEntity resultScreenEntity = terminalSession.doAction(TerminalActions.F1(), warehouses);
		// go to the controller for the resulting screen name
		if (resultScreenEntity != null) {
			String screenEntityName = screenEntitiesRegistry.get(resultScreenEntity.getClass()).getEntityClassName();
			if (httpServletRequest.getParameter("partial") != null) {
				warehouses = terminalSession.getEntity(Warehouses.class);
				uiModel.addAttribute("warehouses", warehouses);
				return "Warehouses";
			}
			return "redirect:" + screenEntityName;
		}
		return "redirect:/";

	}

	// handle exit action
	@RequestMapping(params = "action=exit", method = RequestMethod.POST)
	public String WarehousesController.exit(Warehouses warehouses, Model uiModel, HttpServletRequest httpServletRequest) {
		ScreenEntity resultScreenEntity = terminalSession.doAction(TerminalActions.F3(), warehouses);
		// go to the controller for the resulting screen name
		if (resultScreenEntity != null) {
			String screenEntityName = screenEntitiesRegistry.get(resultScreenEntity.getClass()).getEntityClassName();
			if (httpServletRequest.getParameter("partial") != null) {
				warehouses = terminalSession.getEntity(Warehouses.class);
				uiModel.addAttribute("warehouses", warehouses);
				return "Warehouses";
			}
			return "redirect:" + screenEntityName;
		}
		return "redirect:/";

	}

	// handle create action
	@RequestMapping(params = "action=create", method = RequestMethod.POST)
	public String WarehousesController.create(Warehouses warehouses, Model uiModel, HttpServletRequest httpServletRequest) {
		ScreenEntity resultScreenEntity = terminalSession.doAction(TerminalActions.F6(), warehouses);
		// go to the controller for the resulting screen name
		if (resultScreenEntity != null) {
			String screenEntityName = screenEntitiesRegistry.get(resultScreenEntity.getClass()).getEntityClassName();
			if (httpServletRequest.getParameter("partial") != null) {
				warehouses = terminalSession.getEntity(Warehouses.class);
				uiModel.addAttribute("warehouses", warehouses);
				return "Warehouses";
			}
			return "redirect:" + screenEntityName;
		}
		return "redirect:/";

	}

	// handle cancel action
	@RequestMapping(params = "action=cancel", method = RequestMethod.POST)
	public String WarehousesController.cancel(Warehouses warehouses, Model uiModel, HttpServletRequest httpServletRequest) {
		ScreenEntity resultScreenEntity = terminalSession.doAction(TerminalActions.F12(), warehouses);
		// go to the controller for the resulting screen name
		if (resultScreenEntity != null) {
			String screenEntityName = screenEntitiesRegistry.get(resultScreenEntity.getClass()).getEntityClassName();
			if (httpServletRequest.getParameter("partial") != null) {
				warehouses = terminalSession.getEntity(Warehouses.class);
				uiModel.addAttribute("warehouses", warehouses);
				return "Warehouses";
			}
			return "redirect:" + screenEntityName;
		}
		return "redirect:/";

	}

	// handle previous action
	@RequestMapping(params = "action=previous", method = RequestMethod.POST)
	public String WarehousesController.previous(Warehouses warehouses, Model uiModel, HttpServletRequest httpServletRequest) {
		ScreenEntity resultScreenEntity = terminalSession.doAction(TerminalActions.PAGEUP(), warehouses);
		// go to the controller for the resulting screen name
		if (resultScreenEntity != null) {
			String screenEntityName = screenEntitiesRegistry.get(resultScreenEntity.getClass()).getEntityClassName();
			if (httpServletRequest.getParameter("partial") != null) {
				warehouses = terminalSession.getEntity(Warehouses.class);
				uiModel.addAttribute("warehouses", warehouses);
				return "Warehouses";
			}
			return "redirect:" + screenEntityName;
		}
		return "redirect:/";

	}

	// handle next action
	@RequestMapping(params = "action=next", method = RequestMethod.POST)
	public String WarehousesController.next(Warehouses warehouses, Model uiModel, HttpServletRequest httpServletRequest) {
		ScreenEntity resultScreenEntity = terminalSession.doAction(TerminalActions.PAGEDOWN(), warehouses);
		// go to the controller for the resulting screen name
		if (resultScreenEntity != null) {
			String screenEntityName = screenEntitiesRegistry.get(resultScreenEntity.getClass()).getEntityClassName();
			if (httpServletRequest.getParameter("partial") != null) {
				warehouses = terminalSession.getEntity(Warehouses.class);
				uiModel.addAttribute("warehouses", warehouses);
				return "Warehouses";
			}
			return "redirect:" + screenEntityName;
		}
		return "redirect:/";

	}

	// export to excel
	@RequestMapping(value = "/excel", method = RequestMethod.GET)
	public void WarehousesController.excel(HttpServletResponse response) throws IOException {
		List<Warehouses.WarehousesRecord> records = terminalSession.getModule(Table.class).collectOne(Warehouses.class,
				Warehouses.WarehousesRecord.class);
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=\"Warehouses.xls\"");
		tableWriter.writeTable(records, response.getOutputStream());
	}

	@InitBinder
	public void WarehousesController.initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

}
