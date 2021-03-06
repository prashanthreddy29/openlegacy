/*******************************************************************************
 * Copyright (c) 2012 OpenLegacy Inc.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     OpenLegacy Inc. - initial API and implementation
 *******************************************************************************/
package org.openlegacy.terminal.support;

import org.openlegacy.terminal.RowPart;
import org.openlegacy.terminal.TerminalField;
import org.openlegacy.terminal.TerminalRow;
import org.openlegacy.terminal.utils.TerminalEqualsHashcodeUtil;

import java.util.ArrayList;
import java.util.List;

public class SimpleTerminalRow implements TerminalRow {

	private static final long serialVersionUID = 1L;

	private int rowNumber;
	private List<TerminalField> fields = new ArrayList<TerminalField>();

	public SimpleTerminalRow(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public List<TerminalField> getFields() {
		return fields;
	}

	public TerminalField getField(int column) {
		return SnapshotUtils.getField(this, column);
	}

	public int getRowNumber() {
		return rowNumber;
	}

	/**
	 * Row text is calculated by the row length. Assuming row is fully populated with fields
	 */
	public String getText() {
		String value = SnapshotUtils.getRowText(this);
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TerminalRow)) {
			return false;
		}
		TerminalRow otherRow = (TerminalRow)obj;
		return TerminalEqualsHashcodeUtil.rowEquals(this, otherRow);
	}

	@Override
	public int hashCode() {
		return TerminalEqualsHashcodeUtil.rowHashCode(this);
	}

	@Override
	public String toString() {
		return SnapshotUtils.rowToString(this);
	}

	public List<RowPart> getRowParts() {
		return SnapshotUtils.getRowParts(this);
	}

	public String getText(int column, int length) {
		return SnapshotUtils.getRowText(this, column, length);
	}
}
