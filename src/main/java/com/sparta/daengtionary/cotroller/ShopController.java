package com.sparta.daengtionary.cotroller;

import com.sparta.daengtionary.dto.request.MapPutRequestDto;
import com.sparta.daengtionary.dto.request.MapRequestDto;
import com.sparta.daengtionary.dto.request.ReviewRequestDto;
import com.sparta.daengtionary.service.MapReviewService;
import com.sparta.daengtionary.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final MapService mapService;
    private final MapReviewService mapReviewService;

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestPart(value = "data") MapRequestDto mapRequestDto,
                                        @RequestPart(value = "imgUrl", required = false) List<MultipartFile> mapImgs) {
        return mapService.createMap(mapRequestDto, mapImgs);
    }


    @GetMapping()
    public ResponseEntity<?> getAllRoomCategory(@RequestParam String address, @RequestParam String direction,
                                                Pageable pageable) {
        String category = "shop";
        return mapService.getAllMapByCategory(category,direction ,address, pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearchMap(@RequestParam String title, @RequestParam String content, @RequestParam String nick,
                                          @RequestParam String address, @RequestParam String direction, Pageable pageable){
        return mapService.getSearchMap("shop",title,content,nick,address,direction,pageable);
    }

    @GetMapping("/{shopNo}")
    public ResponseEntity<?> getRoom(@PathVariable Long shopNo) {
        mapService.mapViewUpdate(shopNo);
        return mapService.getAllMap(shopNo);
    }


    @PatchMapping("/{shopNo}")
    public ResponseEntity<?> updateRoom(@PathVariable Long shopNo, @RequestPart(value = "data") MapPutRequestDto requestDto,
                                        @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return mapService.mapUpdate(requestDto, shopNo, multipartFiles);
    }

    @DeleteMapping("/{shopNo}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long shopNo) {
        return mapService.mapDelete(shopNo);
    }

    @PostMapping("/review/create/{shopNo}")
    public ResponseEntity<?> createReview(@RequestPart(value = "data") ReviewRequestDto requestDto,
                                          @RequestPart(value = "imgUrl",required = false)MultipartFile multipartFile,@PathVariable Long shopNo){
        return mapReviewService.createMapReview(shopNo,requestDto,multipartFile);
    }

    @PatchMapping("/review/{shopNo}/{reviewNo}")
    public ResponseEntity<?> updateRoomReview(@RequestPart(value = "data") ReviewRequestDto requestDto,
                                              @RequestPart(value = "imgUrl",required = false)MultipartFile multipartFile,@PathVariable Long shopNo,
                                              @PathVariable Long reviewNo){
        return mapReviewService.updateMapReview(shopNo,reviewNo,requestDto,multipartFile);
    }

    @DeleteMapping("/review/{shopNo}/{reviewNo}")
    public ResponseEntity<?> deleteRoomReview(@PathVariable Long shopNo,@PathVariable Long reviewNo){
        return mapReviewService.deleteMapReview(shopNo,reviewNo);
    }
}
