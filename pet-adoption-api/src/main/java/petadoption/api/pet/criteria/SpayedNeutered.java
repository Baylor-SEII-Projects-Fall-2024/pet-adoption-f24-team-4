package petadoption.api.pet.criteria;

import lombok.Getter;

public enum SpayedNeutered {
    SPAYED_NEUTERED("Spayed Neutered"),
    NOT_SPAYED_NEUTERED("Not Spayed Neutered"),;

    @Getter
    private final String displayName;

    SpayedNeutered(String displayName) {
        this.displayName = displayName;
    }
}
