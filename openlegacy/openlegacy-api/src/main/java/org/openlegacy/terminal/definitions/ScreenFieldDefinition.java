package org.openlegacy.terminal.definitions;

import org.openlegacy.definitions.FieldDefinition;
import org.openlegacy.terminal.TerminalPosition;
import org.openlegacy.terminal.TerminalPositionContainer;

/**
 * Defines a mapping between a screenEntity java field name and it's screen position and length
 * 
 */
public interface ScreenFieldDefinition extends FieldDefinition, TerminalPositionContainer, Comparable<ScreenFieldDefinition> {

	TerminalPosition getPosition();

	int getLength();

	boolean isEditable();
}
