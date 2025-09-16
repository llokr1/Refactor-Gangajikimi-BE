package Myaong.Gangajikimi.postlost.web.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostLostPostResponse {

    Long postId;
    String memberName;
    String postTitle;

    @Builder
    private PostLostPostResponse(Long postId, String memberName, String postTitle) {
        this.postId = postId;
        this.memberName = memberName;
        this.postTitle = postTitle;
    }

    public static PostLostPostResponse of(Long postId, String memberName, String postTitle) {
        return PostLostPostResponse.builder()
                .postId(postId)
                .memberName(memberName)
                .postTitle(postTitle)
                .build();
    }
}
