package ApplauseInterviewQuestion.repositories;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ApplauseInterviewQuestion.pojo.Device;
import ApplauseInterviewQuestion.pojo.Tester;

@EnableTransactionManagement
@Transactional
@Repository
public class TesterRepositoryImpl implements TesterRepositoryCustom{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<String> getDistinctLocations() {
		return em.createQuery("SELECT distinct t.country from Tester t").getResultList();
	}

	@Override
	public void storeDevices(Long testerId, List<Long> deviceIds) {
		Tester tester = em.find(Tester.class, testerId);
		Set<Device> deviceList = new HashSet<>();
		for(Long id : deviceIds) {
			Device device = em.find(Device.class, id);
			deviceList.add(device);
		}
		
		tester.setDevices(deviceList);
		em.persist(tester);
		em.flush();
	}
	
	

}
