package petadoption.api.endpoint;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petadoption.api.adoptionCenter.AdoptionCenterService;
import petadoption.api.message.UserMessage;
import petadoption.api.message.UserMessageService;
import petadoption.api.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/user-messages")
public class UserMessageEndpoint {

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private UserService userService;
    @Autowired
    private AdoptionCenterService adoptionCenterService;

    @GetMapping
    public ResponseEntity<List<UserMessage>> getAllMessages() {
        return ResponseEntity.ok(userMessageService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody UserMessage message) {
        UserMessage createdMessage = userMessageService.saveMessage(message);
        return ResponseEntity.ok(createdMessage);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id) {
        userMessageService.deleteById(id);
    }

    @GetMapping("/adoption-center/{centerId}/user/{userId}")
    public ResponseEntity<List<UserMessage>> getMessagesBetweenCenterAndUser(@PathVariable Long centerId, @PathVariable Long userId) {
        List<UserMessage> messages = userMessageService.findMessagesBetweenCenterAndUser(centerId, userId);
        return ResponseEntity.ok(messages);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getMessagesByUser(@PathVariable Long userId) {
        List<UserMessage> messages = userMessageService.findMessagesByUser(userId);
        return ResponseEntity.ok(messages);
    }
    @GetMapping("/adoption-center/{centerId}")
    public ResponseEntity<List<UserMessage>> getMessagesForCenter(@PathVariable Long centerId) {
        List<UserMessage> messages = userMessageService.findMessagesByCenterId(centerId);
        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(messages);
    }

}