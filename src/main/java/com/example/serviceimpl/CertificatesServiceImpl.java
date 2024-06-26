package com.example.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Certificates;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.CertificatesRepository;
import com.example.service.CertificatesService;

@Service
public class CertificatesServiceImpl implements CertificatesService {

    private final CertificatesRepository certificatesRepository;

    @Autowired
    public CertificatesServiceImpl(CertificatesRepository certificatesRepository) {
        this.certificatesRepository = certificatesRepository;
    }

	@Override
	public Certificates addCertificates(Certificates certificates) {
		// TODO Auto-generated method stub
		return  certificatesRepository.save(certificates);
	}

	@Override
	public List<Certificates> getAllCertificates() {
		// TODO Auto-generated method stub
		return certificatesRepository.findAll();
	}

	

	


	@Override
	public boolean isDocumentExist(int documentId) {
		// TODO Auto-generated method stub
		return certificatesRepository.existsById(documentId);

	}

	@Override
	public Certificates getDocumentById(int documentId) {
		// TODO Auto-generated method stub
		Optional<Certificates> document = certificatesRepository.findById((int) documentId);
        if (document.isPresent()) {
            return document.get();
        } else {
            throw new ResourceNotFoundException("Document", "DocumentId", documentId);
        }

		
	}

	@Override
	public boolean updateDocument(Certificates certificate) {
		// TODO Auto-generated method stub
		if (isDocumentExist(certificate.getDocumentId())) {
            certificatesRepository.save(certificate);
            return true;
        }

		return false;
	}

	@Override
	public boolean deleteDocument(int documentId) {
		// TODO Auto-generated method stub
		if (isDocumentExist(documentId)) {
            certificatesRepository.deleteById((int) documentId);
            return true;
        }

		return false;
	}
	
	@Override
	public List<Certificates> getDocumentsByUserId(int userId) {
		List<Certificates> certificatelist=certificatesRepository.findByJobseekersUserId(userId);
		return certificatelist;
	}

    

}
