package petadoption.api.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petadoption.api.pet.Pet;
import petadoption.api.pet.PetRepository;
import petadoption.api.user.User;
import petadoption.api.user.UserRepository;

import java.util.ArrayList;
import java.util.Date;
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

    public Meeting createAndSaveMeeting(Long petID, Long userID, Date date) throws Exception {
        Optional<Pet> pet = petRepository.findById(petID);
        Optional<User> user = userRepository.findById(userID);
        if(pet.isPresent()){
            if(user.isPresent()){
                Meeting meeting = new Meeting(pet.get(), user.get(), date);
                saveMeeting(meeting);
                return meeting;
            }
            else{
                throw new Exception("Unable to find user.");
            }
        }
        else{
            throw new Exception("Unable to find pet.");
        }
    }


    public Meeting saveMeeting(Meeting meeting) {
        repository.save(meeting);
        return meeting;
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
