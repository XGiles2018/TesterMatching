package ApplauseInterviewQuestion.repositories;

import java.util.List;

import ApplauseInterviewQuestion.pojo.Bug;
import ApplauseInterviewQuestion.pojo.Device;
import ApplauseInterviewQuestion.pojo.Tester;

public interface BugRepositoryCustom {
	List<Bug> getBugsForTesterAndDevice(Tester tester, Device device);
}
