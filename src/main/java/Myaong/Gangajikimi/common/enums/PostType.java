package Myaong.Gangajikimi.common.enums;

public enum PostType {
    LOST("lost"),
    FOUND("found");
    
    private final String value;
    
    PostType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}

