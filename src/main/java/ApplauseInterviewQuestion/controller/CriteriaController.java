package ApplauseInterviewQuestion.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ApplauseInterviewQuestion.pojo.Tester;
import ApplauseInterviewQuestion.service.BugService;
import ApplauseInterviewQuestion.service.DeviceService;
import ApplauseInterviewQuestion.service.TesterService;

@Controller
public class CriteriaController {

	@Autowired
	TesterService testerService;

	@Autowired
	DeviceService deviceService;

	@Autowired
	BugService bugService;

	@RequestMapping("/")
	public String index(Model model) {
		List<String> devices = deviceService.getDeviceDescriptions();
		List<String> countries = testerService.getDistinctLocations();

		devices.add("ALL");
		countries.add("ALL");

		Collections.sort(devices);
		Collections.sort(countries);

		model.addAttribute("devices", devices);
		model.addAttribute("countries", countries);

		return "index";
	}

	@RequestMapping("/getCriteriaResults.html")
	public void getCriteriaResults(HttpServletResponse resp, @RequestParam("devices") List<String> devices,
			@RequestParam("countries") List<String> countries) throws IOException {

		// Each string is sent enclosed in quotes, convert list to 
		//a new list with just device/location
		List<String> convertedDeviceList = sanitizeStringsInList(devices);
		List<String> convertedCountryList = sanitizeStringsInList(countries);
		List<Tester> testerList = new ArrayList<>();
		
		testerList = testerService.getTestersByCountryList(convertedCountryList);
		
		List<Tester> results = bugService.getResultsForCriteria(testerList, convertedDeviceList);
		
		
		resp.getOutputStream().print(printResultsAsString(results));
	}
	
	private String printResultsAsString(List<Tester> testerList) {
		StringBuilder responseString = new StringBuilder("");
		for(Tester tester : testerList) {
			String str = tester.toString() + " has filed " + tester.getBugCount() 
			+ " bugs for the specified devices! \n \n";
			responseString.append(str);
		}
		
		return responseString.toString();
	}

	private List<String> sanitizeStringsInList(List<String> stringList) {
		List<String> sanitizedStrings = new ArrayList<>();
		for (String str : stringList) {
			int beginIndex = str.indexOf("\"") + 1;
			int endIndex = str.lastIndexOf("\"");
			sanitizedStrings.add(str.substring(beginIndex, endIndex));
		}

		return sanitizedStrings;
	}
}
