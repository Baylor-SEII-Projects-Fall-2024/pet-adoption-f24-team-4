package petadoption.api.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petadoption.api.pet.Pet;
import petadoption.api.pet.PetRepository;
import petadoption.api.user.User;
import petadoption.api.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MeetingService {

    @Autowired
    MeetingRepository repository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    UserRepository userRepository;


    public Optional<Meeting> getMeetingById(long meetingID){
        return repository.findById(meetingID);
    }

    public Meeting saveMeeting(Meeting meeting, Long petID, Long userID) {
        if(petID != null && userID != null) {
            Optional<Pet> pets = petRepository.findById(petID);
            if (pets.isPresent()) {
                Optional<User> users = userRepository.findById(userID);
                if (users.isPresent()) {
                    meeting.setPet(pets.get());
                    meeting.setUser(users.get());
                } else {
                    throw new RuntimeException("User was not found.");
                }
            } else {
                throw new RuntimeException("Pet was not found.");
            }

        }
        return repository.save(meeting);
    }

    public List<Meeting> getAllMeetings() { return repository.findAll(); }

    public List<Meeting> getAdoptionCenterMeetings(long adoptionID) {

        List<Pet> pets = petRepository.findByCenter_adoptionID(adoptionID);

        List<Meeting> meetings = new ArrayList<>();
        for(Pet pet : pets){
            meetings.add(repository.findByPet_id(pet.getId()));
        }
        return meetings;
    }

    public List<Meeting> getUserMeetings(long userID) {
        return repository.findByUser_id(userID);
    }

    public void deleteMeeting(Long id) { repository.deleteById(id); }
}
