package ApplauseInterviewQuestion.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ApplauseInterviewQuestion.pojo.Tester;
import ApplauseInterviewQuestion.repositories.TesterRepository;

@Service
public class TesterService {

	@Autowired
	TesterRepository testerRepo;
	
	Logger log = Logger.getLogger(TesterService.class);
	
	public List<String> getDistinctLocations(){
		return testerRepo.getDistinctLocations();
	}
	
	private String sanitizeString(String entry) {
		return entry.substring(1, entry.length() -1);
	}
	
	public void populateTesterData() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File testerData = new File(classLoader.getResource("testers.csv").getFile());
		
		String data = FileUtils.readFileToString(testerData, "UTF-8");
		//Split on new line, then split on comma. Each entry is quoted as well so we remove quotes
		for(String row : data.split("\n")) {
			String[] rowEntries = row.split(",");
			
			//Skip header
			if(rowEntries[0].contains("testerId")) {
				continue; 
			}
			//Data is consistent, and as follows
			//id, firstName, lastName, country, last login
			Tester tester = new Tester();
			tester.setId(Long.parseLong(sanitizeString(rowEntries[0])));
			tester.setFirstName(sanitizeString(rowEntries[1]));
			tester.setLastName(sanitizeString(rowEntries[2]));
			tester.setCountry(sanitizeString(rowEntries[3]));
			
			testerRepo.saveAndFlush(tester);
		}
		log.info("Finished adding tester data");
	}
	
	public void populateTesterDeviceData() throws IOException{
		ClassLoader classLoader = getClass().getClassLoader();
		File tdFile = new File(classLoader.getResource("tester_device.csv").getFile());
		
		String tdData = FileUtils.readFileToString(tdFile, "UTF-8");
		HashMap<Long, List<Long>> deviceAssociations = new HashMap<>();
		//Split on new line, then split on comma. Each entry is quoted as well so we remove quotes
		for(String row : tdData.split("\n")) {
			String[] rowEntries = row.split(",");
			if(rowEntries[0].contains("testerId")) {
				continue;
			}
			Long testerId = Long.parseLong(sanitizeString(rowEntries[0]));
			Long deviceId = Long.parseLong(sanitizeString(rowEntries[1]));
			//Look in map if we've already added the tester/
			//If so, add to list of Device IDs found for tester
			if(deviceAssociations.get(testerId) == null) {
				List<Long> deviceIds = new ArrayList<>();
				deviceIds.add(deviceId);
				deviceAssociations.put(testerId, deviceIds);
			}else {
				List<Long> deviceIds = deviceAssociations.get(testerId);
				deviceIds.add(deviceId);
				deviceAssociations.put(testerId, deviceIds);
			}
		}
		
		//Now we have associations, save to database
		for(Long testerId : deviceAssociations.keySet()) {
			List<Long> deviceIds = deviceAssociations.get(testerId);
			testerRepo.storeDevices(testerId, deviceIds);
		}
		log.info("Finished adding Device Association data");
	}
	
	public Tester getTesterByFirstName(String firstName) {
		return testerRepo.findByFirstName(firstName);
	}
	
	public List<Tester> getTestersByCountryList(List<String> countries){
		if(countries.contains("ALL")) {
			return getAllTesters();
		}
		
		List<Tester> testerList = new ArrayList<>();
		for(String country : countries) {
			testerList.addAll(testerRepo.findAllByCountry(country));
		}
		
		return testerList;
	}
	
	public List<Tester> getAllTesters(){
		return testerRepo.findAll();
	}
}
