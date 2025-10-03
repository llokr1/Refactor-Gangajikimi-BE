package Myaong.Gangajikimi.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportType {
    FAKE("허위/장난 제보"),
    SPAM("스팸/홍보/도배"),
    OFFENSIVE("불쾌한 표현"),
    INAPPROPRIATE("부적절한 내용"),
    COPYRIGHT("무단 사용");

    private final String description;
}

