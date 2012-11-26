package org.openlegacy.mvc.frontend.controllers;

import org.openlegacy.mvc.remoting.db.model.StockItem;
import org.openlegacy.mvc.remoting.entities.ItemDetails;
import org.openlegacy.mvc.remoting.services.OLStockItems;
import org.openlegacy.terminal.ScreenEntity;
import org.openlegacy.terminal.TerminalSession;
import org.openlegacy.terminal.actions.TerminalActions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Handles requests for screen ItemDetails
 */
@Controller
@RequestMapping("/ItemDetails")
public class ItemDetailsController {

	@Inject
	private TerminalSession terminalSession;

	@Inject
	private OLStockItems stockItemsService;

	// handle page initial display
	@RequestMapping(method = RequestMethod.GET, params = "partial=1")
	public String show(Model uiModel) {

		ItemDetails ItemDetails = terminalSession.getEntity(ItemDetails.class);
		uiModel.addAttribute(ItemDetails);

		StockItem stockItem = stockItemsService.getOrCreateStockItem(ItemDetails.getItemNumber());
		uiModel.addAttribute(stockItem);

		if (stockItem.getImages().size() > 0) {
			// show the item first image on the details page
			uiModel.addAttribute("imageId", stockItem.getImages().get(0).getId());
		}

		return "ItemDetails";
	}

	// handle submit action
	@RequestMapping(method = RequestMethod.POST)
	public String submit(ItemDetails ItemDetails, Model uiModel, HttpServletRequest request) {
		ScreenEntity resultScreenEntity = terminalSession.doAction(TerminalActions.ENTER(), ItemDetails);

		StockItem stockItem = stockItemsService.getOrCreateStockItem(ItemDetails.getItemNumber());
		stockItem.setDescription(request.getParameter("description"));
		stockItem.setVideoUrl(request.getParameter("videoUrl"));
		stockItemsService.updateStockItem(stockItem);

		// go to the controller for the resulting screen name
		if (resultScreenEntity != null) {
			ItemDetails = terminalSession.getEntity(ItemDetails.class);
			uiModel.addAttribute(ItemDetails);
			uiModel.addAttribute(stockItem);
			return "ItemDetails";
		}

		return "redirect:/";

	}
	// actions are generated within ItemDetailsController_Aspect.aj

}
