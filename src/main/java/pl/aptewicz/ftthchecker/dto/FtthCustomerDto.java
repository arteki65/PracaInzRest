package pl.aptewicz.ftthchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aptewicz.ftthchecker.domain.FtthCustomer;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FtthCustomerDto {

	private Long id;

	private String username;

	private Collection<FtthIssueDto> ftthDtoIssues;

	public FtthCustomerDto(FtthCustomer ftthCustomer) {
		id = ftthCustomer.getId();
		username = ftthCustomer.getUsername();
		ftthDtoIssues = new ArrayList<>();
		ftthCustomer.getFtthIssues().forEach(ftthIssue -> ftthDtoIssues.add(new FtthIssueDto(ftthIssue)));
	}
}
