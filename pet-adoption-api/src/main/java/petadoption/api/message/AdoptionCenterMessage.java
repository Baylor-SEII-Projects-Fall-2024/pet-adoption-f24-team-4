package petadoption.api.message;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import petadoption.api.adoptionCenter.AdoptionCenter;
import petadoption.api.user.User;

@Data
@Entity
@Table(name = UserMessage.TABLE_NAME)
public class AdoptionCenterMessage {
    public static final String TABLE_NAME = "AdoptionMessage";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MessageID")
    Long id;

    @Setter
    @Getter
    @Column(name = "content")
    String content;

    @Getter
    @Setter
    @Column(name = "receiverID")
    int receiverID;

    @Getter
    @Setter
    @Column(name = "senderID")
    int senderID;
}