package ApplauseInterviewQuestion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ApplauseInterviewQuestion.pojo.Device;
import ApplauseInterviewQuestion.pojo.Tester;

public interface TesterRepository extends JpaRepository<Tester, Long>, TesterRepositoryCustom{
	public List<String> getDistinctLocations();

	public void storeDevices(Long testerId, List<Long> devices);
	
	public Tester findByFirstName(String name);
	
	public List<Tester> findAllByCountry(String country);
}
