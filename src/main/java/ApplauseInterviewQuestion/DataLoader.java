package ApplauseInterviewQuestion;

import java.io.IOException;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import ApplauseInterviewQuestion.service.BugService;
import ApplauseInterviewQuestion.service.DeviceService;
import ApplauseInterviewQuestion.service.TesterService;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent>{
	@Autowired
	TesterService testerService;
	
	@Autowired
	BugService bugService;
	
	@Autowired
	DeviceService deviceService;
	
	private Logger log = Logger.getLogger(DataLoader.class);
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		log.info("Filling internal database with data!");
		
		try {
			testerService.populateTesterData();
			deviceService.populateDeviceData();
			testerService.populateTesterDeviceData();
			bugService.populateBugData();
		}catch(IOException e) {
			log.error("Unable to fill database", e);
		}
	}
}
