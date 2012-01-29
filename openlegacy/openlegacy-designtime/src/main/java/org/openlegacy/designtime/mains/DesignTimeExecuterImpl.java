package org.openlegacy.designtime.mains;

import freemarker.template.TemplateException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.openlegacy.designtime.analyzer.SnapshotsAnalyzer;
import org.openlegacy.designtime.terminal.analyzer.TerminalSnapshotsAnalyzer;
import org.openlegacy.designtime.terminal.generators.ScreenEntityJavaGenerator;
import org.openlegacy.designtime.terminal.generators.ScreenPojoCodeModel;
import org.openlegacy.designtime.terminal.generators.ScreenPojosAjGenerator;
import org.openlegacy.designtime.terminal.model.ScreenEntityDesigntimeDefinition;
import org.openlegacy.exceptions.UnableToGenerateSnapshotException;
import org.openlegacy.terminal.TerminalSnapshot;
import org.openlegacy.terminal.definitions.ScreenEntityDefinition;
import org.openlegacy.terminal.render.TerminalSnapshotImageRenderer;
import org.openlegacy.terminal.render.TerminalSnapshotRenderer;
import org.openlegacy.terminal.render.TerminalSnapshotTextRenderer;
import org.openlegacy.terminal.render.TerminalSnapshotXmlRenderer;
import org.openlegacy.utils.FileUtils;
import org.openlegacy.utils.ZipUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;

public class DesignTimeExecuterImpl implements DesignTimeExecuter {

	private static final String DEFAULT_SPRING_CONTEXT_FILE = "/src/main/resources/META-INF/spring/applicationContext.xml";
	private ApplicationContext applicationContext;

	public void createProject(String templateName, File baseDir, String projectName, String provider, String defaultPackageName)
			throws IOException {
		File targetZip = extractTemplate(templateName, baseDir);
		File targetPath = unzipTemplate(baseDir, projectName, targetZip);

		renameProject(projectName, targetPath);
		renameProjectPOM(projectName, targetPath);
		renameProviderInPOM(provider, targetPath);

		updateSpringContextWithDefaultPackage(defaultPackageName, targetPath);

		targetZip.delete();
	}

	private static void updateSpringContextWithDefaultPackage(String defaultPackageName, File targetPath) throws IOException,
			FileNotFoundException {
		File springFile = new File(targetPath, DEFAULT_SPRING_CONTEXT_FILE);

		String springFileContent = IOUtils.toString(new FileInputStream(springFile));

		springFileContent = springFileContent.replaceAll("<context:component-scan base-package=\".*\" />",
				MessageFormat.format("<context:component-scan base-package=\"{0}\" />", defaultPackageName));
		FileOutputStream fos = new FileOutputStream(springFile);
		IOUtils.write(springFileContent, fos);
	}

	private static void renameProject(String projectName, File targetPath) throws IOException, FileNotFoundException {
		File projectFile = new File(targetPath, ".project");
		String projectFileContent = IOUtils.toString(new FileInputStream(projectFile));

		// NOTE assuming all project templates starts with "openlegacy-"
		projectFileContent = projectFileContent.replaceAll("<name>openlegacy-.*</name>",
				MessageFormat.format("<name>{0}</name>", projectName));
		FileOutputStream fos = new FileOutputStream(projectFile);
		IOUtils.write(projectFileContent, fos);
	}

	private static void renameProjectPOM(String projectName, File targetPath) throws IOException, FileNotFoundException {
		File pomFile = new File(targetPath, "pom.xml");
		String pomFileContent = IOUtils.toString(new FileInputStream(pomFile));

		pomFileContent = pomFileContent.replaceFirst("<groupId>.*</groupId>",
				MessageFormat.format("<groupId>{0}</groupId>", projectName));
		pomFileContent = pomFileContent.replaceFirst("<artifactId>.*</artifactId>",
				MessageFormat.format("<artifactId>{0}</artifactId>", projectName));
		FileOutputStream fos = new FileOutputStream(pomFile);
		IOUtils.write(pomFileContent, fos);
	}

	private static void renameProviderInPOM(String provider, File targetPath) throws FileNotFoundException, IOException {
		File pomFile = new File(targetPath, "pom.xml");
		String pomFileContent = IOUtils.toString(new FileInputStream(pomFile));

		if (!provider.equals(DesignTimeExecuter.MOCK_PROVIDER)) {
			pomFileContent = pomFileContent.replace("<groupId>org.openlegacy</groupId>",
					"<groupId>org.openlegacy.providers</groupId>");
			pomFileContent = pomFileContent.replace("<artifactId>openlegacy-Impl</artifactId>",
					MessageFormat.format("<artifactId>{0}</artifactId>", provider));
		}

		FileOutputStream fos = new FileOutputStream(pomFile);
		IOUtils.write(pomFileContent, fos);
	}

	private static File unzipTemplate(File baseDir, String projectName, File targetZip) throws IOException {
		File targetPath = new File(baseDir, projectName);
		ZipUtil.unzip(targetZip.getAbsolutePath(), targetPath.getAbsolutePath());
		return targetPath;
	}

	private File extractTemplate(String templateName, File baseDir) throws FileNotFoundException, IOException {
		URL zipFile = getClass().getResource(MessageFormat.format("/templates/{0}.zip", templateName));

		File targetZip = new File(baseDir, templateName + ".zip");
		FileOutputStream targetZipOutputStream = new FileOutputStream(targetZip);
		IOUtils.copy(zipFile.openStream(), targetZipOutputStream);
		targetZipOutputStream.close();
		return targetZip;
	}

	public void generateScreens(File trailFile, File sourceDirectory, String packageDirectoryName,
			OverrideConfirmer overrideConfirmer) throws UnableToGenerateSnapshotException {
		ApplicationContext applicationContext = getApplicationContext();
		TerminalSnapshotsAnalyzer snapshotsAnalyzer = applicationContext.getBean(TerminalSnapshotsAnalyzer.class);

		Map<String, ScreenEntityDefinition> screenEntitiesDefinitions = snapshotsAnalyzer.analyzeTrail(trailFile.getAbsolutePath());
		Collection<ScreenEntityDefinition> screenDefinitions = screenEntitiesDefinitions.values();
		for (ScreenEntityDefinition screenEntityDefinition : screenDefinitions) {
			((ScreenEntityDesigntimeDefinition)screenEntityDefinition).setPackageName(packageDirectoryName.replaceAll("/", "."));
			FileOutputStream fos = null;
			try {
				File packageDir = new File(sourceDirectory, packageDirectoryName);
				String entityName = screenEntityDefinition.getEntityName();
				File file = new File(packageDir, MessageFormat.format("{0}.java", entityName));
				if (file.exists()) {
					boolean override = overrideConfirmer.isOverride(file);
					if (!override) {
						continue;
					}
				}
				fos = generateJava(screenEntityDefinition, file);

				File screenResourcesDir = new File(packageDir, entityName + "-resources");
				screenResourcesDir.mkdir();
				TerminalSnapshot snapshot = screenEntityDefinition.getSnapshot();

				generateResource(snapshot, entityName, screenResourcesDir, TerminalSnapshotTextRenderer.instance());
				generateResource(snapshot, entityName, screenResourcesDir, TerminalSnapshotImageRenderer.instance());
				generateResource(snapshot, entityName, screenResourcesDir, TerminalSnapshotXmlRenderer.instance());
			} catch (TemplateException e) {
				throw (new UnableToGenerateSnapshotException(e));
			} catch (IOException e) {
				throw (new UnableToGenerateSnapshotException(e));
			} finally {
				IOUtils.closeQuietly(fos);
			}
		}

	}

	private static FileOutputStream generateJava(ScreenEntityDefinition screenEntityDefinition, File file)
			throws FileNotFoundException, TemplateException, IOException {
		FileOutputStream fos;
		file.getParentFile().mkdirs();
		fos = new FileOutputStream(file);
		new ScreenEntityJavaGenerator().generate(screenEntityDefinition, fos);
		return fos;
	}

	private static void generateResource(TerminalSnapshot terminalSnapshot, String entityName, File screenResourcesDir,
			TerminalSnapshotRenderer renderer) {
		FileOutputStream fos = null;
		try {
			File screenTextFile = new File(screenResourcesDir, MessageFormat.format("{0}.{1}", entityName,
					renderer.getFileFormat()));
			fos = new FileOutputStream(screenTextFile);
			renderer.render(terminalSnapshot, fos);
		} catch (FileNotFoundException e) {
			throw (new UnableToGenerateSnapshotException(e));
		} finally {
			IOUtils.closeQuietly(fos);
		}
	}

	private synchronized ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			applicationContext = new ClassPathXmlApplicationContext("/openlegacy-designtime-context.xml");
		}
		return applicationContext;
	}

	public void generateAspect(File javaFile) {

		OutputStream fos = null;
		try {
			File aspectFile = new File(javaFile.getParentFile(), FileUtils.fileWithoutExtenstion(javaFile.getName())
					+ "_Aspect.aj");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			CompilationUnit compilationUnit = JavaParser.parse(javaFile);
			ScreenPojoCodeModel screenPojoCodeModel = new ScreenPojosAjGenerator().generateScreenEntity(compilationUnit,
					getMainType(compilationUnit), baos);
			if (screenPojoCodeModel.isRelevant()) {
				fos = new FileOutputStream(aspectFile);
				fos.write(baos.toByteArray());
			}
		} catch (IOException e) {
			throw (new UnableToGenerateSnapshotException(e));
		} catch (TemplateException e) {
			throw (new UnableToGenerateSnapshotException(e));
		} catch (ParseException e) {
			throw (new UnableToGenerateSnapshotException(e));
		} finally {
			IOUtils.closeQuietly(fos);
		}

	}

	private static ClassOrInterfaceDeclaration getMainType(CompilationUnit compilationUnit) {
		return (ClassOrInterfaceDeclaration)compilationUnit.getTypes().get(0);
	}

	public void initialize() {
		// initialize application context & analyzer
		getApplicationContext().getBean(SnapshotsAnalyzer.class);
	}
}