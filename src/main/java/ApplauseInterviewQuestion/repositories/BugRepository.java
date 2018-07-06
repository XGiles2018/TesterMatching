package ApplauseInterviewQuestion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ApplauseInterviewQuestion.pojo.Bug;
import ApplauseInterviewQuestion.pojo.Tester;

public interface BugRepository extends JpaRepository<Bug, Long>, BugRepositoryCustom{
	public List<Bug> findAllByTester(Tester tester);
}
