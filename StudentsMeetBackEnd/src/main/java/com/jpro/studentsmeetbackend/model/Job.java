package com.jpro.studentsmeetbackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;


@Entity
@Component
@Table(name="STMT_JOB")
public class Job extends BaseDomain{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long jobId;
	
	@Size(min=4,max=25)
	private String jobName;
	
	private String jobDescription;
	
	private float jobSalary;
	
	private String jobStatus;

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	
	public float getJobSalary() {
		return jobSalary;
	}

	public void setJobSalary(float jobSalary) {
		this.jobSalary = jobSalary;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	
}
