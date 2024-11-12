package petadoption.api.pet.criteria;

import lombok.Getter;

public enum Sex {
    MALE("Male"),
    FEMALE("Female");

    @Getter
    private final String displayName;

    Sex(String displayName) {
        this.displayName = displayName;
    }
}
