package Myaong.Gangajikimi.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PageResponse {
    
    private List<HomePostResponse> content;
    private boolean hasNext;

    @Builder
    private PageResponse(List<HomePostResponse> content, boolean hasNext) {
        this.content = content;
        this.hasNext = hasNext;
    }

    public static PageResponse of(List<HomePostResponse> content, boolean hasNext) {
        return PageResponse.builder()
                .content(content)
                .hasNext(hasNext)
                .build();
    }

}

