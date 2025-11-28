package com.example.presentation.screen.home

import com.example.domain.model.feature.hospitals.HospitalCard
import com.example.domain.model.feature.reviews.HospitalReview

data class HomeData(
    val hospitalCards: List<HospitalCard>,
    val hospitalReviews: List<HospitalReview>
) {
    companion object {
        fun stub() = HomeData(
            hospitalCards = listOf(HospitalCard.stub()),
            hospitalReviews = listOf(HospitalReview.stub())
        )
    }
}