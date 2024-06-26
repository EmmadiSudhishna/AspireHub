package com.example.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import com.example.entity.Company;
import com.example.entity.JobApply;
import com.example.entity.JobSeekers;
import com.example.entity.Vacancies;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class CompanyController {
	
	@GetMapping("/company")
	public ModelAndView comapanyLogin(Model model)
	{
		Company company = new Company();
		
		model.addAttribute("company", company);
		
		ModelAndView view = new ModelAndView("company");
		return view;
		
	}
	
	@GetMapping("/companyLogin")
    public ModelAndView companyLogin(Model model) {
        Company company = new Company();
        company.setEmail("example@gmail.com");
		company.setPassword("123");
        model.addAttribute("company", company);
        return new ModelAndView("Company/companyLogin");
    }
	
	@GetMapping("/companyhome")
    public String companyHome() {
		ModelAndView view = new ModelAndView("Company/companyhome");
        return "Company/companyhome"; // Assuming "adminhome" is the name of your Thymeleaf template
    }
	/*@GetMapping(value = "/companyhome")
	public ModelAndView companyHome() {
		ModelAndView view = new ModelAndView("Company/companyhome");
		return view;
	}*/
	
	
	@PostMapping("/ValidateCompanyLogin")
	public ModelAndView validateComapnyLogin(@ModelAttribute("company") Company company, Model model, HttpSession session) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    HttpEntity<Company> entity = new HttpEntity<Company>(company, headers);
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Company> response = restTemplate.exchange("http://localhost:8091/AspireHub/api/v1/companyLogin",
	            HttpMethod.POST, entity, Company.class);

	    Company responseFlag = response.getBody();

	    if (responseFlag!=null) {
	        // Put company ID in session
	        session.setAttribute("company", responseFlag);

	        ModelAndView view = new ModelAndView("Company/companyhome");
	        return view;
	    } else {
	        model.addAttribute("responseText", "Email/Password incorrect...");

	        ModelAndView view = new ModelAndView("Company/companyLogin");
	        return view;
	    }
	}
	
	
	
	@GetMapping("/newRegistrationCompany")
    public ModelAndView newRegisterCompany(Model model) {
        Company company = new Company();
        model.addAttribute("company", company);
        return new ModelAndView("newRegistrationCompany");
    }

 /*@PostMapping("/newRegistrationCompany")
 public ModelAndView addCompany(@ModelAttribute("company") Company company, Model model) {
     HttpHeaders headers = new HttpHeaders();
     headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
     HttpEntity<Company> entity = new HttpEntity<>(company, headers);
     RestTemplate restTemplate = new RestTemplate();
     String url = "http://localhost:8090/api/v1/addCompany";
     ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
     String responseString = responseEntity.getBody();
     
     model.addAttribute("responseString", responseString);
     return new ModelAndView("newRegistrationCompany");
 }*/
	@PostMapping("/newRegistrationCompany")
	public ModelAndView addCompany(@ModelAttribute("company") Company company,@RequestParam("logo1") MultipartFile logo1, Model model) 
	{
		System.out.println("In the add company method");
		
		try {
			// Create the upload directory if not exists
	        Files.createDirectories(Path.of("./temp_uploads"));
			
	        // Handle file upload for logo
			byte[] logoBytes =logo1.getBytes();
			String logoName = "temp_" + System.currentTimeMillis() + "_" + logo1.getOriginalFilename();
			Files.write(Paths.get("./temp_uploads", logoName), logoBytes);
			
			// Set the logo name in the company object
			company.setLogo(logoName);
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Company> entity = new HttpEntity<>(company, headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange("http://localhost:8091/AspireHub/api/v1/addCompany", HttpMethod.POST,
					entity, String.class);
			String responseMsg = response.getBody();
			System.out.println("REsponse object="+responseMsg);
			
			////////////////////////code to upload product picture
			HttpHeaders picHeaders = new HttpHeaders();
			picHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, Object> picMap = new LinkedMultiValueMap<>();
			picMap.add("file", new FileSystemResource("./temp_uploads/" + logoName));
			picMap.add("filename", logoName);
			HttpEntity<MultiValueMap<String, Object>> picEntity = new HttpEntity<>(picMap, picHeaders);
			
			// Send a request to upload the product picture
			String uploadPicUrl = "http://localhost:8091/AspireHub/api/files/upload";
			ResponseEntity<String> picResponse = restTemplate.exchange(uploadPicUrl, HttpMethod.POST, picEntity,
					String.class);
			
			String picResponseString = picResponse.getBody();
			System.out.println("responseMsg=" + responseMsg);
			
			if (response.getStatusCode().is2xxSuccessful()) 
			{
				System.out.println("if invoked");
				ModelAndView view = new ModelAndView("Company/companyLogin");
				view.addObject("responseMsg", responseMsg);
				return view;
			}
			else
			{
				System.out.println("else invoked");
				ModelAndView view = new ModelAndView("newRegistrationCompany");
				model.addAttribute("responseText", responseMsg);
				return view;
			}
		}
		catch (Exception e)
		{
			System.out.println("Error:"+ e);
			// Handle exceptions related to file operations or HTTP requests
			ModelAndView errorView = new ModelAndView("newRegistrationCompany");
			model.addAttribute("responseText", e.getMessage());
			return errorView;
		}
	}

 
 
 @GetMapping("/companyProfile")
 public ModelAndView showCompanyProfile(Model model,HttpSession session) {
	 Company company = (Company) session.getAttribute("company");
	 int cid = company.getCompanyId();
	 RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Company> responseEntity = restTemplate
				.getForEntity("http://localhost:8091/AspireHub/api/v1/getCompany/" + cid, Company.class);
		Company company1 = responseEntity.getBody();
		System.out.println("company1" + company1.getLogo());
		String imageUrl = company1.getLogo();
		company1.setLogo(imageUrl);
		System.out.println("company1 = " + company1.getLogo());
		model.addAttribute("company1", company1);
		
		ModelAndView view = new ModelAndView("Company/companyProfile");
     return view;
 }
 
 
 @GetMapping("/viewVacancy")
	public String viewAllVacancies(Model model, HttpSession session) {
 	Company company = (Company) session.getAttribute("company");
 	
	    int companyId = company.getCompanyId();
	    System.out.println("companyId"+companyId);
	    // Fetch vacancies associated with the companyId from the backend
	    RestTemplate restTemplate = new RestTemplate();
	    String url = "http://localhost:8091/AspireHub/api/v1/getVacanciesByCompanyId/" + companyId ;
	    Vacancies[] vacanciesArray = restTemplate.getForObject(url, Vacancies[].class);
	    List<Vacancies> vacancies = Arrays.asList(vacanciesArray);
	    // Add vacancies to the model
	    model.addAttribute("vacancies", vacancies);
	    // Return the viewVacancy.html file
	    return "Company/viewVacancy";
	}

	
	@GetMapping("/updateCompany")
	public ModelAndView updateCompanyForm(@RequestParam("companyId") Long companyId) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Company> response = restTemplate
				.getForEntity("http://localhost:8091/AspireHub/api/v1/getCompany/" + companyId, Company.class);
		Company company = response.getBody();
		ModelAndView view = new ModelAndView("updateCompanyForm");
		view.addObject("company", company);
		return view;
	}
	
	
	@PostMapping("/updateCompany")
	public ModelAndView updateCompany(@ModelAttribute("company") Company company, Model model) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Company> entity = new HttpEntity<>(company, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put("http://localhost:8091/AspireHub/api/v1/updateCompany/" + company.getCompanyId(), entity);
		ModelAndView view = new ModelAndView("companySuccess");
		view.addObject("responseMsg", "Company updated successfully");
		return view;
	}
	
	@GetMapping("/deleteCompany")
	public ModelAndView deleteCompany(@RequestParam("companyId") Integer companyId) {
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.delete("http://localhost:8091/AspireHub/api/v1/deleteCompany/" + companyId);
	    ModelAndView view = new ModelAndView("companySuccess");
	    view.addObject("responseMsg", "Company deleted successfully");
	    return view;
	}

	
	@GetMapping("/allCompanies")
	public ModelAndView getAllCompanies() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Company[]> response = restTemplate.getForEntity("http://localhost:8091/AspireHub/api/v1/allCompanies",
				Company[].class);
		List<Company> companies = Arrays.asList(response.getBody());
		ModelAndView view = new ModelAndView("allCompanies");
		view.addObject("companies", companies);
		return view;
	}
	
	@GetMapping("/editCompanyProfile/{companyId}")
	public ModelAndView editCompanyProfile(@PathVariable("companyId") int companyId, Model model, HttpSession session) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Company> responseEntity = restTemplate
				.getForEntity("http://localhost:8091/AspireHub/api/v1/getCompany/" + companyId, Company.class);
		Company company = responseEntity.getBody();
		System.out.println("Company Obj in frontend=" + company);
		model.addAttribute("company", company);

		ModelAndView view = new ModelAndView("Company/editCompanyProfile");

		return view;
	}


	@PostMapping("/updatingCompanyProfile")
	public String updatingCompany(@ModelAttribute("company") Company company, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Company> entity = new HttpEntity<>(company, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put("http://localhost:8091/AspireHub/api/v1/updateCompany/{companyId}", entity, company.getCompanyId());

		ResponseEntity<Company> responseEntity = restTemplate
				.getForEntity("http://localhost:8091/AspireHub/api/v1/getCompany/" + company.getCompanyId(), Company.class);
		Company company2 = responseEntity.getBody();
		session.setAttribute("company", company2);

		return "redirect:/companyProfile";
	}

	
	
	@GetMapping("/editCompanyLogo")
	public ModelAndView editCompanyLogo(Model model) {
		Company company = new Company();

		model.addAttribute("company", company);
		ModelAndView view = new ModelAndView("Company/editCompanyLogo");

		return view;
	}
	@PostMapping("/uploadLogo")
	public String uploadLogo(@RequestParam("logo1") MultipartFile logo1, Model model, HttpSession session) {

		try {
			Company company = (Company) session.getAttribute("company");

			int companyId = company.getCompanyId();

			// Create the upload directory if not exists
			Files.createDirectories(Path.of("./temp_uploads"));

			byte[] picBytes = logo1.getBytes();
			String jobseekerPicName = "temp_" + System.currentTimeMillis() + "_" + logo1.getOriginalFilename();
			Files.write(Paths.get("./temp_uploads", jobseekerPicName), picBytes);

			// Set the product picture name in the product object
			company.setLogo(jobseekerPicName);

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Company> entity = new HttpEntity<Company>(company, headers);
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8091/AspireHub/api/v1/addCompany";
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			String responseString = response.getBody();
			// System.out.println("responseFlag=" + responseFlag);

			// Up to here product is adding

			//////////////////////// code to upload product picture
			HttpHeaders picHeaders = new HttpHeaders();
			picHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, Object> picMap = new LinkedMultiValueMap<>();
			picMap.add("file", new FileSystemResource("./temp_uploads/" + jobseekerPicName));
			picMap.add("filename", jobseekerPicName);
			HttpEntity<MultiValueMap<String, Object>> picEntity = new HttpEntity<>(picMap, picHeaders);

			// Send a request to upload the product picture
			String uploadPicUrl = "http://localhost:8091/AspireHub/api/files/upload";
			ResponseEntity<String> picResponse = restTemplate.exchange(uploadPicUrl, HttpMethod.POST, picEntity,
					String.class);

			String picResponseString = picResponse.getBody();

			// Clean up: Delete the temporary product picture
			Files.deleteIfExists(Paths.get("./temp_uploads", jobseekerPicName));

			///////////////////////
			/*
			 * ModelAndView view = new ModelAndView("/getProfile");
			 * model.addAttribute("responseString", responseString);
			 */

			ResponseEntity<Company> responseEntity = restTemplate
					.getForEntity("http://localhost:8091/AspireHub/api/v1/getCompany/" + company.getCompanyId(), Company.class);
			 company = responseEntity.getBody();
			session.setAttribute("company", company);

			return "redirect:/companyProfile";
		} catch (Exception e) {

			model.addAttribute("responseString", e.getMessage());
			// Redirect to some error page or handle it accordingly
			return "errorPage";
		}

	}
    
    @GetMapping("/viewCompanyJobApply")
	public ModelAndView viewJobApplyByCompanyId(Model model, HttpSession session)
	{

		RestTemplate restTemplate = new RestTemplate();
		int companyId = ((Company) session.getAttribute("company")).getCompanyId();
		System.out.println("CompanyId="+companyId);

		ResponseEntity<JobApply[]> responseEntity = restTemplate
				.getForEntity("http://localhost:8091/AspireHub/api/v1/findJobApplyByCompanyId/" + companyId, JobApply[].class);
		
		JobApply[] responseBody = responseEntity.getBody();
		List<JobApply> JobApplyList = Arrays.asList(responseBody);
		System.out.println("Job Apply List in company="+JobApplyList);
		model.addAttribute("JobApplyList", JobApplyList);
		ModelAndView view = new ModelAndView("Company/viewCompanyJobApply");
		return view;

	}
	
	

	
 
}