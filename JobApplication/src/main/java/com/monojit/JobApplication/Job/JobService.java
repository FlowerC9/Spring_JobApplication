package com.monojit.JobApplication.Job;

import java.util.List;

public interface JobService {
    List<Job> findAll();
    void createJob(Job job);

    Job getJobById(Long id);

    Boolean removeJob(Long id);

    Boolean updateJob(Long id, Job updatedJob);
}
