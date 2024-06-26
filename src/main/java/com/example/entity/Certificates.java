package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "certificates")
public class Certificates {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "documentId")
	private int documentId;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private JobSeekers jobseekers;
	
	@Column(name="documentType")
	private String documentType;
	
	@Column(name="documentFile")
	private String documentFile;
	
	

}
