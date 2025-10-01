package Myaong.Gangajikimi.postfound.web.dto.response;

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

    @Builder
    private PostFoundResponse(Long postId, String memberName, String postTitle, LocalDateTime postDate) {
        this.postId = postId;
        this.memberName = memberName;
        this.postTitle = postTitle;
        this.postDate = postDate;
    }

    public static PostFoundResponse of(Long postId, String memberName, String postTitle, LocalDateTime postDate) {
        return PostFoundResponse.builder()
                .postId(postId)
                .memberName(memberName)
                .postTitle(postTitle)
                .postDate(postDate)
                .build();
    }

    public static PostFoundResponse from(PostFound postFound){
        return PostFoundResponse.builder()
                .postId(postFound.getId())
                .memberName(postFound.getMember().getMemberName())
                .postTitle(postFound.getTitle())
                .build();
    }


}

