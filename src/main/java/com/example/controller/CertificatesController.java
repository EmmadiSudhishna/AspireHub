package com.example.controller;

import com.example.entity.Certificates;
import com.example.service.CertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CertificatesController {

    private final CertificatesService certificatesService;

    @Autowired
    public CertificatesController(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    @PostMapping("/addCertificates")
    public ResponseEntity<Object> addCertificates(@RequestBody Certificates certificates) {
        certificatesService.addCertificates(certificates);
        return new ResponseEntity<>("Certificate added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/allCertificates")
    public ResponseEntity<Object> getAllCertificates() {
        List<Certificates> certificates = certificatesService.getAllCertificates();
        ResponseEntity<Object> entity=new ResponseEntity<>(certificatesService, HttpStatus.OK);
        return entity;
    }

    @GetMapping(value = "/getDocument/{documentId}")
    public ResponseEntity<Object> getDocument(@PathVariable("documentId") int documentId) {
        Certificates certificate;
        if (certificatesService.isDocumentExist(documentId)) {
            certificate = certificatesService.getDocumentById(documentId);
        } else {
            certificate = null;
        }
        ResponseEntity<Object> entity = new ResponseEntity<>(certificate, HttpStatus.OK);
        return entity;
    }

    

        @DeleteMapping(value = "/deleteDocument/{documentId}")
        public ResponseEntity<Object> deleteDocument(@PathVariable("documentId") int documentId) {
            boolean deleted = certificatesService.deleteDocument(documentId);
            if (deleted) {
                return new ResponseEntity<>("Document deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Document not found", HttpStatus.NOT_FOUND);
            }
        }
        
        @PutMapping(value = "/updateDocument/{documentId}")
        public ResponseEntity<Object> updateDocument(@PathVariable("documentId") int documentId, @RequestBody Certificates certificate) {
            boolean updated = certificatesService.updateDocument(certificate);
            if (updated) {
                return new ResponseEntity<>("Document updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Document not found", HttpStatus.NOT_FOUND);
            }
        }
        
        @GetMapping("/getDocuments/{userId}")
    	public ResponseEntity<Object> getMethodName(@PathVariable("userId") int userId) {
    		List<Certificates> certificate = certificatesService.getDocumentsByUserId(userId);
    		ResponseEntity<Object> entity = new ResponseEntity<>(certificate, HttpStatus.OK);
    		return entity;
    	}
        
        


}