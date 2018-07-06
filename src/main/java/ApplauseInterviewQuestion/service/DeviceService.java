package ApplauseInterviewQuestion.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ApplauseInterviewQuestion.pojo.Device;
import ApplauseInterviewQuestion.repositories.DeviceRepository;

@Service
public class DeviceService {

	@Autowired
	DeviceRepository deviceRepo;
	
	private Logger log = Logger.getLogger(DeviceService.class);
	
	public List<String> getDeviceDescriptions(){
		return deviceRepo.getDeviceDescriptions();
	}
	
	private String sanitizeString(String entry) {
		return entry.substring(1, entry.length() -1);
	}
	
	public void populateDeviceData() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File deviceData = new File(classLoader.getResource("devices.csv").getFile());
		
		String data = FileUtils.readFileToString(deviceData, "UTF-8");
		//Split on new line, then split on comma. Each entry is quoted as well so we remove quotes
		for(String row : data.split("\n")) {
			String[] rowEntries = row.split(",");
			
			//Skip header
			if(rowEntries[0].contains("deviceId")) {
				continue; 
			}
			//Data is consistent, and as follows
			//id, description
			Device device = new Device();
			device.setId(Long.parseLong(sanitizeString(rowEntries[0])));
			device.setDescription(sanitizeString(rowEntries[1]));
			
			deviceRepo.saveAndFlush(device);
		}
		log.info("Device Data loaded");
	}
}
