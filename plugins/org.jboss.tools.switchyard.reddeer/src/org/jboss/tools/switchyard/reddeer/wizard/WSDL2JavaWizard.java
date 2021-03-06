package org.jboss.tools.switchyard.reddeer.wizard;

import org.eclipse.reddeer.common.wait.AbstractWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.api.Text;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Wizard for creating WSDL from Java.
 * 
 * @author apodhrad
 * 
 */
public class WSDL2JavaWizard extends NewMenuWizard {

	public static final String DIALOG_TITLE = "Java2WSDL";

	public WSDL2JavaWizard() {
		super(DIALOG_TITLE, "SwitchYard", "Java Files from WSDL");
	}

	public WSDL2JavaWizard activate() {
		new DefaultShell(DIALOG_TITLE);
		AbstractWait.sleep(TimePeriod.SHORT);
		return this;
	}

	public WSDL2JavaWizard openDialog() {
		open();
		return this;
	}

	public WSDL2JavaWizard nextDialog() {
		next();
		return this;
	}

	public Text getWSDL2JavaOptionsTXT() {
		return new LabeledText("WSDL2Java Options");
	}

	public CheckBox getOverwriteExistingFilesCHB() {
		return new CheckBox("Overwrite existing files");
	}

	public CheckBox getGenerateParameterAndResultTypesCHB() {
		return new CheckBox("Generate parameter and result types");
	}

	public CheckBox getCreateWrapperForMessagePartsCHB() {
		return new CheckBox("Create wrapper for message parts");
	}

	public Button getBrowseBTN() {
		return new PushButton("Browse...");
	}

	public Text getWSDLFileTXT() {
		return new LabeledText("WSDL File:");
	}

	public Text getPackageTXT() {
		return new LabeledText("Package:");
	}

	public Text getSourcefolderTXT() {
		return new LabeledText("Source folder:");
	}

	public CheckBox getShowAllWizardsCHB() {
		return new CheckBox("Show All Wizards.");
	}

}
