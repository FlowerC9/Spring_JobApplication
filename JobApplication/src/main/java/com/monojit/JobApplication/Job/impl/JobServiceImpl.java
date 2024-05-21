package com.monojit.JobApplication.Job.impl;

import com.monojit.JobApplication.Job.Job;
import com.monojit.JobApplication.Job.JobService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    private List<Job> jobs = new ArrayList<>();
    @Override
    public List<Job> findAll() {
       return jobs;
    }

    @Override
    public void createJob(Job job) {
        job.setId(Long.valueOf(jobs.size()));
        jobs.add(job);
    }

    @Override
    public Job getJobById(Long id) {
        Optional<Job> job= jobs.stream().filter(j->j.getId().equals(id)).findFirst();
        return job.orElse(null);
    }

    @Override
    public Boolean removeJob(Long id) {
        return jobs.removeIf(job->job.getId().equals(id));
    }

    @Override
    public Boolean updateJob(Long id, Job updatedJob) {
        Optional<Job>existingJobOpt=jobs.stream().filter(j->j.getId().equals(id)).findFirst();
        if(existingJobOpt.isPresent()){
            Job existingJob = existingJobOpt.get();
            existingJob.setTitle(updatedJob.getTitle());
            existingJob.setDescription(updatedJob.getDescription());
            existingJob.setMinSalary(updatedJob.getMinSalary());
            existingJob.setMaxSalary(updatedJob.getMaxSalary());
            existingJob.setLocation(updatedJob.getLocation());
            return true;
        }else {
            return false;
        }
    }

}
