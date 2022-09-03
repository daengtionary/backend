package com.sparta.daengtionary.cotroller;


import com.sparta.daengtionary.dto.request.MapPutRequestDto;
import com.sparta.daengtionary.dto.request.MapRequestDto;
import com.sparta.daengtionary.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class HospitalController {

    private final MapService mapService;

    @PostMapping("/create")
    public ResponseEntity<?> createMap(@RequestPart(value = "data") MapRequestDto mapRequestDto,
                                       @RequestPart(value = "imgUrl", required = false) List<MultipartFile> mapImgs) {
        return mapService.createMap(mapRequestDto, mapImgs);
    }

    @PostMapping()
    public ResponseEntity<?> getAllMapCategory(@RequestParam String orderby,
                                               Pageable pageable ) {
        String category = "hospital";
        return mapService.getAllMapByCategory(category, orderby, pageable);
    }

    @GetMapping("/{mapNo}")
    public ResponseEntity<?> getMap(@PathVariable Long mapNo) {
        return mapService.getAllMap(mapNo);
    }

    @PatchMapping("/{mapNo}")
    public ResponseEntity<?> updateMap(@PathVariable Long mapNo, @RequestPart(value = "data") MapPutRequestDto requestDto) {
        return mapService.mapUpdate(requestDto, mapNo);
    }
    @PutMapping("/{mapNo}")
    public ResponseEntity<?> updatePutMap(@PathVariable Long mapNo, @RequestPart(value = "data") MapPutRequestDto requestDto) {
        return mapService.mapUpdate(requestDto, mapNo);
    }

    @PatchMapping("/update-test/{mapNo}")
    public ResponseEntity<?> updateMap(@PathVariable Long mapNo, @RequestPart(value = "data") MapPutRequestDto requestDto,
                                       @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return mapService.mapUpdateTest(requestDto, mapNo,multipartFiles);
    }

    @DeleteMapping("/{mapNo}")
    public ResponseEntity<?> deleteMap(@PathVariable Long mapNo, @RequestParam Long memberNo) {
        return mapService.mapDelete(mapNo, memberNo);
    }
}