package pl.aptewicz.ftthchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FtthCustomerDto {

	private Long id;

	private String username;

	private String password;

	private Collection<FtthIssueDto> ftthDtoIssues;
}
