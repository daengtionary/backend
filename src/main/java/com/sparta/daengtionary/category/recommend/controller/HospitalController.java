package com.sparta.daengtionary.category.recommend.controller;


import com.sparta.daengtionary.category.recommend.dto.request.MapPutRequestDto;
import com.sparta.daengtionary.category.recommend.dto.request.MapRequestDto;
import com.sparta.daengtionary.category.recommend.dto.request.ReviewRequestDto;
import com.sparta.daengtionary.category.recommend.service.MapReviewService;
import com.sparta.daengtionary.category.recommend.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class HospitalController {

    private final MapService mapService;
    private final MapReviewService mapReviewService;

    @PostMapping("/create")
    public ResponseEntity<?> createHospital(@RequestPart(value = "data") MapRequestDto mapRequestDto,
                                            @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return mapService.createMap(mapRequestDto, multipartFiles);
    }

    @GetMapping()
    public ResponseEntity<?> getAllHospitalCategory(@RequestParam String address, @RequestParam String sort,
                                                    @RequestParam int pagenum, @RequestParam int pagesize) {
        return mapService.getAllMapByCategory("hospital", address, sort, pagenum, pagesize);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearchHospital(@RequestParam String title, @RequestParam String content, @RequestParam String nick, @RequestParam String sort,
                                               @RequestParam String address, @RequestParam int pagenum, @RequestParam int pagesize) {
        return mapService.getSearchMap("hospital", title, content, nick, address, sort, pagenum, pagesize);
    }

    @GetMapping("/{mapNo}")
    public ResponseEntity<?> getHospital(@PathVariable Long mapNo, @RequestParam int pagenum, @RequestParam int pagesize) {
        mapService.mapViewUpdate(mapNo);
        return mapService.getAllMap(mapNo, pagenum, pagesize);
    }

    @PatchMapping("/{mapNo}")
    public ResponseEntity<?> updateHospital(@PathVariable Long mapNo, @RequestPart(value = "data") MapPutRequestDto requestDto,
                                            @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return mapService.mapUpdate(requestDto, mapNo, multipartFiles);
    }

    @DeleteMapping("/{mapNo}")
    public ResponseEntity<?> deleteHospital(@PathVariable Long mapNo) {
        return mapService.mapDelete(mapNo);
    }

    @PostMapping("/review/create/{hospitalNo}")
    public ResponseEntity<?> createHospitalReview(@RequestPart(value = "data") ReviewRequestDto requestDto, @PathVariable Long hospitalNo) {
        return mapReviewService.createMapReview(hospitalNo, requestDto);
    }

    @PatchMapping("/review/{hospitalNo}/{reviewNo}")
    public ResponseEntity<?> updateHospitalReview(@RequestPart(value = "data") ReviewRequestDto requestDto, @PathVariable Long hospitalNo,
                                                  @PathVariable Long reviewNo) {
        return mapReviewService.updateMapReview(hospitalNo, reviewNo, requestDto);
    }

    @DeleteMapping("/review/{hospitalNo}/{reviewNo}")
    public ResponseEntity<?> deleteHospitalReview(@PathVariable Long hospitalNo, @PathVariable Long reviewNo) {
        return mapReviewService.deleteMapReview(hospitalNo, reviewNo);
    }
}
