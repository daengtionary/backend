package com.sparta.daengtionary.category.friend.service;

import com.sparta.daengtionary.aop.amazon.AwsS3UploadService;
import com.sparta.daengtionary.aop.dto.ResponseBodyDto;
import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.aop.jwt.TokenProvider;
import com.sparta.daengtionary.aop.supportrepository.PostRepositorySupport;
import com.sparta.daengtionary.category.chat.domain.ChatRoom;
import com.sparta.daengtionary.category.chat.service.ChatRoomService;
import com.sparta.daengtionary.category.friend.domain.Friend;
import com.sparta.daengtionary.category.friend.domain.FriendImg;
import com.sparta.daengtionary.category.friend.dto.request.FriendRequestDto;
import com.sparta.daengtionary.category.friend.dto.response.FriendDetailResponseDto;
import com.sparta.daengtionary.category.friend.dto.response.FriendResponseDto;
import com.sparta.daengtionary.category.friend.repository.FriendImgRepository;
import com.sparta.daengtionary.category.friend.repository.FriendRepository;
import com.sparta.daengtionary.category.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final AwsS3UploadService s3UploadService;
    private final FriendImgRepository friendImgRepository;
    private final ChatRoomService chatRoomService;
    private final ResponseBodyDto responseBodyDto;
    private final TokenProvider tokenProvider;
    private final PostRepositorySupport postRepositorySupport;

    @Transactional
    public ResponseEntity<?> createFriend(FriendRequestDto requestDto,
                                          List<MultipartFile> multipartFiles) {
        Member member = tokenProvider.getMemberFromAuthentication();

        ChatRoom chatRoom = chatRoomService.createGroupChatRoom(member, requestDto.getTitle());

        Friend friend = Friend.builder()
                .member(member)
                .address(requestDto.getAddress())
                .category(requestDto.getCategory())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .maxCount(requestDto.getMaxCount())
                .roomNo(chatRoom.getRoomNo())
                .build();
        friendRepository.save(friend);

        if (multipartFiles != null) {
            if (multipartFiles.get(0).getSize() > 0) {
                List<String> friendImgList = s3UploadService.uploadListImg(multipartFiles);
                List<FriendImg> friendImgs = new ArrayList<>();
                for (String img : friendImgList) {
                    friendImgs.add(
                            FriendImg.builder()
                                    .friend(friend)
                                    .friendImg(img)
                                    .build()
                    );
                }

                friendImgRepository.saveAll(friendImgs);
            }
        }

        return responseBodyDto.success("?????? ??????");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllFriend(String category, String address, String content, String title, int pagenum, int pagesize) {
        List<FriendResponseDto> friendResponseDtos = postRepositorySupport.findAllFriend(category, address, content, title, pagenum, pagesize);

        return responseBodyDto.success(friendResponseDtos, "?????? ??????");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getFriend(Long friendNo) {
        Friend friend = findByFriend(friendNo);

        List<FriendImg> friendImgList = friendImgRepository.findAllByFriend(friend);

        return responseBodyDto.success(FriendDetailResponseDto.builder()
                        .friendNo(friend.getFriendNo())
                        .member(friend.getMember())
                        .address(friend.getAddress())
                        .category(friend.getCategory())
                        .title(friend.getTitle())
                        .content(friend.getContent())
                        .status(friend.getStatus())
                        .count(friend.getCount())
                        .maxCount(friend.getMaxCount())
                        .roomNo(friend.getRoomNo())
                        .images(friendImgList)
                        .createdAt(friend.getCreatedAt())
                        .modifiedAt(friend.getModifiedAt())
                        .build()
                , "?????? ??????"
        );
    }

    @Transactional
    public ResponseEntity<?> friendUpdate(Long friendNo, FriendRequestDto requestDto) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Friend friend = findByFriend(friendNo);
        friend.validateMember(member);

        friend.firendUpdate(requestDto);

        return responseBodyDto.success("?????? ??????");
    }

    @Transactional
    public ResponseEntity<?> friendDelete(Long friendNo) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Friend friend = findByFriend(friendNo);
        friend.validateMember(member);

        friendRepository.delete(friend);

        return responseBodyDto.success("?????? ??????");
    }

    @Transactional
    public ResponseEntity<?> friendNotOverCount(Long friendNo) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Friend friend = findByFriend(friendNo);


        if (friend.getStatus().equals("?????????")) {
            friend.NotOverCount(member);
            friend.finishCount();
            return responseBodyDto.success("?????? ??????");
        }

        return responseBodyDto.success("?????? ??????");
    }


    public Friend findByFriend(Long friendNo) {
        return friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new CustomException(ErrorCode.MAP_NOT_FOUND)
        );
    }
}