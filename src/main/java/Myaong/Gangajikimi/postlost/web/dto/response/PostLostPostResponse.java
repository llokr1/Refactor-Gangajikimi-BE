package Myaong.Gangajikimi.postlost.web.dto.response;

import Myaong.Gangajikimi.postlost.entity.PostLost;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostLostPostResponse {

    Long postId;
    String memberName;
    String postTitle;
    LocalDateTime postDate;

    @Builder
    private PostLostPostResponse(Long postId, String memberName, String postTitle, LocalDateTime postDate) {
        this.postId = postId;
        this.memberName = memberName;
        this.postTitle = postTitle;
        this.postDate = postDate;
    }

    public static PostLostPostResponse of(Long postId, String memberName, String postTitle, LocalDateTime postDate) {
        return PostLostPostResponse.builder()
                .postId(postId)
                .memberName(memberName)
                .postTitle(postTitle)
                .postDate(postDate)
                .build();
    }

    public static PostLostPostResponse from(PostLost postLost) {
        return PostLostPostResponse.builder()
                .postId(postLost.getId())
                .memberName(postLost.getMember().getMemberName())
                .postTitle(postLost.getTitle())
                .build();
    }
}
