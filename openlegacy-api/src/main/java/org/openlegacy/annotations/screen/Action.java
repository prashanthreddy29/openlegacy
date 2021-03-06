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
package org.openlegacy.annotations.screen;

import org.openlegacy.terminal.actions.TerminalAction;
import org.openlegacy.terminal.actions.TerminalAction.AdditionalKey;
import org.openlegacy.terminal.actions.TerminalActions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a terminal session action for a screen entity. Defined within {@link ScreenActions} annotation<br/>
 * Defines a an action to be performed on entities marked with {@link ScreenEntity} <br/>
 * Implementation might be simple using classes from : {@link TerminalActions}, or custom (commonly known as "macro") by
 * implementing {@link TerminalAction} <br/>
 * <br/>
 * Example:<br/>
 * <br/>
 * <code>@ScreenActions(actions = { @Action(action = TerminalActions.F2.class, displayName = "Save") }) <br/>public class ItemDetails {<br/>...<br/>}</code>
 * <br/>
 * 
 * @author Roi Mor
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Action {

	/**
	 * The action class to invoke. Typically {@link TerminalActions}.&lt;F KEY&gt;
	 * 
	 * @return terminal action
	 */
	Class<? extends TerminalAction> action();

	String displayName() default "";

	/**
	 * Not mandatory
	 */
	int row() default 0;

	/**
	 * Not mandatory
	 */
	int column() default 0;

	AdditionalKey additionalKey() default AdditionalKey.NONE;

	String alias() default "";

	/**
	 * Whether the action is global or not
	 * 
	 * @return Whether the action is global
	 */
	boolean global() default true;

	/**
	 * The Java field name to set focus in this action
	 * 
	 * @return The Java field name to set focus in this action
	 */
	String focusField() default "";
}
