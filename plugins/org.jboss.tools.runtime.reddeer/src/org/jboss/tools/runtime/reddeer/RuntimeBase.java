package org.jboss.tools.runtime.reddeer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.eclipse.wst.server.core.IRuntime;
import org.eclipse.wst.server.core.ServerCore;
import org.jboss.tools.jbpm.preferences.PreferencesManager;

/**
 * 
 * @author apodhrad
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class RuntimeBase {

	protected String name;

	@XmlAttribute(name = "version")
	private String version;

	@XmlElement(name = "home", namespace = Namespaces.SOA_REQ)
	private String home;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public boolean exists() {
		IRuntime[] runtime = ServerCore.getRuntimes();
		for (int i = 0; i < runtime.length; i++) {
			if (runtime[i].getId().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public abstract void create();
}