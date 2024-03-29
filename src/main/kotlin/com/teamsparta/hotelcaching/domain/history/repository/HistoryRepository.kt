package com.teamsparta.hotelcaching.domain.history.repository

import com.teamsparta.hotelcaching.domain.history.dto.HistoryResponse
import com.teamsparta.hotelcaching.domain.history.model.HistoryEntity
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository

interface HistoryRepository:JpaRepository<HistoryEntity,Long>,CustomHistoryRepository {

    fun findByKeyWord(keyWord:String) : HistoryEntity?

//    fun findBySearchNumber(searchNumber : Long) : List<HistoryEntity>

    @Cacheable(value = ["trend"])
    override fun findTrend(): List<HistoryResponse>
}