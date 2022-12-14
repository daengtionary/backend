package com.sparta.daengtionary.category.community.controller;

import com.sparta.daengtionary.category.community.dto.request.CommunityRequestDto;
import com.sparta.daengtionary.category.community.service.CommunityReviewService;
import com.sparta.daengtionary.category.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService service;
    private final CommunityReviewService communityReviewService;

    @PostMapping("/create")
    public ResponseEntity<?> creatCommunity(@RequestPart(value = "data") CommunityRequestDto requestDto,
                                            @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFileList) {
        return service.createCommunity(requestDto, multipartFileList);
    }

    @GetMapping()
    public ResponseEntity<?> getCommunitySort(@RequestParam String sort, @RequestParam int pagenum, @RequestParam int pagesize) {
        return service.getCommunitySort(sort, pagenum, pagesize);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearchCommunity(@RequestParam String category, @RequestParam String title, @RequestParam String content, @RequestParam String nick,
                                                @RequestParam String sort, @RequestParam int pagenum, @RequestParam int pagesize) {
        return service.getSearchCommunity(category, title, content, nick, sort, pagenum, pagesize);
    }

    @GetMapping("/{comNo}")
    public ResponseEntity<?> getCommunity(@PathVariable Long comNo, @RequestParam int pagenum, @RequestParam int pagesize) {
        service.communityViewUpdate(comNo);
        return service.getCommunity(comNo, pagenum, pagesize);
    }

    @PatchMapping("/{comNo}")
    public ResponseEntity<?> updateCommunity(@PathVariable Long comNo, @RequestPart(value = "data") CommunityRequestDto requestDto,
                                             @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return service.communityUpdate(requestDto, comNo, multipartFiles);
    }

    @DeleteMapping("/{comNo}")
    public ResponseEntity<?> deleteCommunity(@PathVariable Long comNo) {
        return service.communityDelete(comNo);
    }

    @PostMapping("/review/create/{comNo}")
    public ResponseEntity<?> createReview(@RequestPart(value = "data") String content, @PathVariable Long comNo) {
        return communityReviewService.createCommunityReview(comNo, content);
    }

    @PatchMapping("/review/{comNo}/{reviewNo}")
    public ResponseEntity<?> updateCommunityReview(@RequestPart(value = "data") String content, @PathVariable Long comNo,
                                                   @PathVariable Long reviewNo) {
        return communityReviewService.updateCommunityReview(comNo, reviewNo, content);
    }

    @DeleteMapping("/review/{comNo}/{reviewNo}")
    public ResponseEntity<?> deleteCommunityReview(@PathVariable Long comNo, @PathVariable Long reviewNo) {
        return communityReviewService.deleteCommunityReview(comNo, reviewNo);
    }
}
