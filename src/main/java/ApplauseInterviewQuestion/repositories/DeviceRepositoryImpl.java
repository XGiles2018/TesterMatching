package ApplauseInterviewQuestion.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class DeviceRepositoryImpl implements DeviceRepositoryCustom{

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDeviceDescriptions() {
		return em.createQuery("SELECT distinct d.description from Device d").getResultList();
	}
	
	
}
