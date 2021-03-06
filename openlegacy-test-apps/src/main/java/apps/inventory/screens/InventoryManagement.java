package apps.inventory.screens;

import org.openlegacy.annotations.screen.AssignedField;
import org.openlegacy.annotations.screen.Identifier;
import org.openlegacy.annotations.screen.ScreenEntity;
import org.openlegacy.annotations.screen.ScreenField;
import org.openlegacy.annotations.screen.ScreenIdentifiers;
import org.openlegacy.annotations.screen.ScreenNavigation;
import org.openlegacy.modules.menu.Menu.MenuEntity;
import org.openlegacy.terminal.actions.TerminalActions.F3;

@ScreenEntity(screenType = MenuEntity.class)
@ScreenIdentifiers(identifiers = { @Identifier(row = 1, column = 31, value = "Inventory Management") })
@ScreenNavigation(accessedFrom = MainMenu.class, assignedFields = { @AssignedField(field = "selection", value = "1") }, exitAction = F3.class)
public class InventoryManagement {

	@ScreenField(row = 21, column = 8, editable = true)
	private String selection;
}
