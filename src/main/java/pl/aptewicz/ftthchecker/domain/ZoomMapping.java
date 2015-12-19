package pl.aptewicz.ftthchecker.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ZoomMapping {

	@Id
	private Long id;

	private Long zoom;

	private Long nodeSize;

	public Long getZoom() {
		return zoom;
	}

	public void setZoom(Long zoom) {
		this.zoom = zoom;
	}

	public Long getNodeSize() {
		return nodeSize;
	}

	public void setNodeSize(Long nodeSize) {
		this.nodeSize = nodeSize;
	}
}
