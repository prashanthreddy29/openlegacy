package org.openlegacy.providers.h3270;

import org.h3270.host.S3270;
import org.h3270.host.S3270Screen;
import org.openlegacy.terminal.TerminalConnection;
import org.openlegacy.terminal.TerminalField;
import org.openlegacy.terminal.TerminalSnapshot;
import org.openlegacy.terminal.spi.TerminalSendAction;

import java.util.List;

public class H3270Connection implements TerminalConnection {

	private S3270 s3270Session;

	// adding sequence support to tn5250j which doesn't support it
	private int sequence = 1;

	public H3270Connection(S3270 s3270Session) {
		this.s3270Session = s3270Session;
	}

	public TerminalSnapshot getSnapshot() {
		s3270Session.updateScreen();
		return new H3270TerminalSnapshot((S3270Screen)s3270Session.getScreen(), sequence);
	}

	public TerminalSnapshot fetchSnapshot() {
		return getSnapshot();
	}

	public void doAction(TerminalSendAction terminalSendAction) {
		sequence++;
		TerminalSnapshot snapshot = getSnapshot();
		List<TerminalField> modifiedFields = terminalSendAction.getModifiedFields();
		for (TerminalField modifiedField : modifiedFields) {
			H3270TerminalField field = (H3270TerminalField)snapshot.getField(modifiedField.getPosition());
			field.setValue(modifiedField.getValue());
			if (field.getPosition().equals(terminalSendAction.getCursorPosition())) {
				field.setFocus();
			}
		}
		s3270Session.doKey(terminalSendAction.getCommand().toString());
		sequence++;
	}

	public Object getDelegate() {
		return s3270Session;
	}

	public String getSessionId() {
		// TODO better session id handling
		return String.valueOf(s3270Session.hashCode());
	}

	public boolean isConnected() {
		return s3270Session.isConnected();
	}

	public void disconnect() {
		s3270Session.disconnect();
	}

}
