package pl.aptewicz.ftthchecker.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Hierarchy {

	@Id
	@GeneratedValue
	private Long id;

	private String accessSite;

	private String distributionSite;

	private String centralSite;
}
