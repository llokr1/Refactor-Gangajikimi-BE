package Myaong.Gangajikimi.postfound.web.dto.response;

import Myaong.Gangajikimi.common.enums.DogStatus;
import Myaong.Gangajikimi.postfound.entity.PostFound;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostFoundResponse {

    Long postId;
    String memberName;
    String postTitle;
    LocalDateTime postDate;
    DogStatus dogStatus;

    @Builder
    private PostFoundResponse(Long postId, String memberName, String postTitle, LocalDateTime postDate, DogStatus dogStatus) {
        this.postId = postId;
        this.memberName = memberName;
        this.postTitle = postTitle;
        this.postDate = postDate;
        this.dogStatus = dogStatus;
    }

    public static PostFoundResponse of(Long postId, String memberName, String postTitle, LocalDateTime postDate, DogStatus dogStatus) {
        return PostFoundResponse.builder()
                .postId(postId)
                .memberName(memberName)
                .postTitle(postTitle)
                .postDate(postDate)
                .dogStatus(dogStatus)
                .build();
    }

    public static PostFoundResponse from(PostFound postFound){
        return PostFoundResponse.builder()
                .postId(postFound.getId())
                .memberName(postFound.getMember().getMemberName())
                .postTitle(postFound.getTitle())
                .postDate(postFound.getCreatedAt())
                .dogStatus(postFound.getStatus())
                .build();
    }


}

