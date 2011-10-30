package org.openlegacy.designtime.generators;

import freemarker.template.TemplateException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import japa.parser.ParseException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class ScreenEntityAjGeneratorTest {

	@Test
	public void testSimple() throws Exception {
		testGenerate();
	}

	@Test
	public void testLightWeight() throws Exception {
		testGenerate();
	}

	@Test
	public void testHasGetter() throws Exception {
		testGenerate();
	}

	@Test
	public void testNotScreenEntity() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		new ScreenEntityAjGenerator().generate(getClass().getResourceAsStream("testNotScreenEntity.java"), baos);

		Assert.assertEquals(0, baos.toByteArray().length);
	}

	@Test
	public void testNonJavaFile() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			new ScreenEntityAjGenerator().generate(getClass().getResourceAsStream("testNonJavaFile.txt"), baos);
			Assert.fail("Parsing should have failed");
		} catch (ParseException e) {
			// good!
		}

	}

	private void testGenerate() throws Exception {
		String testMethodName = getTestMethodName();
		testGenerate(testMethodName + ".java", testMethodName + "_Aspect.aj");
	}

	private void testGenerate(String javaSource, String expectAspect) throws IOException, TemplateException, ParseException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		new ScreenEntityAjGenerator().generate(getClass().getResourceAsStream(javaSource), baos);

		byte[] expectedBytes = IOUtils.toByteArray(getClass().getResourceAsStream(expectAspect));
		Assert.assertEquals(initTestString(expectedBytes), initTestString(baos.toByteArray()));
	}

	private static String initTestString(byte[] expectedBytes) {
		return new String(expectedBytes).replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("\t", "");
	}

	protected String getTestMethodName() {
		StackTraceElement[] stackElements = Thread.currentThread().getStackTrace();
		String methodName = null;
		for (StackTraceElement stackTraceElement : stackElements) {
			String clsName = stackTraceElement.getClassName();
			methodName = stackTraceElement.getMethodName();
			try {
				Class<?> cls = Class.forName(clsName);
				Method method = cls.getMethod(methodName);
				Test test = method.getAnnotation(Test.class);
				if (test != null) {
					methodName = method.getName();
					break;
				}
			} catch (Exception ex) {
				// do nothing
			}
		}
		return methodName;
	}

}