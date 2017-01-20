package org.jboss.tools.runtime.reddeer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Openshift {

	@XmlElement(namespace = Namespaces.SOA_REQ)
	private boolean isExternallyManaged;

	@XmlElement(namespace = Namespaces.SOA_REQ)
	private boolean useManagementOperations;

	public boolean isExternallyManaged() {
		return isExternallyManaged;
	}

	public void setExternallyManaged(boolean isExternallyManaged) {
		this.isExternallyManaged = isExternallyManaged;
	}

	public boolean isUseManagementOperations() {
		return useManagementOperations;
	}

	public void setUseManagementOperations(boolean useManagementOperations) {
		this.useManagementOperations = useManagementOperations;
	}

}
