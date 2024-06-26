package com.example.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.entity.Certificates;
import com.example.entity.JobSeekers;

import jakarta.servlet.http.HttpSession;

@Controller
public class CertificatesController {
	
	@GetMapping("/addDocuments")
    public ModelAndView showAddDocumentPage(Model model) {
        Certificates certificate = new Certificates();
        model.addAttribute("certificate", certificate);
		ModelAndView view = new ModelAndView("User/addDocuments");
        return view;
    }

 
 @PostMapping("/addingDocuments")
    public String addingDocuments(@ModelAttribute("certificate") Certificates certificate, HttpSession session, Model model, @RequestParam("documentFile1") MultipartFile documentFile1) {
        System.out.println("Inside addingDocuments method");
        // Retrieving the JobSeekers object from the session
        JobSeekers jobseeker = (JobSeekers) session.getAttribute("jobseeker");
        //Long userId=jobseeker.getUserId();
        // Check if job seeker is not null
        if (jobseeker != null) {
            int userId = jobseeker.getUserId();
            // Now you have the job seeker's ID, you can use it as needed
            System.out.println("Job Seeker ID: " + userId);
            try {
                // Create the upload directory if not exists
                Files.createDirectories(Path.of("./temp_uploads"));

                // Handle file upload for document
                byte[] documentFileBytes = documentFile1.getBytes();
                String documentFileName = "temp_" + System.currentTimeMillis() + "_" + documentFile1.getOriginalFilename();
                Files.write(Paths.get("./temp_uploads", documentFileName), documentFileBytes);

                // Set the file name in the document object
                certificate.setDocumentFile(documentFileName);
                certificate.setJobseekers(jobseeker);

                // Prepare the HTTP headers
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                HttpEntity<Certificates> entity = new HttpEntity<>(certificate, headers);

                // Make a REST API call to add the document
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.exchange("http://localhost:8091/AspireHub/api/v1/addCertificates", HttpMethod.POST,
                        entity, String.class);
                String responseMsg = response.getBody();
                System.out.println("Response object in documents=" + responseMsg);

                // Code to upload document file
                HttpHeaders picHeaders = new HttpHeaders();
                picHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                MultiValueMap<String, Object> picMap = new LinkedMultiValueMap<>();
                picMap.add("file", new FileSystemResource("./temp_uploads/" + documentFileName));
                picMap.add("filename", documentFileName);
                HttpEntity<MultiValueMap<String, Object>> picEntity = new HttpEntity<>(picMap, picHeaders);
                // Send a request to upload the document file
                String uploadPicUrl = "http://localhost:8091/AspireHub/api/files/upload";
                ResponseEntity<String> picResponse = restTemplate.exchange(uploadPicUrl, HttpMethod.POST, picEntity,
                        String.class);
                String picResponseString = picResponse.getBody();

                System.out.println("responseMsg=" + responseMsg);

                // Clean up: Delete the temporary document file
                Files.deleteIfExists(Paths.get("./temp_uploads", documentFileName));

                // Reset document object for the next upload
                certificate = new Certificates();
                model.addAttribute("certificate", certificate);
                model.addAttribute("responseString", picResponseString);
                model.addAttribute("job", "*Thank you for submitting");
                return "redirect:jobSeekershome";
            } catch (IOException e) {
                model.addAttribute("responseString", e.getMessage());
                return "redirect:User/addDocuments";
            }
        } else {
            // Handle the case where jobseeker is null or not found in the session
            // Redirect to login page or display an error message
            System.out.println("Job seeker not found in the session.");
            return "redirect:jobSeekershome"; // Example redirect to login page
        }
    }
 
 
 @GetMapping("/viewDocuments")
 public ModelAndView viewDocuments(Model model, HttpSession session) {
     JobSeekers jobseeker = (JobSeekers) session.getAttribute("jobseeker");
     int userId = jobseeker.getUserId();
     System.out.println("userId: " + userId);

     RestTemplate restTemplate = new RestTemplate();
     ResponseEntity<Certificates[]> responseEntity = restTemplate
             .getForEntity("http://localhost:8091/AspireHub/api/v1/getDocuments/" + userId, Certificates[].class);

     if (responseEntity.getStatusCode() == HttpStatus.OK) {
         Certificates[] documents = responseEntity.getBody();
         List<Certificates> docList = Arrays.asList(documents);
         System.out.println("docList: " + docList.size());
         model.addAttribute("documentList", docList);
     } else {
         System.out.println("Document not found");
         model.addAttribute("documentList", new ArrayList<>());
     }

     ModelAndView view = new ModelAndView("User/viewDocuments");
     return view;
 }
 
 
 @GetMapping("/deleteDocument/{documentId}")
 public String deleteDocument(@PathVariable("documentId") int documentId, Model model) {
     RestTemplate restTemplate = new RestTemplate();
     String url = "http://localhost:8091/AspireHub/api/v1/deleteDocument/" + documentId;
     ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
     ModelAndView view = new ModelAndView("User/viewDocuments");
	    view.addObject("responseMsg", "Document deleted successfully");
	    return "redirect:/viewDocuments";
     
 }
 
 
 /*@GetMapping("/editDocument/{documentId}")
 public ModelAndView editDocument(@PathVariable int documentId, Model model) {
     RestTemplate restTemplate = new RestTemplate();
     Certificates certificate = restTemplate.getForObject("http://localhost:8090/api/v1/getDocument/"+documentId, Certificates.class);
     model.addAttribute("certificate", certificate);
     return new ModelAndView("User/editDocument");
 }*/
 
 @GetMapping("/editDocument/{documentId}")
	public ModelAndView editDocument(@PathVariable("documentId") int documentId,Model model) {
		
		
		RestTemplate restTemplate = new RestTemplate();	
		ResponseEntity<Certificates> responseEntity = restTemplate.getForEntity("http://localhost:8091/AspireHub/api/v1/getDocument/" + documentId,
				Certificates.class);
		Certificates certificate = responseEntity.getBody();
		System.out.println("Docummmments:" + certificate.getDocumentId());
		System.out.println("Docummmments:" + certificate.getDocumentType());
		System.out.println("userId D" +certificate.getJobseekers().getUserId());
		model.addAttribute("certificate", certificate);
		ModelAndView view = new ModelAndView("User/editDocument");
		return view;
	}

@PostMapping("/updateDocument")
	public String addProducts(@ModelAttribute("certificate") Certificates certificate,
			@RequestParam("documentFile1") MultipartFile documentFile1, Model model) {
		// Set the product picture name and save it to a temporary location
		///////////////////////////////////////////////////////////////
	 
		try {

			// Create the upload directory if not exists
			Files.createDirectories(Path.of("./temp_uploads"));
			
			
			
			
			byte[] picBytes = documentFile1.getBytes();
			String docFile = "temp_" + System.currentTimeMillis() + "_" + documentFile1.getOriginalFilename();
			Files.write(Paths.get("./temp_uploads", docFile), picBytes);

			// Set the product picture name in the product object
			certificate.setDocumentFile(docFile);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<Certificates> entity = new HttpEntity<Certificates>(certificate, headers);

			RestTemplate restTemplate = new RestTemplate();

			String url = "http://localhost:8091/AspireHub/api/v1/updateDocument/"+certificate.getDocumentId();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

			// System.out.println("responseFlag=" + responseFlag);

			// Up to here product is adding

			//////////////////////// code to upload product picture
			HttpHeaders picHeaders = new HttpHeaders();
			picHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, Object> picMap = new LinkedMultiValueMap<>();
			picMap.add("file", new FileSystemResource("./temp_uploads/" + docFile));
			picMap.add("filename", docFile);
			HttpEntity<MultiValueMap<String, Object>> picEntity = new HttpEntity<>(picMap, picHeaders);

			// Send a request to upload the product picture
			String uploadPicUrl = "http://localhost:8091/AspireHub/api/files/upload";
			ResponseEntity<String> picResponse = restTemplate.exchange(uploadPicUrl, HttpMethod.POST, picEntity,
					String.class);

			String picResponseString = picResponse.getBody();

			// Clean up: Delete the temporary product picture
			Files.deleteIfExists(Paths.get("./temp_uploads", docFile));

			///////////////////////

			certificate = new Certificates();
			model.addAttribute("certificate", certificate);
			

			return "redirect:/viewDocuments";

		} catch (IOException e) {
			// Handle exceptions related to file operations or HTTP requests

			
			return null;
		}

	}
}


