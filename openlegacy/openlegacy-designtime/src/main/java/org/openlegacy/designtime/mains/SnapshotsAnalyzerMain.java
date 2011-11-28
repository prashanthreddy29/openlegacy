package org.openlegacy.designtime.mains;

import org.openlegacy.designtime.terminal.analyzer.TerminalSnapshotsAnalyzer;
import org.openlegacy.designtime.terminal.analyzer.TerminalSnapshotsLoader;
import org.openlegacy.designtime.terminal.generators.ScreenEntityJavaGenerator;
import org.openlegacy.terminal.TerminalSnapshot;
import org.openlegacy.terminal.definitions.ScreenEntityDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.List;

public class SnapshotsAnalyzerMain {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage:\njava org.openlegacy.designtime.mains.SnapshotsAnalyzerMain screens-resource-folder output-folder package");
			return;
		}
		String inputFolderName = args[0];
		String outputFolderName = args[1];
		String packageFolder = args[2].replaceAll(".", "/");

		File outputFolder = new File(outputFolderName, packageFolder);

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/openlegacy-designtime-context.xml");

		TerminalSnapshotsLoader snapshotsLoader = applicationContext.getBean(TerminalSnapshotsLoader.class);
		try {
			List<TerminalSnapshot> snapshots = snapshotsLoader.loadSnapshots(inputFolderName);
			TerminalSnapshotsAnalyzer snapshotsAnalyzer = applicationContext.getBean(TerminalSnapshotsAnalyzer.class);
			Collection<ScreenEntityDefinition> screenDefinitions = snapshotsAnalyzer.analyzeSnapshots(snapshots).values();
			for (ScreenEntityDefinition screenEntityDefinition : screenDefinitions) {
				File outputFile = new File(outputFolder, screenEntityDefinition.getEntityName() + ".java");
				FileOutputStream output = new FileOutputStream(outputFile);
				new ScreenEntityJavaGenerator().generate(screenEntityDefinition, output);
				output.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}