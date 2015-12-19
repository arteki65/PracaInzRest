package pl.aptewicz.ftthchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.aptewicz.ftthchecker.domain.ZoomMapping;

public interface ZoomMappingRepository
		extends
			JpaRepository<ZoomMapping, Long> {

	public ZoomMapping findByZoom(Long zoom);
}
