package org.openlegacy.terminal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.openlegacy.recognizers.RecognizersSuite;
import org.openlegacy.terminal.mock.MockTerminalConnectionTest;
import org.openlegacy.terminal.modules.login.DefaultLoginModuleFailedTest;
import org.openlegacy.terminal.modules.login.DefaultLoginModuleTest;
import org.openlegacy.terminal.modules.navigation.DefaultNavigationModuleTest;
import org.openlegacy.terminal.modules.table.DefaultTableDrilldownPerformerTest;
import org.openlegacy.terminal.modules.trail.XmlTrailWriterTest;
import org.openlegacy.terminal.support.DefaultSessionNavigatorTest;
import org.openlegacy.terminal.support.DefaultTerminalSessionTest;
import org.openlegacy.terminal.support.binders.ScreenEntityTablesBinderTest;

@RunWith(Suite.class)
@SuiteClasses({ DefaultScreenEntityBinderTest.class, RecognizersSuite.class, XmlTrailWriterTest.class,
		DefaultLoginModuleTest.class, DefaultLoginModuleFailedTest.class, DefaultTerminalSessionTest.class,
		DefaultSessionNavigatorTest.class, MockTerminalConnectionTest.class, ScreenEntityTablesBinderTest.class,
		DefaultNavigationModuleTest.class, DefaultTableDrilldownPerformerTest.class })
public class AllTests {

}