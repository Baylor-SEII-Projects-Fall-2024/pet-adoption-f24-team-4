package petadoption.api.pet.criteria;

import lombok.Getter;


public enum FurType {
    SMOOTH("Smooth"),
    SILKY("Silky"),
    CURLY("Curly"),
    ROUGH("Rough"),
    WIRY("Wiry"),
    DOUBLE("Double"),
    WAVY("Wavy"),
    CORDED("Corded"),
    ;
    @Getter
    private final String displayName;

    FurType(String displayName) {
        this.displayName = displayName;
    }
}
