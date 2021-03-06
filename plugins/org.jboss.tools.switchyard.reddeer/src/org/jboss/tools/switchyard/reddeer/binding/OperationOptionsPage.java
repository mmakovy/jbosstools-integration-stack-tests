package org.jboss.tools.switchyard.reddeer.binding;

import org.eclipse.reddeer.common.wait.AbstractWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.swt.api.Combo;
import org.eclipse.reddeer.swt.api.Text;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.group.DefaultGroup;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.tab.DefaultTabItem;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.jboss.tools.switchyard.reddeer.widget.LabeledText;

public abstract class OperationOptionsPage<T> extends WizardDialog {

	public static final String NAME = "Name";
	public static final String OPERATION_NAME = "Operation Name";
	public static final String OPERATION_SELECTOR = "Operation Selector";
	public static final String XPATH = "XPath";
	public static final String REGEX = "Regex";
	public static final String JAVA_CLASS = "Java Class";

	@SuppressWarnings("unchecked")
	public T setName(String name) {
		new LabeledText(NAME).setFocus();
		// a workaround to activate typing listener
		new LabeledText(NAME).typeText("aa");
		new LabeledText(NAME).setText(name);
		new LabeledText(NAME).setFocusOut();
		return (T) this;
	}

	public String getName() {
		return new LabeledText(NAME).getText();
	}

	@SuppressWarnings("unchecked")
	public T setOperationSelector(String selector, String value) {
		setOperationSelector(selector);
		setOperationValue(value);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T setOperationSelector(String selector) {
		new DefaultCombo(new DefaultGroup(OPERATION_SELECTOR), 0).setSelection(selector);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T setOperationValue(String value) {
		String selector = getOperationSelector();
		if (OPERATION_NAME.equals(selector)) {
			new DefaultCombo(new DefaultGroup(OPERATION_SELECTOR), 1).setSelection(value);
		} else {
			new DefaultText(new DefaultGroup(OPERATION_SELECTOR), 0).setText(value);
		}
		return (T) this;
	}

	public String getOperationSelector() {
		DefaultGroup group = new DefaultGroup(OPERATION_SELECTOR);
		return new DefaultCombo(group).getText();
	}

	public String getOperationSelectorValue() {
		DefaultGroup group = new DefaultGroup(OPERATION_SELECTOR);
		return new DefaultCombo(group, 1).getText();
	}

	@Override
	public void finish() {
		AbstractWait.sleep(TimePeriod.SHORT);
		if (getShell() == null) {
			setShell(new DefaultShell(""));
		}
		super.finish();
	}

	public void ok() {
		AbstractWait.sleep(TimePeriod.SHORT);

		String shellText = new DefaultShell().getText();
		new PushButton("OK").click();

		new WaitWhile(new ShellIsAvailable(shellText), TimePeriod.LONG);
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}

	/* Only on properties */

	public void selectAuthenticationDetails() {
		new DefaultTabItem("Authentication Details").activate();
	}

	public void selectProxySettings() {
		new DefaultTabItem("Proxy settings").activate();
	}

	/* Only on reference binding */

	public Combo getAuthenticationType() {
		return new LabeledCombo("Authentication Type");
	}

	public Text getDomain() {
		return new LabeledText("Domain");
	}

	public Text getPort() {
		return new LabeledText("Port");
	}

	public Text getHost() {
		return new LabeledText("Host");
	}

	public Text getRealm() {
		return new LabeledText("Realm");
	}

	public Text getPassword() {
		return new LabeledText("Password");
	}

	public Text getUser() {
		return new LabeledText("User");
	}

	public Text getUserName() {
		return new LabeledText("User Name");
	}
}
