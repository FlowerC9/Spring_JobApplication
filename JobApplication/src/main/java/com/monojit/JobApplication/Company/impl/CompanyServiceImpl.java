package com.monojit.JobApplication.Company.impl;

import com.monojit.JobApplication.Company.Company;
import com.monojit.JobApplication.Company.CompanyRepository;
import com.monojit.JobApplication.Company.CompanyService;
import com.monojit.JobApplication.Job.Job;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Boolean updateCompany(Long id,Company company) {
        Optional<Company> existingCompanyOpt=companyRepository.findById(id);
        if(existingCompanyOpt.isPresent()){
            Company existingCompany = existingCompanyOpt.get();
            existingCompany.setDescription(company.getDescription());
            existingCompany.setName(company.getName());
            existingCompany.setJobs(company.getJobs());
            companyRepository.save(existingCompany);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public Boolean deleteCompanyById(Long id) {
        if(companyRepository.existsById(id)){
            companyRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    @Override
    public Company getCompanyById(Long id) {
        return  companyRepository.findById(id).orElse(null);
    }

}
