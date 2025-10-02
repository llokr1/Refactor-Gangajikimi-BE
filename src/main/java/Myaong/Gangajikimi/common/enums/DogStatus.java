package Myaong.Gangajikimi.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DogStatus {
    MISSING("실종"),
    SIGHTED("목격"),
    RETURNED("귀가완료");

    private final String description;
}
