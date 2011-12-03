package org.openlegacy.terminal;

/**
 * Defines a field on a terminal screen
 * 
 */
public interface TerminalField {

	TerminalPosition getPosition();

	String getValue();

	void setValue(String value);

	int getLength();

	boolean isEditable();

	boolean isModified();

}
