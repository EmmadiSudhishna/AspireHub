package com.example.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.entity.Admin;
import com.example.entity.Company;
import com.example.entity.JobApply;
import com.example.entity.JobSeekers;
import com.example.entity.Questions;
import com.example.entity.TestResult;
import com.example.entity.User;
import com.example.entity.Vacancies;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller

public class JobSeekersController {
	

	@GetMapping("/jobseekersLogin")
	public ModelAndView jobseekersLogin(Model model) {
	    JobSeekers jobseekers = new JobSeekers();
	    model.addAttribute("jobSeekers", jobseekers); // Note the change in attribute name to "jobSeekers"
	    return new ModelAndView("User/jobseekersLogin");
	}



    @GetMapping("/jobSeekershome")
    public String jobSeekersHome() {
        return "User/jobSeekershome"; // Assuming "adminhome" is the name of your Thymeleaf template
    }

    @PostMapping("/ValidateJobseekersLogin")
    public ModelAndView validateJobseekersLogin(@ModelAttribute("jobSeekers") JobSeekers jobSeekers, Model model, HttpSession session) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        
        HttpEntity<JobSeekers> entity = new HttpEntity<>(jobSeekers, headers);
        
        RestTemplate restTemplate = new RestTemplate();
        
        
       
        
        ResponseEntity<JobSeekers> response = restTemplate.exchange("http://localhost:8091/AspireHub/api/v1/jobseekersLogin",
                HttpMethod.POST, entity, JobSeekers.class);
        
        JobSeekers responseFlag = response.getBody();
        System.out.println("responseFlag= " + responseFlag);
        
        if (responseFlag!=null) {
        	
        	//put jobseekers id in session
        	session.setAttribute("jobseeker", responseFlag);
            ModelAndView view = new ModelAndView("User/jobSeekershome");
            return view;
        } else {
        	 //model.addAttribute("jobSeekers", new JobSeekers());
            model.addAttribute("responseText", "Email/Password incorrect...");

            ModelAndView view = new ModelAndView("User/jobseekersLogin");
            return view;
        }
    }
    
    

    @GetMapping("/newUserRegistration")
    public ModelAndView newUserRegister(Model model) {
        JobSeekers jobSeeker = new JobSeekers();
        model.addAttribute("jobSeeker", jobSeeker);
        return new ModelAndView("newUserRegistration");
    }

    /*@PostMapping("/newUserRegistration")
    public ModelAndView addJobSeekers(@ModelAttribute("jobSeeker") JobSeekers jobSeeker, Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<JobSeekers> entity = new HttpEntity<>(jobSeeker, headers);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8090/api/v1/addJobSeekers"; // Update the URL according to your backend endpoint
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        String responseString = responseEntity.getBody();
        
        model.addAttribute("responseString", responseString);
        return new ModelAndView("newUserRegistration");
    }*/
   
    
    
    @PostMapping("/newUserRegistration")
	public ModelAndView addJobseeker(@ModelAttribute("jobSeekers") JobSeekers jobseeker,@RequestParam("profilePic1") MultipartFile profilePic1, Model model) 
	{
		System.out.println("In the add jobseeker method");
		
		try {
			// Create the upload directory if not exists
	        Files.createDirectories(Path.of("./temp_uploads"));
			
	        // Handle file upload for logo
			byte[] profilePicBytes = profilePic1.getBytes();
			String profilePicName = "temp_" + System.currentTimeMillis() + "_" + profilePic1.getOriginalFilename();
			Files.write(Paths.get("./temp_uploads", profilePicName), profilePicBytes);
			
			// Set the logo name in the company object
			jobseeker.setProfilepic(profilePicName);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<JobSeekers> entity = new HttpEntity<>(jobseeker, headers);
			
			ResponseEntity<String> response = restTemplate.exchange("http://localhost:8091/AspireHub/api/v1/addJobSeekers", HttpMethod.POST,
					entity, String.class);
			String responseMsg = response.getBody();
			System.out.println("REsponse object="+responseMsg);
			
			////////////////////////code to upload product picture
			HttpHeaders picHeaders = new HttpHeaders();
			picHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, Object> picMap = new LinkedMultiValueMap<>();
			picMap.add("file", new FileSystemResource("./temp_uploads/" + profilePicName));
			picMap.add("filename", profilePicName);
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
				ModelAndView view = new ModelAndView("User/jobseekersLogin");
				view.addObject("responseMsg", responseMsg);
				return view;
			}
			else
			{
				System.out.println("else invoked");
				ModelAndView view = new ModelAndView("newUserRegistration");
				model.addAttribute("responseText", responseMsg);
				return view;
			}
		}
		catch (Exception e)
		{
			System.out.println("Error:"+ e);
			// Handle exceptions related to file operations or HTTP requests
			ModelAndView errorView = new ModelAndView("newUserRegistration");
			model.addAttribute("responseText", e.getMessage());
			return errorView;
		}
	}
    
    
    @GetMapping("/jobseekerProfile")
    public ModelAndView showJobseekerProfile(Model model, HttpSession session) {
        JobSeekers jobseeker = (JobSeekers) session.getAttribute("jobseeker");
        int userId = jobseeker.getUserId(); // Use userId instead of uid
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JobSeekers> responseEntity = restTemplate
                .getForEntity("http://localhost:8091/AspireHub/api/v1/getJobSeekers/" + userId, JobSeekers.class); // Use JobSeekers.class instead of User.class
        JobSeekers jobseeker1 = responseEntity.getBody();
        System.out.println("jobseeker1" + jobseeker1.getProfilepic());
        
        session.setAttribute("jobseeker1", jobseeker1); // Set jobseeker1 in the session
        System.out.println("jobseeker1: " + jobseeker1.getProfilepic());
        
        String imageUrl = jobseeker1.getProfilepic();
        jobseeker1.setProfilepic(imageUrl);
        
        System.out.println("jobseeker1 = " +jobseeker1.getProfilepic());
        model.addAttribute("jobseeker1", jobseeker1);

        ModelAndView view = new ModelAndView("User/jobseekerProfile");
        return view;
    }
    
    @GetMapping("/jobseekerVacancies")
	 public ModelAndView viewVacancies(Model model) {
	     RestTemplate restTemplate = new RestTemplate();
	     ResponseEntity<Vacancies[]> responseEntity = restTemplate.getForEntity("http://localhost:8091/AspireHub/api/v1/allVacancies", Vacancies[].class);
	     Vacancies[] responseBody = responseEntity.getBody();
	     List<Vacancies> vacanciesList = Arrays.asList(responseBody);
	     model.addAttribute("vacanciesList", vacanciesList);
	     ModelAndView view = new ModelAndView("User/jobseekerVacancies");
	     return view;
	 }
    
    
   /* @GetMapping("/gettakeexam/{vacancyId}")
    public ModelAndView getTakeExam(HttpSession session, @PathVariable("vacancyId") int vacancyId) {
        // Your existing code
    
  // Check if job seeker is logged in
        JobSeekers jobseeker = (JobSeekers) session.getAttribute("jobseeker");
        if (jobseeker == null) {
            // Redirect to login page if not logged in
            return new ModelAndView("redirect:/jobApplicationHistory");
        }
        int userId = jobseeker.getUserId();
        // Retrieve questions for the given vacancyId synchronously
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Questions>> response = restTemplate.exchange(
                "http://localhost:8090/api/v1/allQuestions/{vacancyId}", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Questions>>() {}, vacancyId);
        List<Questions> questionsList = response.getBody();
        // Pass the userId, vacancyId, and questionsList to the view
        ModelAndView modelAndView = new ModelAndView("User/takeExam");
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("vacancyId", vacancyId);
        modelAndView.addObject("questionsList", questionsList);
        // Set questionsList in session
        session.setAttribute("questionsList", questionsList);
        return modelAndView;
    }
    
    @PostMapping("/takeExam")
    public String takeExam(HttpServletRequest request, HttpSession session) {
        // Retrieve questionsList from the session
        List<Questions> questionsList = (List<Questions>) session.getAttribute("questionsList");
        // Check if questionsList is null or empty
        if (questionsList == null || questionsList.isEmpty()) {
            // Handle the case where questionsList is null or empty
            // You can redirect to an error page or return an error message
            System.out.println("No questions found in the session.");
            return "error";
        }
        // Create a list to store valid test results
        List<TestResult> validTestResults = new ArrayList<>();
        // Create variables to store the score and total questions
        int totalQuestions = 0;
        int correctCount = 0; // Initialize correct count
        // Iterate through each question in the questionsList
        for (Questions question : questionsList) {
            // Retrieve the selected option for each question
            String selectedOption = request.getParameter("selectedOption_" + question.getQuestionId());
            // Retrieve the correct option for the question
            String correctOption = question.getCorrect();
            System.out.println("Question ID: " + question.getQuestionId() +
                    ", Selected Option: " + selectedOption + ", Correct Option: " + correctOption);
            // Check if the selected option is correct
            boolean isCorrect = selectedOption != null && selectedOption.equals(correctOption);
            if (isCorrect) {
                // Increase correct count if the selected option is correct
                correctCount++;
            }
            // Create a test result object for the current question
            TestResult testResult = new TestResult();
            JobSeekers job = (JobSeekers) session.getAttribute("jobseeker");
            testResult.setJobseekers(job); // Set the jobseeker
            Vacancies vacancyId = (Vacancies) session.getAttribute("vacancyId");
            testResult.setVacancies(vacancyId); 
            testResult.setQuestion(question); // Set the question
            testResult.setSelectedOption(selectedOption); // Set the selected option
            // Set the score for the current question
            int score = isCorrect ? 1 : 0;
            testResult.setScore(score);
            // You can set other properties of testResult as per your requirements
            // Add the test result to the list of valid test results
            validTestResults.add(testResult);
            // Increment total questions count
            totalQuestions++;
        }
        // Calculate percentage score
        double percentage = ((double) correctCount / totalQuestions) * 100;
        // Set the result to "pass" if the percentage score is greater than or equal to 70, otherwise set it to "fail"
        String result = percentage >= 70 ? "pass" : "fail";
        // Set the result for each valid test result
        validTestResults.forEach(testResult -> testResult.setResult(result));
        // Display score and result
        System.out.println("Total Questions: " + totalQuestions);
        System.out.println("Correct Answers: " + correctCount);
        System.out.println("Percentage Score: " + percentage + "%");
        System.out.println("Result: " + result);
        // Assuming you have a RestTemplate bean autowired
        RestTemplate restTemplate = new RestTemplate();
        // Make a POST request to save the valid test results only
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:8090/api/v1/addTestResult",
                validTestResults, // Send only the valid test results
                String.class);
        // Check the response status code and handle accordingly
        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("Test results saved successfully.");
            // Redirect or return appropriate response
            return "redirect:/jobSeekershome"; // Redirect to a page showing the exam result
        } else {
            System.out.println("Failed to save test results.");
            // Handle the case where test results are not saved successfully
            // You can redirect to an error page or return an error message
            return "User/jobApplicationHistory";
        }

 
 }*/
    
    @GetMapping("/takeExam/{vacancyId}")
	public ModelAndView takeExam(Model model, @PathVariable("vacancyId") int vacancyId,HttpSession session) {
		Questions question = new Questions();
		System.out.println("oooo vacancyId" + vacancyId);
		Vacancies vacancy1 = new Vacancies();
		vacancy1.setVacancyId(vacancyId);

		question.setVacancies(vacancy1);

		model.addAttribute("question", question);
		System.out.println("oooo " + question);

		//Adding testId and vacancyId to the model
		//model.addAttribute("testId", testId);
		model.addAttribute("vacancyId", vacancyId);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Questions[]> responseEntity = restTemplate
				.getForEntity("http://localhost:8091/AspireHub/api/v1/allQuestions/" + vacancyId, Questions[].class);

		Questions[] responseBody = responseEntity.getBody();
		List<Questions> quesList = Arrays.asList(responseBody);
		System.out.println("quesList" + quesList);
		System.out.println("quesList" + quesList.size());
		
		 // Set questionsList attribute in session
	    session.setAttribute("questionsList", quesList);
	   
		
		model.addAttribute("quesList", quesList);
		ModelAndView view = new ModelAndView("User/takeExam");
		return view;
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	////////////////////after selecting options and clicking on submit exam for that the code 
	/////////////////////////////////////////////////////////////////////////////////////////////

	@PostMapping("/submitExam")
	public String submitExam(HttpServletRequest request, HttpSession session) {
///////////////////////////////////////////////////////////////////////////////
		JobSeekers jobSeeker = (JobSeekers) session.getAttribute("jobseeker");
		int userId = jobSeeker.getUserId();
		System.err.println("User ID: " + userId);

		////////////////////////////////////////////////////////////////////////////////
		List<Questions> questionsList = (List<Questions>) session.getAttribute("questionsList");
		int totalScore = 0;

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		for (Questions quest : questionsList) {
			int questId = quest.getQuestionId();
			////////////////////////////////////////////////////////////////////////
			Vacancies  vacancy = quest.getVacancies();
			/////////////////////////////////////
			System.err.println("vacancyId ===" + vacancy.getVacancyId());

			String selectedOption = request.getParameter("selectedOptions" + questId);
			String correctOption = quest.getCorrect();
			System.out.println("questId: " + questId + ", selectedOptions: " + selectedOption + ", correctOption: "
					+ correctOption);

			String score = "0";
			String result = "incorrect";

			if (selectedOption != null && selectedOption.equals(correctOption)) {
				result = "correct";
				score = quest.getScore();
				totalScore += Integer.parseInt(quest.getScore());

				// Add score to totalScore

			}

			TestResult testResult = new TestResult();
			testResult.setJobseekers(jobSeeker);
			testResult.setQuestion(quest);
			testResult.setVacancies(vacancy);
			testResult.setSelectedOption(selectedOption);
			testResult.setResult(result);

			// Assuming you calculate score somewhere else and set it here
			testResult.setScore(score);
			////////////////////////////////////////////////////////////////////////////////////
			// Save the test result
			// setting test result object to end point
			HttpEntity<TestResult> entity = new HttpEntity<TestResult>(testResult, headers);

			String url = "http://localhost:8091/AspireHub/api/v1/addTestResult";
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

			String responseString = response.getBody();
			System.out.println("responseString" + responseString);

			/////////////////////////////////////////////////////////////////

			String status;
			if (totalScore >= 75) {
				status = "qualified";
			} else {
				status = "better luck next time";
			}

			System.out.println("totalscore" + totalScore);

			JobApply jobapply = new JobApply();
///
			jobapply.setStatus(String.valueOf(totalScore));

			HttpEntity<JobApply> entity1 = new HttpEntity<JobApply>(jobapply, headers);

			String url1 = "http://localhost:8091/AspireHub/api/v1/updateStatus/" + userId + "/" + vacancy.getVacancyId() + "/"
					+ status + "/" + totalScore;
			ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.PUT, entity, String.class);

			String responseString1 = response.getBody();
			System.out.println("responseString" + responseString);

		}

		return "redirect:/jobSeekershome";
	}
	
	@GetMapping("/viewTestResult")
	public ModelAndView viewTestResult(Model model, HttpSession session)
	{
		System.out.println("in view test result method");
		RestTemplate restTemplate = new RestTemplate();
		int userId = ((JobSeekers) session.getAttribute("jobseeker")).getUserId();

		ResponseEntity<TestResult[]> responseEntity = restTemplate
				.getForEntity("http://localhost:8091/AspireHub/api/v1/findTestResultsByUserId/" + userId, TestResult[].class);
		
		TestResult[] responseBody = responseEntity.getBody();
		List<TestResult> testResultList = Arrays.asList(responseBody);
		System.out.println("Test result List="+testResultList);
		model.addAttribute("testResultList", testResultList);
		ModelAndView view = new ModelAndView("User/viewResults");
		return view;

	}
	
	@GetMapping("/editJobSeekerProfile/{userId}")
	public ModelAndView editJobSeekerProfile(@PathVariable("userId") int userId, Model model, HttpSession session) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JobSeekers> responseEntity = restTemplate
				.getForEntity("http://localhost:8091/AspireHub/api/v1/getJobSeekers/" + userId, JobSeekers.class);
		JobSeekers jobseeker = responseEntity.getBody();
		System.out.println("JobSeeker Obj in frontend=" + jobseeker);
		model.addAttribute("jobseeker", jobseeker);

		ModelAndView view = new ModelAndView("User/updateJobseekerProfile");

		return view;
	}

	@PostMapping("/updatingJobseeker")
	public String updatingJobseeker(@ModelAttribute("jobseeker") JobSeekers jobseeker, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<JobSeekers> entity = new HttpEntity<>(jobseeker, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put("http://localhost:8091/AspireHub/api/v1/updateJobSeekers/{userId}", entity, jobseeker.getUserId());

		ResponseEntity<JobSeekers> responseEntity = restTemplate
				.getForEntity("http://localhost:8091/AspireHub/api/v1/getJobSeekers/" + jobseeker.getUserId(), JobSeekers.class);
		JobSeekers jobseeker2 = responseEntity.getBody();
		session.setAttribute("jobseeker", jobseeker2);

		return "redirect:/jobseekerProfile";
	}

	
	
	
	
	@GetMapping("/editProfilePic")
	public ModelAndView editCompProf(Model model) {
		JobSeekers jobseeker = new JobSeekers();

		model.addAttribute("jobseeker", jobseeker);
		ModelAndView view = new ModelAndView("User/updateProfilePic");

		return view;
	}

	@PostMapping("/updatingPicture")
	public String updatingPicture(@RequestParam("logo1") MultipartFile logo1, Model model, HttpSession session) {

		try {
			JobSeekers jobseeker = (JobSeekers) session.getAttribute("jobseeker");

			int userId = jobseeker.getUserId();

			// Create the upload directory if not exists
			Files.createDirectories(Path.of("./temp_uploads"));

			byte[] picBytes = logo1.getBytes();
			String jobseekerPicName = "temp_" + System.currentTimeMillis() + "_" + logo1.getOriginalFilename();
			Files.write(Paths.get("./temp_uploads", jobseekerPicName), picBytes);

			// Set the product picture name in the product object
			jobseeker.setProfilepic(jobseekerPicName);

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<JobSeekers> entity = new HttpEntity<JobSeekers>(jobseeker, headers);
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8091/AspireHub/api/v1/addJobSeekers";
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

			ResponseEntity<JobSeekers> responseEntity = restTemplate
					.getForEntity("http://localhost:8091/AspireHub/api/v1/getJobSeekers/" + jobseeker.getUserId(), JobSeekers.class);
			 jobseeker = responseEntity.getBody();
			session.setAttribute("jobseeker", jobseeker);

			return "redirect:/jobseekerProfile";
		} catch (Exception e) {

			model.addAttribute("responseString", e.getMessage());
			// Redirect to some error page or handle it accordingly
			return "errorPage";
		}

	}

}






    
    


