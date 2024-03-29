package com.teamsparta.hotelcaching.domain.hotel.controller

import com.teamsparta.hotelcaching.domain.history.dto.HistoryResponse
import com.teamsparta.hotelcaching.domain.hotel.dto.HotelRequest
import com.teamsparta.hotelcaching.domain.hotel.dto.HotelResponse
import com.teamsparta.hotelcaching.domain.hotel.service.HotelService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "hotel", description = "hotel API")
@RequestMapping("/api/hotels")
@RestController
class HotelController(
    private val hotelService: HotelService
) {

    @Operation(summary = "호텔 전체 조회")
    @GetMapping
    fun getHotelList(
        @RequestParam(name = "page", defaultValue = "0")
        page: Int,
        @RequestParam(name = "size", defaultValue = "10")
        size: Int
    ): ResponseEntity<List<HotelResponse>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(hotelService.getHotelList(page, size))
    }

    @PostMapping
    fun createHotel(request: HotelRequest):ResponseEntity<HotelResponse> {
        val createHotel = hotelService.createHotel(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(createHotel)
    }

    @DeleteMapping("/{hotelId}")
    fun deleteHotel(@PathVariable hotelId: Long):ResponseEntity<Unit> {
        hotelService.deleteHotel(hotelId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @Operation(summary = "v1")
    @GetMapping("/search/v1")
    fun searchHotelList(@RequestParam(name = "name") name : String): ResponseEntity<List<HotelResponse>>{
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.searchHotelList(name))
    }

    @Operation(summary = "v1")
    @GetMapping("/search/paging/v1")
    fun searchHotelListWithPaging(
        @RequestParam(name = "name") name : String,
        @RequestParam(name = "page", defaultValue = "0") page: Int,
        @RequestParam(name = "size", defaultValue = "5") size: Int,
    ): ResponseEntity<Page<HotelResponse>>{
        val pageable = PageRequest.of(page,size)
        val hotelPage = hotelService.searchHotelListWithPaging(name,pageable)
        return ResponseEntity.status(HttpStatus.OK).body(hotelPage)
    }

    @Operation(summary = "캐싱 적용 v2")
    @GetMapping("/search/v2")
    fun searchHotelListVersion2(@RequestParam(name = "name") name : String): ResponseEntity<List<HotelResponse>>{
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.searchHotelListVersion2(name))
    }

    @Operation(summary = "캐싱 적용 v2")
    @GetMapping("/search/paging/v2")
    fun searchHotelListWithPagingVersion2(
        @RequestParam(name = "name") name : String,
        @RequestParam(name = "page", defaultValue = "0") page: Int,
        @RequestParam(name = "size", defaultValue = "5") size: Int,
    ): ResponseEntity<Page<HotelResponse>>{
        val pageable = PageRequest.of(page,size)
        val hotelPage = hotelService.searchHotelListWithPagingVersion2(name,pageable)
        return ResponseEntity.status(HttpStatus.OK).body(hotelPage)
    }

    @GetMapping("/popular")
    fun getPopularKeyWordBySearchNumber():ResponseEntity<List<HistoryResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getPopularKeyWordBySearchNumber())
    }
}