package petadoption.api.pet.criteria.breed;

import lombok.Getter;

public enum CatBreed implements AnimalBreed {
    // For unsure / unknown breed
    MIX_BREED("Mix Breed"),

    // "A"
    ABYSSINIAN("Abyssinian"),
    AMERICAN_BOBTAIL("American Bobtail"),
    AMERICAN_CURL("American Curl"),
    AMERICAN_SHORTHAIR("American Shorthair"),
    AMERICAN_WIREHAIR("American Wirehair"),
    AUSTRALIAN_MIST("Australian Mist"),

    // "B"
    BALINESE("Balinese"),
    BENGAL("Bengal"),
    BIRMAN("Birman"),
    BOMBAY("Bombay"),
    BRITISH_LONGHAIR("British Longhair"),
    BRITISH_SHORTHAIR("British Shorthair"),
    BURMESE("Burmese"),
    BURMILLA("Burmilla"),

    // "C"
    CHARTREUX("Chartreux"),
    CHAUSIE("Chausie"),
    COLORPOINT_SHORTHAIR("Colorpoint Shorthair"),
    CORNISH_REX("Cornish Rex"),

    // "D"
    DEVON_REX("Devon Rex"),
    DONSKOY("Donskoy"),
    DOMESTIC_SHORTHAIR("Domestic Shorthair"),
    DOMESTIC_LONGHAIR("Domestic Longhair"),

    // "E"
    EGYPTIAN_MAU("Egyptian Mau"),
    EUROPEAN_BURMERSE("European Burmerse"),
    EXOTIC_SHORTHAIR("Exotic Shorthair"),  // Actual breed, not a descriptor

    // "H"
    HAVANA_BROWN("Havana Brown"),
    HIMALAYAN("Himalayan"),

    // "J"
    JAPANESE_BOBTAIL("Japanese Bobtail"),
    JAVANESE("Javanese"),

    // "K"
    KHAO_MANEE("Khao Manee"),
    KORAT("Korat"),
    KURILIAN("Kurilian"),

    // "L"
    LAPERM("LaPerm"),
    LYKOI("Lykoi"),

    // "M"
    MAINE_COON("Maine Coon"),
    MANX("Manx"),

    // "N"
    NEBELUNG("Nebelung"),
    NORWEGIAN_FOREST_CAT("Norwegian Forest Cat"),

    // "O"
    OCICAT("Ocicat"),
    ORIENTAL("Oriental"),

    // "P"
    PERSIAN("Persian"),
    PETERBALD("Peterbald"),
    PIXIE_BOB("Pixie-Bob"),

    // "R"
    RAGAMUFFIN("RagaMuffin"),
    RAGDOLL("Ragdoll"),
    RUSSIAN_BLUE("Russian Blue"),

    // "S"
    SAVANNAH("Savannah"),
    SCOTTISH_FOLD("Scottish Fold"),
    SELKIRK_REX("Selkirk Rex"),
    SIAMESE("Siamese"),
    SIBERIAN("Siberian"),
    SINGAPURA("Singapura"),
    SNOWSHOE("Snowshoe"),
    SOMALI("Somali"),
    SPHYNX("Sphynx"),

    // "T"
    TONKINESE("Tonkinese"),
    TOYBOB("Toybob"),
    TURKISH_ANGORA("Turkish Angora"),
    TURKISH_VAN("Turkish Van"),
    TOYGER("Toyger"),
    TUXEDO("TUXEDO");


    @Getter
    private final String breedName;

    CatBreed(String breedName) {
        this.breedName = breedName;
    }

    @Override
    public String getAnimalType() {
        return "Cat";
    }

    public String getDisplayName() {
        return breedName;
    }
}
