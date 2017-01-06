package pl.aptewicz.ftthchecker.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Node {

	@Id
	private Long name;

	private Double x;

	private Double y;

}
