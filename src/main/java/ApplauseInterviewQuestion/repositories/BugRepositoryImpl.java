package ApplauseInterviewQuestion.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ApplauseInterviewQuestion.pojo.Bug;
import ApplauseInterviewQuestion.pojo.Device;
import ApplauseInterviewQuestion.pojo.Tester;

@Repository
public class BugRepositoryImpl implements BugRepositoryCustom{

	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<Bug> getBugsForTesterAndDevice(Tester tester, Device device) {
		Query q = em.createQuery("FROM Bug b where b.tester = :tester AND b.device= :device");
		q.setParameter("tester", tester);
		q.setParameter("device", device);
		
		return q.getResultList();
	}

}
