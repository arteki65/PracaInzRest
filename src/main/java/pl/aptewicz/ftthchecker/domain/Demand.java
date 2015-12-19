package pl.aptewicz.ftthchecker.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Demand {

	@Id
	private Long name;

	@OneToOne
	private Node node;

	private Long fiberCount;

	private Long signalCount;

	private Long requiredPower;

	public Long getName() {
		return name;
	}

	public void setName(Long name) {
		this.name = name;
	}

	public Long getFiberCount() {
		return fiberCount;
	}

	public void setFiberCount(Long fiberCount) {
		this.fiberCount = fiberCount;
	}

	public Long getSignalCount() {
		return signalCount;
	}

	public void setSignalCount(Long signalCount) {
		this.signalCount = signalCount;
	}

	public Long getRequiredPower() {
		return requiredPower;
	}

	public void setRequiredPower(Long requiredPower) {
		this.requiredPower = requiredPower;
	}
}
