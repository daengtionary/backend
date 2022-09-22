package com.sparta.daengtionary.category.trade.controller;

import com.sparta.daengtionary.category.trade.dto.request.TradeRequestDto;
import com.sparta.daengtionary.category.trade.service.TradeReviewService;
import com.sparta.daengtionary.category.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;
    private final TradeReviewService tradeReviewService;

    @PostMapping("/create")
    public ResponseEntity<?> createTrade(@RequestPart(value = "data") TradeRequestDto requestDto,
                                         @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return tradeService.createTrade(requestDto, multipartFiles);
    }

    @GetMapping()
    public ResponseEntity<?> getTradeSort(@RequestParam String direction, Pageable pageable) {
        return tradeService.getTradeSort(direction, pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearchTrade(@RequestParam String title, @RequestParam String content, @RequestParam String nick,
                                            @RequestParam String address, @RequestParam String postStatus,
                                            @RequestParam String direction, Pageable pageable) {
        return tradeService.getSearchTrade(title, content, nick, address, postStatus, direction, pageable);
    }


    @GetMapping("/{tradeNo}")
    public ResponseEntity<?> getTrade(@PathVariable Long tradeNo) {
        tradeService.tradeViewUpdate(tradeNo);
        return tradeService.getTrade(tradeNo);
    }

    @PatchMapping("/{tradeNo}")
    public ResponseEntity<?> updateTrade(@PathVariable Long tradeNo, @RequestPart(value = "data") TradeRequestDto requestDto,
                                         @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return tradeService.tradeUpdate(requestDto, tradeNo, multipartFiles);
    }

    @DeleteMapping("/{tradeNo}")
    public ResponseEntity<?> deleteTrade(@PathVariable Long tradeNo) {
        return tradeService.tradeDelete(tradeNo);
    }

    @PostMapping("/review/create/{tradeNo}")
    public ResponseEntity<?> createReview(@RequestPart(value = "data") String content, @PathVariable Long tradeNo) {
        return tradeReviewService.createTradeReview(tradeNo, content);
    }

    @PatchMapping("/review/{tradeNo}/{reviewNo}")
    public ResponseEntity<?> updateTradeReview(@RequestPart(value = "data") String content, @PathVariable Long tradeNo,
                                               @PathVariable Long reviewNo) {
        return tradeReviewService.updateTradeReview(tradeNo, reviewNo, content);
    }

    @DeleteMapping("/review/{tradeNo}/{reviewNo}")
    public ResponseEntity<?> deleteTradeReview(@PathVariable Long tradeNo, @PathVariable Long reviewNo) {
        return tradeReviewService.deleteTradeReview(tradeNo, reviewNo);
    }

}