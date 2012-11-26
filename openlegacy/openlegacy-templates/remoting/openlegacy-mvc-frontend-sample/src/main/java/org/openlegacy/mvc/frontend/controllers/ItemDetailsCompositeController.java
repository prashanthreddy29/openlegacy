package org.openlegacy.mvc.frontend.controllers;

import org.openlegacy.mvc.remoting.db.model.StockItem;
import org.openlegacy.mvc.remoting.db.model.StockItemNote;
import org.openlegacy.mvc.remoting.entities.ItemDetails;
import org.openlegacy.mvc.remoting.services.OLStockItems;
import org.openlegacy.mvc.remoting.services.OLTerminalSession;
import org.openlegacy.terminal.TerminalSession;
import org.openlegacy.terminal.modules.table.TerminalDrilldownActions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Handles requests for screen ItemDetailsComposite
 */
@Controller
@RequestMapping("/ItemDetails")
public class ItemDetailsCompositeController {

	@Inject
	private OLStockItems stockItemsService;

	@Inject
	private TerminalSession terminalSession;

	// handle page initial display
	@RequestMapping(method = RequestMethod.GET)
	public String show(Model uiModel) {
		// get the item number from the host session
		Integer itemNumber = terminalSession.getEntity(ItemDetails.class).getItemNumber();

		addExtensionsToPage(uiModel, itemNumber);

		return "ItemDetailsComposite";
	}

	private void addExtensionsToPage(Model uiModel, Integer itemNumber) {
		// fetch relevant notes from the DB and pass the page
		StockItem stockItem = stockItemsService.getOrCreateStockItem(itemNumber);

		uiModel.addAttribute(stockItem);
		Collection<StockItemNote> notes = stockItem.getNotes().values();
		uiModel.addAttribute("notes", notes);
	}

	// handle page navigation with friendly URL for drill-down
	@RequestMapping(value = "/revise/{id}", method = RequestMethod.GET)
	public String revise(@PathVariable("id") Integer itemNumber, Model uiModel) {
		return performDrillDownAction(itemNumber, uiModel, "2");
	}

	// handle page navigation with friendly URL for drill-down
	@RequestMapping(value = "/display/{id}", method = RequestMethod.GET)
	public String display(@PathVariable("id") Integer itemNumber, Model uiModel) {
		return performDrillDownAction(itemNumber, uiModel, "5");
	}

	// handle page navigation with friendly URL for drill-down
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Integer itemNumber, Model uiModel) {
		return performDrillDownAction(itemNumber, uiModel, "4");
	}

	private String performDrillDownAction(Integer itemNumber, Model uiModel, String actionValue) {
		// terminalSession.getModule(Table.class).drillDown(ItemDetails.class, TerminalDrilldownActions.enter(actionValue),
		// itemNumber);
		// Note: cannot use commented line above, because Table module using spring application context, which cannot be
		// serialized
		((OLTerminalSession)terminalSession).doTableDrillDown(ItemDetails.class, TerminalDrilldownActions.enter(actionValue),
				itemNumber);

		addExtensionsToPage(uiModel, itemNumber);

		return "ItemDetailsComposite";
	}

	@RequestMapping(value = "/updateNote", method = RequestMethod.GET)
	public @ResponseBody
	String updateNode(@RequestParam("itemNumber") Integer itemNumber, @RequestParam("noteId") String noteId,
			@RequestParam("text") String text) {

		stockItemsService.addOrUpdateNote(itemNumber, noteId, text);
		return "";
	}

}
