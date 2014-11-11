package org.jboss.tools.runtime.reddeer.preference;

import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.workbench.preference.WorkbenchPreferencePage;
import org.jboss.tools.runtime.reddeer.wizard.DownloadRuntimesWizard;

/**
 * Represents preference page <i>JBoss Runtime Detection</i>.
 * 
 * @author tsedmik
 */
public class JBossRuntimeDetection extends WorkbenchPreferencePage {

	public JBossRuntimeDetection() {
		super("JBoss Tools", "JBoss Runtime Detection");
	}

	public DownloadRuntimesWizard downloadRuntime() {
		new PushButton("Download...").click();
		new WaitUntil(new ShellWithTextIsAvailable("Download Runtimes"));
		new DefaultShell("Download Runtimes");
		return new DownloadRuntimesWizard();
	}

	public int getRuntimesCount() {
		return new DefaultTable(0).rowCount();
	}

	public void removeAllRuntimes() {
		for (TableItem item : new DefaultTable(0).getItems()) {
			item.select();
			new PushButton("Remove").click();
		}
	}
}