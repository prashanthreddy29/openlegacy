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
package org.openlegacy.designtime.terminal.analyzer.support.fact_processors;

import org.openlegacy.designtime.terminal.analyzer.ScreenFact;
import org.openlegacy.terminal.TerminalField;

public class ScreenWindowFact implements ScreenFact {

	private TerminalField topBorderField;
	private TerminalField buttomBorderField;

	public ScreenWindowFact(TerminalField topBorderField, TerminalField buttomBorderField) {
		this.topBorderField = topBorderField;
		this.buttomBorderField = buttomBorderField;
	}

	public TerminalField getTopBorderField() {
		return topBorderField;
	}

	public TerminalField getButtomBorderField() {
		return buttomBorderField;
	}
}
