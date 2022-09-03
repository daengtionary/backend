package com.sparta.daengtionary.service;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.*;
import com.sparta.daengtionary.dto.request.MapPutRequestDto;
import com.sparta.daengtionary.dto.request.MapRequestDto;
import com.sparta.daengtionary.dto.response.MapDetailResponseDto;
import com.sparta.daengtionary.dto.response.MapResponseDto;
import com.sparta.daengtionary.dto.response.MemberResponseDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.repository.MapImgRepository;
import com.sparta.daengtionary.repository.MapInfoRepository;
import com.sparta.daengtionary.repository.MapRepository;
import com.sparta.daengtionary.repository.MemberRepository;
import com.sparta.daengtionary.repository.supportRepository.MapRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapService {
    private final MapRepository mapRepository;
    private final MapInfoRepository mapInfoRepository;
    private final MapImgRepository mapImgRepository;
    private final MemberRepository memberRepository;
    private final ResponseBodyDto responseBodyDto;

    private final MapRepositorySupport mapRepositorySupport;

    private final AwsS3UploadService s3UploadService;

    @Transactional
    public ResponseEntity<?> createMap(MapRequestDto mapRequestDto, List<MultipartFile> multipartFiles) {
        Member member = validateMember(mapRequestDto.getMemberNo());
        validateFile(multipartFiles);
        List<String> mapImgs = s3UploadService.upload(multipartFiles);

        Map map = Map.builder()
                .member(member)
                .title(mapRequestDto.getTitle())
                .content(mapRequestDto.getContent())
                .category(mapRequestDto.getCategory())
                .address(mapRequestDto.getAddress())
                .build();

        mapRepository.save(map);

        List<MapInfo> mapInfos = new ArrayList<>();

        for (String mapinfo : mapRequestDto.getMapInfos()) {
            mapInfos.add(
                    MapInfo.builder()
                            .map(map)
                            .mapInfo(mapinfo)
                            .build()
            );
        }
        mapInfoRepository.saveAll(mapInfos);

        List<MapImg> mapImgList = new ArrayList<>();
        for (String img : mapImgs) {
            mapImgList.add(
                    MapImg.builder()
                            .map(map)
                            .mapImgUrl(img)
                            .build()
            );
        }
        mapImgRepository.saveAll(mapImgList);

        return responseBodyDto.success(
                MapDetailResponseDto.builder()
                        .mapNo(map.getMapNo())
                        .category(map.getCategory())
                        .title(map.getTitle())
                        .address(map.getAddress())
                        .mapInfo(mapRequestDto.getMapInfos())
                        .imgUrls(mapImgs)
                        .createdAt(map.getCreatedAt())
                        .moditiedAt(map.getModifiedAt())
                        .build(),
                "생성 완료"
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllMapByCategory(String category, String orderBy, Pageable pageable) {
        if (orderBy.equals("popular")) {
            PageImpl<MapResponseDto> mapResponseDtoPage = mapRepositorySupport.findAllByMapByPopular(category, pageable);
            return responseBodyDto.success(mapResponseDtoPage, "조회 완료");
        }

        PageImpl<MapResponseDto> mapResponseDtoPage = mapRepositorySupport.findAllByMap(category, pageable);
        return responseBodyDto.success(mapResponseDtoPage, "조회 완료");

    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllMap(Long mapNo) {
        Map map = validateMap(mapNo);

        List<MapImg> mapImgTemp = mapImgRepository.findAllByMap(map);
        List<String> mapImgs = new ArrayList<>();

        for (MapImg i : mapImgTemp) {
            mapImgs.add(i.getMapImgUrl());
        }

        List<MapInfo> mapInfoTemp = mapInfoRepository.findAllByMap(map);
        List<String> infoList = new ArrayList<>();

        for (MapInfo i : mapInfoTemp) {
            infoList.add(i.getMapInfo());
        }

        return responseBodyDto.success(
                MapDetailResponseDto.builder()
                        .mapNo(map.getMapNo())
                        .member(
                                MemberResponseDto.builder()
                                        .memberNo(map.getMember().getMemberNo())
                                        .email(map.getMember().getEmail())
                                        .role(map.getMember().getRole())
                                        .nick(map.getMember().getNick())
                                        .build()
                        )
                        .title(map.getTitle())
                        .address(map.getAddress())
                        .category(map.getCategory())
                        .content(map.getContent())
                        .star(map.getStar())
                        .view(map.getView())
                        .imgUrls(mapImgs)
                        .mapInfo(infoList)
                        .createdAt(map.getCreatedAt())
                        .moditiedAt(map.getModifiedAt())
                        .build(),
                "조회 성공"
        );
    }

    @Transactional
    public ResponseEntity<?> mapUpdate(MapPutRequestDto requestDto, Long mapNo, List<MultipartFile> multipartFiles) {
        Member member = validateMember(requestDto.getMemberNo());
        Map map = validateMap(mapNo);
        map.validateMember(member);
        validateFile(multipartFiles);


        List<MapInfo> mapInfos = new ArrayList<>();

        for (String mapinfo : requestDto.getMapInfos()) {
            mapInfos.add(
                    MapInfo.builder()
                            .map(map)
                            .mapInfo(mapinfo)
                            .build()
            );
        }
        List<MapInfo> infoDelete = mapInfoRepository.findAllByMap(map);
        mapInfoRepository.deleteAll(infoDelete);
        mapInfoRepository.saveAll(mapInfos);


        List<MapImg> temp = mapImgRepository.findAllByMap(map);
        for (MapImg i : temp) {
            s3UploadService.deleteFile(i.getMapImgUrl());
        }
        mapImgRepository.deleteAll(temp);

        List<String> mapImgs = s3UploadService.upload(multipartFiles);

        List<MapImg> mapImgList = new ArrayList<>();
        for (String img : mapImgs) {
            mapImgList.add(
                    MapImg.builder()
                            .map(map)
                            .mapImgUrl(img)
                            .build()
            );
        }
        mapImgRepository.saveAll(mapImgList);

        map.updateMap(requestDto);

        return responseBodyDto.success(MapDetailResponseDto.builder()
                        .mapNo(map.getMapNo())
                        .member(
                                MemberResponseDto.builder()
                                        .memberNo(map.getMember().getMemberNo())
                                        .email(map.getMember().getEmail())
                                        .role(map.getMember().getRole())
                                        .nick(map.getMember().getNick())
                                        .build()
                        )
                        .title(map.getTitle())
                        .address(map.getAddress())
                        .category(map.getCategory())
                        .content(map.getContent())
                        .star(map.getStar())
                        .view(map.getView())
                        .imgUrls(mapImgs)
                        .mapInfo(requestDto.getMapInfos())
                        .createdAt(map.getCreatedAt())
                        .moditiedAt(map.getModifiedAt())
                        .build(),

                "수정 성공"
        );
    }

    @Transactional
    public ResponseEntity<?> mapDelete(Long mapNo, Long memberNo) {
        Member member = validateMember(memberNo);
        Map map = validateMap(mapNo);
        map.validateMember(member);
        List<MapInfo> infoDelete = mapInfoRepository.findAllByMap(map);
        mapInfoRepository.deleteAll(infoDelete);
        List<MapImg> imgDelete = mapImgRepository.findAllByMap(map);
        mapImgRepository.deleteAll(imgDelete);
        mapRepository.delete(map);

        return responseBodyDto.success("삭제 완료");
    }

    private void validateFile(List<MultipartFile> multipartFiles) {
        if (multipartFiles == null) {
            throw new CustomException(ErrorCode.WRONG_INPUT_CONTENT);
        }
    }


    @Transactional(readOnly = true)
    public void isDuplicateCheck(String title, String address) {
        if (mapRepository.existsByTitle(title) && mapRepository.existsByAddress(address)) {
            throw new CustomException(ErrorCode.MAP_DUPLICATE_TITLE);
        }
    }

    @Transactional(readOnly = true)
    public Map validateMap(Long mapNo) {
        return mapRepository.findById(mapNo).orElseThrow(
                () -> new CustomException(ErrorCode.MAP_NOT_FOUND)
        );
    }

    private Member validateMember(Long memberNo) {
        return memberRepository.findById(memberNo).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER_INFO)
        );
    }
}