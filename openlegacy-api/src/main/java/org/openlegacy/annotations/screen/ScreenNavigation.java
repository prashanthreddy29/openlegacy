package org.openlegacy.annotations.screen;

import org.openlegacy.terminal.actions.TerminalAction;
import org.openlegacy.terminal.actions.TerminalActions;
import org.openlegacy.terminal.actions.TerminalAction.AdditionalKey;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ScreenNavigation {

	Class<?> accessedFrom();

	Class<? extends TerminalAction> terminalAction() default TerminalActions.ENTER.class;

	AdditionalKey additionalKey() default AdditionalKey.NONE;
	
	AssignedField[] assignedFields() default {};

	Class<? extends TerminalAction> exitAction() default TerminalActions.F3.class;

	AdditionalKey exitAdditionalKey() default AdditionalKey.NONE;
}
