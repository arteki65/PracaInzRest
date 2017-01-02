package pl.aptewicz.ftthchecker.repository;

import pl.aptewicz.ftthchecker.domain.FtthCheckerUser;

import java.util.List;

public interface FtthCheckerUserInAreaRepository {

	List<FtthCheckerUser> findUsersInArea(double x1, double y1, double x2, double y2);
}
