package petadoption.api.pet.criteria;

import lombok.Getter;

public enum FurColor {
    WHITE("White"),
    BLACK("Black"),
    BROWN("Brown"),
    GRAY("Gray"),
    MIXED_COLOR("Mixed Color"),

    ALBINO("Albino"),
    ASH("Ash"),
    ISABELLA("Isabella"),
    WASHED_OUT("Washed-Out"),
    BANDING("Banding"),
    REDDISH_BROWN("Reddish-Brown"),
    BISCUIT("Biscuit"),
    SILVER("Silver"),
    BRINDLE("Brindle"),
    BLUE("Blue"),
    FAWN("Fawn"),
    ROAN("Roan"),
    BRONZE("Bronze"),
    CHARCOAL("Charcoal"),
    COPPER("Copper"),
    MERLE("Merle"),
    GINGER("Ginger"),
    IVORY("Ivory"),
    RED("Red"),
    LIGHT("Light"),
    DARK("Dark"),
    MUDDY("Muddy"),
    PARTI_COLOR("Parti-Color"),
    PLATINUM("Platinum"),
    SMOKY("Smoky"),
    GREY("Grey"),
    APRICOT("Apricot"),
    CHESTNUT("Chestnut"),
    CINNAMON("Cinnamon"),
    CREAM("Cream"),
    MAHOGANY("Mahogany"),
    ORANGE("Orange"),
    TAN("Tan"),
    SOLID("Solid"),
    MULTI_COLOR("Multi-Color"),
    TORTOISE("Tortoise"),
    BI_COLOR("Bi-Color"),
    TRI_COLOR("Tri-Color"),
    COLOR_POINT("Color-Point"),
    CHOCOLATE("Chocolate"),
    LILAC("Lilac"),
    SABLE("Sable"),
    YELLOW("Yellow"),
    NONE("None")
    ;


    @Getter
    private final String displayName;

    FurColor(String displayName) {
        this.displayName = displayName;
    }
}
