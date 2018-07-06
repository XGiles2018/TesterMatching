package ApplauseInterviewQuestion.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ApplauseInterviewQuestion.pojo.Bug;
import ApplauseInterviewQuestion.pojo.Device;
import ApplauseInterviewQuestion.pojo.Tester;
import ApplauseInterviewQuestion.repositories.BugRepository;
import ApplauseInterviewQuestion.repositories.DeviceRepository;
import ApplauseInterviewQuestion.repositories.TesterRepository;

@Service
public class BugService {
	private Logger log = Logger.getLogger(BugService.class);
	
	@Autowired
	BugRepository bugRepo;
	
	@Autowired
	TesterRepository testerRepo;
	
	@Autowired
	DeviceRepository deviceRepo;
	
	public BugService() {
		log.info("Bug Service created");
	}
	
	public void populateBugData() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File deviceData = new File(classLoader.getResource("bugs.csv").getFile());
		
		String data = FileUtils.readFileToString(deviceData, "UTF-8");
		//Split on new line, then split on comma. Each entry is quoted as well so we remove quotes
		for(String row : data.split("\n")) {
			String[] rowEntries = row.split(",");
			
			//Skip header
			if(rowEntries[0].contains("bugId")) {
				continue; 
			}
			//Data is consistent, and as follows
			//id, deviceId, testerId
			Bug bug = new Bug();
			bug.setId(Long.parseLong(sanitizeString(rowEntries[0])));
			
			//Now set device and tester associations
			Device device = deviceRepo.getOne(Long.parseLong(sanitizeString(rowEntries[1])));
			bug.setDevice(device);
			
			Tester tester = testerRepo.getOne(Long.parseLong(sanitizeString(rowEntries[2])));
			bug.setTester(tester);
			
			bugRepo.saveAndFlush(bug);
		}
		log.info("Finished adding Bug data");
	}
	
	private String sanitizeString(String entry) {
		return entry.substring(1, entry.length() -1);
	}
	
	public List<Tester> getResultsForCriteria(List<Tester> testers, List<String> devices){
		//Could probably be solved with a "simple" query
		List<Tester> sortedTesterList = new ArrayList<>();
		for(Tester tester : testers) {
			List<Bug> bugList = new ArrayList<>();
			if(devices.contains("ALL")) {
				bugList = bugRepo.findAllByTester(tester);
			}else {
				for(String deviceDescription : devices) {
					Device device = deviceRepo.findByDescription(deviceDescription);
					bugList.addAll(bugRepo.getBugsForTesterAndDevice(tester, device));
				}
			}
			tester.setBugCount(bugList.size());
			sortedTesterList.add(tester);
		}
		//Sorting by experience (who filed most bugs)
		Collections.sort(sortedTesterList, new Comparator<Tester>() {

			@Override
			public int compare(Tester t1, Tester t2) {
				return t2.getBugCount() - t1.getBugCount();
			}
		});
		
		return sortedTesterList;
	}
}
