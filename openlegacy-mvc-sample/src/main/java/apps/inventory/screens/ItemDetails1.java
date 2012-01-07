package apps.inventory.screens;

import org.openlegacy.annotations.screen.Action;
import org.openlegacy.annotations.screen.Identifier;
import org.openlegacy.annotations.screen.ScreenActions;
import org.openlegacy.annotations.screen.ScreenEntity;
import org.openlegacy.annotations.screen.ScreenField;
import org.openlegacy.annotations.screen.ScreenIdentifiers;
import org.openlegacy.terminal.actions.TerminalAction.AdditionalKey;
import org.openlegacy.terminal.actions.TerminalActions;

@ScreenIdentifiers(identifiers = { 
		@Identifier(row = 2, column = 26, value = "    Work with Item Master     "), 
		@Identifier(row = 4, column = 2, value = "Type choices, press Enter."), 
		@Identifier(row = 6, column = 2, value = "Item Number . . . . . . . . .") 
		})
@ScreenActions(actions = { 
		@Action(action = TerminalActions.F1.class, displayName = "Help", alias = "help"), 
		@Action(action = TerminalActions.F4.class, displayName = "Prompt", alias = "prompt"), 
		@Action(action = TerminalActions.F12.class, displayName = "Cancel", alias = "cancel"), 
		@Action(action = TerminalActions.F2.class ,additionalKey= AdditionalKey.SHIFT, displayName = "Delete", alias = "delete") 
		})
@ScreenEntity()
public class ItemDetails1 {

	@ScreenField(row = 6, column = 33 ,endColumn= 47 ,displayName = "Item Number", sampleValue = "2000")
    private String itemNumber;

	@ScreenField(row = 7, column = 33 ,editable= true ,displayName = "Item Description", sampleValue = "Tomato - VEG")
    private String itemDescription;

	@ScreenField(row = 8, column = 33 ,editable= true ,displayName = "Alpha Search", sampleValue = "TOMATO")
    private String alphaSearch;

	@ScreenField(row = 9, column = 33 ,editable= true ,displayName = "Superceding item To")
    private String supercedingItemto;

	@ScreenField(row = 10, column = 33 ,editable= true ,displayName = "Superceding Item From")
    private String supercedingItemfrom;

	@ScreenField(row = 11, column = 33 ,editable= true ,displayName = "Substitute item number")
    private String substituteItemNumber;

	@ScreenField(row = 12, column = 33 ,editable= true ,displayName = "Manufacturers Item No")
    private String manufacturersItemNo;

	@ScreenField(row = 13, column = 33 ,editable= true ,displayName = "Item weight")
    private String itemWeight;

	@ScreenField(row = 14, column = 33 ,editable= true ,displayName = "Item Class", sampleValue = "VEG")
    private String itemClass;

	@ScreenField(row = 14, column = 37 ,endColumn= 76 ,displayName = "Item Class", sampleValue = "Vegetables")
    private String itemClass1;

	@ScreenField(row = 15, column = 33 ,editable= true ,displayName = "Stock Group", sampleValue = "SG")
    private String stockGroup;

	@ScreenField(row = 15, column = 37 ,endColumn= 76 ,displayName = "Stock Group", sampleValue = "Generic stock group")
    private String stockGroup1;

	@ScreenField(row = 16, column = 33 ,editable= true ,displayName = "Unit of Measure")
    private String unitOfMeasure;

	@ScreenField(row = 17, column = 33 ,editable= true ,displayName = "Packing Multiplier")
    private String packingMultiplier;

	@ScreenField(row = 18, column = 33 ,editable= true ,displayName = "Outer Unit of Measure")
    private String outerUnitOfMeasure;

	@ScreenField(row = 19, column = 33 ,editable= true ,displayName = "Outer Quantity")
    private String outerQuantity;

	@ScreenField(row = 20, column = 33 ,editable= true ,displayName = "Pallet label required")
    private String palletLabelRequired;

	@ScreenField(row = 21, column = 33 ,editable= true ,displayName = "VAT Code")
    private String vatCode;

	private ItemDetails2 itemDetails2;
	
}