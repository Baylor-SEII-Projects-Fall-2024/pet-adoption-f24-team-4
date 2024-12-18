package petadoption.api.pet.criteria;

import lombok.Getter;

/**
 * @author Rafe Loya
 * @author Will Clore
 */
public enum Temperament {
    CHILL("Chill"),      // willclo1
    NEEDY("Needy"),      // willclo1
    AGGRESSIVE("Aggressive"), // willclo1
    ENERGETIC("Energetic"),  // willclo1
    FRIENDLY("Friendly"),
    BOLD("Bold"),
    LIVELY("Lively"),
    FOCUSED("Focused"),
    CURIOUS("Curious"),
    SKITTISH("Skittish"),
    TIMID("Timid"),
    PASSIVE("Passive"),
    CALM("Calm"),
    QUIET("Quiet"),
    ANXIOUS("Anxious"),
    SHY("Shy"),
    EXTROVERTED("Extroverted"),
    INTROVERTED("Introverted"),
    EXCITABLE("Excitable"),
    REACTIVE("Reactive"),
    IMPULSIVE("Impulsive"),
    DEMANDING("Demanding"),
    POSSESSIVE("Possessive"),
    INDEPENDENT("Independent"),
    DEPENDENT("Dependent"),
    PLAYFUL("Playful"),
    WILLFUL("Willful"),
    ACTIVE("Active"),
    AFFECTIONATE("Affectionate"),
    INTELLIGENT("Intelligent"),
    VOCAL("Vocal"),
    ATHLETIC("Athletic"),
    SWEET("Sweet"),
    SOCIABLE("Sociable"),
    EVEN_TEMPERED("Even Tempered"),
    ADAPTABLE("Adaptable"),
    INSISTENT("Insistent"),
    LOYAL("Loyal"),
    EASY_GOING("Easy Going"),
    DOCILE("Docile"),
    PLACID("Placid"),
    BOSSY("Bossy"),
    AGILE("Agile"),
    KID_FRIENDLY("Kid Friendly"),
    PET_FRIENDLY("Pet Friendly"),
    CAT_FRIENDLY("Cat Friendly"),
    DOG_FRIENDLY("Dog Friendly"),
    DOMINANT("Dominant"),
    SUBMISSIVE("Submissive"),
    EASILY_TRAINED("Easily Trained"),
    POTTY_TRAINED("Potty Trained"),
    CUDDLY("Cuddly"),
    ALOOF("Aloof"),
    GOOD_ON_LEASH("Good on Leash"),
    STRONG("Strong"),
    HYPER("Hyper"),
    ;

    @Getter
    private final String displayName;

    Temperament(String displayName) {
        this.displayName = displayName;
    }
}