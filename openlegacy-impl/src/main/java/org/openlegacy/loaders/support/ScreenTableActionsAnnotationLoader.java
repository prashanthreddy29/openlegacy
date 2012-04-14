package org.openlegacy.loaders.support;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openlegacy.EntitiesRegistry;
import org.openlegacy.annotations.screen.ScreenTableActions;
import org.openlegacy.annotations.screen.TableAction;
import org.openlegacy.definitions.support.SimpleActionDefinition;
import org.openlegacy.exceptions.RegistryException;
import org.openlegacy.terminal.definitions.ScreenTableDefinition;
import org.openlegacy.terminal.spi.ScreenEntitiesRegistry;
import org.openlegacy.terminal.table.TerminalDrilldownAction;
import org.openlegacy.utils.ReflectionUtil;
import org.openlegacy.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;

@Component
public class ScreenTableActionsAnnotationLoader extends AbstractClassAnnotationLoader {

	private final static Log logger = LogFactory.getLog(ScreenTableActionsAnnotationLoader.class);

	public boolean match(Annotation annotation) {
		return annotation.annotationType() == ScreenTableActions.class;
	}

	public void load(EntitiesRegistry<?, ?> entitiesRegistry, Annotation annotation, Class<?> containingClass) {

		ScreenEntitiesRegistry screenEntitiesRegistry = (ScreenEntitiesRegistry)entitiesRegistry;

		ScreenTableDefinition screenTableDefinition = screenEntitiesRegistry.getTable(containingClass);
		if (screenTableDefinition == null) {
			throw (new RegistryException("Class " + containingClass + " was not defined as @ScreenTable"));
		}
		ScreenTableActions screenTableActions = (ScreenTableActions)annotation;

		TableAction[] actions = screenTableActions.actions();
		if (actions.length > 0) {
			for (TableAction action : actions) {
				Class<? extends TerminalDrilldownAction> theAction = action.action();
				TerminalDrilldownAction drilldownAction = ReflectionUtil.newInstance(theAction);
				drilldownAction.setActionValue(action.actionValue());
				SimpleActionDefinition actionDefinition = new SimpleActionDefinition(drilldownAction, action.displayName());

				if (StringUtils.isEmpty(action.alias())) {
					actionDefinition.setAlias(StringUtil.toJavaFieldName(action.displayName()));
				} else {
					actionDefinition.setAlias(action.alias());
				}

				screenTableDefinition.getActions().add(actionDefinition);
				if (logger.isDebugEnabled()) {
					logger.debug(MessageFormat.format("Action {0} - \"{1}\" was added to the registry for table {2}",
							theAction.getSimpleName(), action.displayName(), containingClass));
				}

			}
			logger.info(MessageFormat.format("Screen identifications for \"{0}\" was added to the screen registry",
					screenTableDefinition.getTableClass()));
		}
	}
}