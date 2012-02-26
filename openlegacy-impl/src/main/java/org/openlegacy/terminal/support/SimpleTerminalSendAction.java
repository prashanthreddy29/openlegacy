package org.openlegacy.terminal.support;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openlegacy.terminal.TerminalField;
import org.openlegacy.terminal.TerminalPosition;
import org.openlegacy.terminal.spi.TerminalSendAction;

import java.util.ArrayList;
import java.util.List;

public class SimpleTerminalSendAction implements TerminalSendAction {

	private List<TerminalField> modifiedFields = new ArrayList<TerminalField>();
	private Object command;
	private TerminalPosition cursorPosition;

	public SimpleTerminalSendAction(Object command) {
		this.command = command;
	}

	public List<TerminalField> getModifiedFields() {
		return modifiedFields;
	}

	public Object getCommand() {
		return command;
	}

	public TerminalPosition getCursorPosition() {
		return cursorPosition;
	}

	public void setCursorPosition(TerminalPosition cursorPosition) {
		this.cursorPosition = cursorPosition;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}