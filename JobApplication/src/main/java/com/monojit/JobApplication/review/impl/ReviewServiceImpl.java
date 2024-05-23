package com.monojit.JobApplication.review.impl;

import com.monojit.JobApplication.Company.Company;
import com.monojit.JobApplication.Company.CompanyService;
import com.monojit.JobApplication.review.Review;
import com.monojit.JobApplication.review.ReviewRepository;
import com.monojit.JobApplication.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private CompanyService companyService;
    public ReviewServiceImpl(ReviewRepository reviewRepository,CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService=companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review>reviews=reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public Boolean addReview(Long companyId, Review review) {
        Company company = companyService.getCompanyById(companyId);
        if(company!=null){
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        }
        else return false;
    }

    @Override
    public Review getReview(Long companyId, Long reviewId) {
       List<Review> reviews=reviewRepository.findByCompanyId(companyId);
       return reviews.stream().filter(review->review.getId().equals(reviewId)).findFirst().orElse(null);
    }

    @Override
    public Boolean updateReview(Long companyId, Long reviewId, Review updatedReview) {
        if(companyService.getCompanyById(companyId)!=null){
            List<Review>reviews=reviewRepository.findByCompanyId(companyId);
            Review review=reviews.stream().filter(r->r.getId().equals(reviewId)).findFirst().orElse(null);
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            reviewRepository.save(review);
            return true;
        }
        else return false;
    }

    @Override
    public Boolean deleteReview(Long companyId, Long reviewId) {
        if(companyService.getCompanyById(companyId)!=null && reviewRepository.existsById(reviewId)){
            Review review =reviewRepository.findById(reviewId).orElse(null);
            Company company = review.getCompany();
            company.getReviews().remove(review);
            review.setCompany(null);
            companyService.updateCompany(companyId,company);
            reviewRepository.deleteById(reviewId);
            return true;
        }
        else{
            return false;
        }
    }
}
