package org.jboss.tools.bpel.reddeer.activity;

import org.eclipse.gef.EditPart;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.AndMatcher;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.gef.impl.editpart.AbstractEditPart;
import org.eclipse.reddeer.swt.api.MenuItem;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.hamcrest.Matcher;
import org.jboss.tools.bpel.reddeer.editor.BpelEditor;
import org.jboss.tools.bpel.reddeer.view.BPELPropertiesView;

/**
 * 
 * @author apodhrad
 * 
 */
public class Activity extends AbstractEditPart {

	public static final String ASSIGN = "Assign";
	public static final String COMPENSATE = "Compensate";
	public static final String COMPENSATE_SCOPE = "CompensateScope";
	public static final String EMPTY = "Empty";
	public static final String EXIT = "Exit";
	public static final String FLOW = "Flow";
	public static final String FOR_EACH = "ForEach";
	public static final String IF = "If";
	public static final String INVOKE = "Invoke";
	public static final String PICK = "Pick";
	public static final String RECEIVE = "Receive";
	public static final String REPEAT_UNTIL = "RepeatUntil";
	public static final String REPLY = "Reply";
	public static final String RETHROW = "Rethrow";
	public static final String SCOPE = "Scope";
	public static final String SEQUENCE = "Sequence";
	public static final String THROW = "Throw";
	public static final String VALIDATE = "Validate";
	public static final String WAIT = "Wait";
	public static final String WHILE = "While";

	protected String name;
	protected String type;

	protected Logger log = Logger.getLogger(this.getClass());

	public Activity(String name, String type) {
		this(name, type, null, 0);
	}

	public Activity(String name, String type, Activity parent, int index) {
		super(getActivityMatcher(name, type, parent));
	}

	@SuppressWarnings("unchecked")
	private static Matcher<EditPart> getActivityMatcher(String name, String type, Activity parent) {
		Matcher<EditPart> matcher = new ActivityOfType(type);
		if (name != null) {
			matcher = new AndMatcher(matcher, new ActivityWithName(name));
		}
		if (parent != null) {
			matcher = new AndMatcher(matcher, new ActivityWithParent(parent));
		}
		return matcher;
	}

	public void delete() {
		menu("Delete");
	}

	@Override
	public void select() {
		new BpelEditor().activate();
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				getEditPart().getViewer().getControl().setFocus();
			}
		});
		super.select();
	}

	public void add(String type) {
		menu("Add", type);
	}

	public void add(String type, String name) {
		log.info("Add activity '" + name + "' of type <" + type + ">");
		add(type);
		if (name != null) {
			// we expect that each activity has its default name as its type
			new Activity(type, type, this, 0).setName(name);
		}
	}

	public void insertBefore(String type) {
		menu("Insert Before", type);
	}

	public void insertBefore(String type, String name) {
		log.info("Insert before activity '" + name + "' of type <" + type + ">");
		insertBefore(type);
		if (name != null) {
			// we expect that each activity has its default name as its type
			new Activity(type, type, this, 0).setName(name);
		}
	}

	public void setName(String name) {
		openProperties().setName(name);
		save();
	}

	protected void menu(String... menu) {
		select();
		new ContextMenuItem(menu).select();
	}

	public boolean validate(String text) {
		throw new UnsupportedOperationException();
	}

	protected EditPart getEditPart() {
		return editPart;
	}

	public BPELPropertiesView openProperties() {
		select();
		return new BPELPropertiesView();
	}

	protected void save() {
		MenuItem saveMenu = new ShellMenuItem(new WorkbenchShell(), "File", "Save");
		if (saveMenu.isEnabled()) {
			saveMenu.select();
		}
	}

}
