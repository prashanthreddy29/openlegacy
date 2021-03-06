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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openlegacy.terminal.ScreenEntity;
import org.openlegacy.terminal.modules.table.TerminalDrilldownActions.EnterDrilldownAction;
import org.openlegacy.terminal.table.TerminalDrilldownAction;

/**
 * A table action for a screen table entity. Defined within {@link ScreenTableActions}<br/>
 * 
 * @author Roi Mor
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface TableAction {

	Class<? extends TerminalDrilldownAction> action() default EnterDrilldownAction.class;

	boolean defaultAction() default false;

	String actionValue() default "";

	String displayName();

	String alias() default "";

	Class<?> targetEntity() default ScreenEntity.NONE.class;
}
