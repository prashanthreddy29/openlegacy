package org.openlegacy.terminal.support;

import org.openlegacy.support.SessionModuleAdapter;
import org.openlegacy.terminal.TerminalConnection;
import org.openlegacy.terminal.TerminalConnectionListener;
import org.openlegacy.terminal.TerminalSession;
import org.openlegacy.terminal.TerminalSessionModule;
import org.openlegacy.terminal.spi.TerminalSendAction;

import java.io.Serializable;

/**
 * Define a terminal session override-able methods which happens before & after a terminal session action
 * 
 */
public abstract class TerminalSessionModuleAdapter extends SessionModuleAdapter<TerminalSession> implements TerminalSessionModule, TerminalConnectionListener, Serializable {

	private static final long serialVersionUID = 1L;

	public void beforeSendAction(TerminalConnection terminalConnection, TerminalSendAction terminalSendAction) {
		// allow override
	}

	public void afterSendAction(TerminalConnection terminalConnection) {
		// allow override
	}

	public void destroy() {
		// allow override
	}
}
