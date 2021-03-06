package org.jboss.tools.switchyard.reddeer.project;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.direct.preferences.Preferences;
import org.eclipse.reddeer.eclipse.core.resources.ProjectItem;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jboss.tools.switchyard.reddeer.condition.JUnitHasFinished;

/**
 * Extension for project item.
 * 
 * @author apodhrad
 * 
 */
public class ProjectItemExt {

	private ProjectItem projectItem;

	public ProjectItemExt(ProjectItem projectItem) {
		this.projectItem = projectItem;
	}

	@SuppressWarnings("unchecked")
	public void runAs(String menu) {
		projectItem.select();
		new ContextMenuItem(new WithMnemonicTextMatcher("Run As"), new MenuMatcher(menu)).select();
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}

	@SuppressWarnings("unchecked")
	public void runAs(Configuration config) {
		projectItem.select();
		new ContextMenuItem(new WithMnemonicTextMatcher("Run As"), config.getMatcher()).select();
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}

	@SuppressWarnings("unchecked")
	public void debugAs(Configuration config) {
		projectItem.select();
		new ContextMenuItem(new WithMnemonicTextMatcher("Debug As"), config.getMatcher()).select();
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}

	public void runAsJUnitTest() {
		String consoleOpenOnErr = Preferences.get("org.eclipse.debug.ui", "DEBUG.consoleOpenOnErr");
		String consoleOpenOnOut = Preferences.get("org.eclipse.debug.ui", "DEBUG.consoleOpenOnOut");
		Preferences.set("org.eclipse.debug.ui", "DEBUG.consoleOpenOnErr", "false");
		Preferences.set("org.eclipse.debug.ui", "DEBUG.consoleOpenOnOut", "false");

		runAs("JUnit Test");
		new WaitUntil(new JUnitHasFinished(), TimePeriod.LONG);

		Preferences.set("org.eclipse.debug.ui", "DEBUG.consoleOpenOnErr", consoleOpenOnErr);
		Preferences.set("org.eclipse.debug.ui", "DEBUG.consoleOpenOnOut", consoleOpenOnOut);
	}
	
	public void select() {
		projectItem.select();
	}
	
	public File toFile() {
		String projectName = projectItem.getProject().getName();
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		IFile file = project.getFile(joinStrings(projectItem.getTreeItem().getPath(), "/"));
		return file.getLocation().toFile();
	}
	
	private static String joinStrings(String[] strings, String delimiter) {
	    StringBuilder sbStr = new StringBuilder();
	    for (int i = 1, il = strings.length; i < il; i++) {
	        if (i > 0)
	            sbStr.append(delimiter);
	        sbStr.append(strings[i]);
	    }
	    return sbStr.toString();
	}
	
	public ProjectItem getProjectItem() {
		return projectItem;
	}

	public void delete() {
		Exception exception = null;
		for (int i = 0; i <= 10; i++) {
			try {
				exception = null;
				new WorkbenchShell().setFocus();
				projectItem.delete();
				break;
			} catch (Exception e) {
				exception = e;
			}
		}
		if (exception != null) {
			throw new RuntimeException("Cannot delete '" + projectItem.getName() + "'", exception);
		}
	}

	private class MenuMatcher extends BaseMatcher<String> {

		private String text;

		public MenuMatcher(String text) {
			super();
			this.text = text;
		}

		@Override
		public boolean matches(Object obj) {
			if (obj instanceof String) {
				String label = (String) obj;
				return label.contains(text);
			}
			return false;
		}

		@Override
		public void describeTo(Description desc) {
			desc.appendText("menu item containing '" + text + "'");
		}

	}

	public enum Configuration {

		JUNIT_TEST("JUnit Test");

		private Matcher<String> matcher;

		private Configuration(String config) {
			matcher = new RegexMatcher("[1-9]+ " + config + ".*");
		}

		public Matcher<String> getMatcher() {
			return matcher;
		}
	}
}
