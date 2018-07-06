package ApplauseInterviewQuestion.repositories;

import java.util.List;

import ApplauseInterviewQuestion.pojo.Device;
import ApplauseInterviewQuestion.pojo.Tester;

public interface TesterRepositoryCustom {
	List<String> getDistinctLocations();
	
	public void storeDevices(Long testerId, List<Long> devices);
}
