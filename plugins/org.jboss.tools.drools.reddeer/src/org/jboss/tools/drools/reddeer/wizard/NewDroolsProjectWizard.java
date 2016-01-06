package org.jboss.tools.drools.reddeer.wizard;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;
import org.jboss.tools.drools.reddeer.wizard.NewDroolsProjectSelectRuntimeWizardPage.CodeCompatibility;

public class NewDroolsProjectWizard extends NewWizardDialog {

	public NewDroolsProjectWizard() {
		super("Drools", "Drools Project");
	}

	public NewDroolsProjectNameWizardPage getFirstPage() {
		return new NewDroolsProjectNameWizardPage();
	}

	public NewDroolsProjectSelectSamplesWizardPage getSelectSamplesPage() {
		return new NewDroolsProjectSelectSamplesWizardPage();
	}

	public NewDroolsProjectSelectRuntimeWizardPage getDroolsRuntimePage() {
		return new NewDroolsProjectSelectRuntimeWizardPage();
	}

	/**
	 * Creates default Project with given name. All samples are installed, the runtime is set to the workspace default
	 * and code is set to be compatible with Drools 5.1 or above.
	 * 
	 * @param projectName
	 *            name of the new project
	 */
	public void createDefaultProjectWithAllSamples(String projectName) {
		open();
		getFirstPage().setProjectName(projectName);
		next();
		getSelectSamplesPage().checkAll();
		next();
		NewDroolsProjectSelectRuntimeWizardPage runtime = getDroolsRuntimePage();
		runtime.setUseDefaultRuntime(true);
		runtime.setCodeCompatibleWithVersion(CodeCompatibility.Drools60x);
		runtime.setGAV("com.redhat", "brms-test", "1.0-SNAPSHOT");
		finish();
	}
}
