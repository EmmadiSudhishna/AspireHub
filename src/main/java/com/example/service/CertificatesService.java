package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Certificates;

public interface CertificatesService {
Certificates addCertificates(Certificates certificates);
    
    List<Certificates> getAllCertificates();
    
    boolean isDocumentExist(int documentId);
    Certificates getDocumentById(int documentId);
   // Optional<Certificates> getDocumentById(Integer documentId);
    boolean updateDocument(Certificates documentId);
   boolean deleteDocument(int documentId);
  // boolean updateDocument(int documentId, Certificates updatedCertificate);
   List<Certificates> getDocumentsByUserId(int userId);
   
}
