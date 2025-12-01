package com.example.presentation.screen.home

import com.example.domain.model.feature.hospitals.MatchedHospital
import com.example.domain.model.feature.review.Review
import com.example.domain.model.feature.review.ReviewList

data class HomeData(
    val matchedHospitals: List<MatchedHospital>,
    val hospitalReviews: ReviewList
) {
    companion object {
        fun stub() = HomeData(
            matchedHospitals = listOf(MatchedHospital.stub()),
            hospitalReviews = ReviewList(
                listOf(
                    Review.stub(),
                ), null, false
            )
        )
    }
}