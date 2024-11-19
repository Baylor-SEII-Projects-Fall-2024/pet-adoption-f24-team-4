import React, { useEffect, useState } from 'react';
import { keyframes } from '@emotion/react';
import { Stack, Typography, Button, Box, Card, CardMedia, CardActions, Drawer, Snackbar, Backdrop, LinearProgress, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';
import { useRouter } from 'next/router';
import NavBar from '@/components/NavBar';
import LikeDislikeButtons from '@/components/likeDislikeButtons';
import { format } from 'date-fns';

// Keyframe animations for sliding left and right
const slideRight = keyframes`
  0% { transform: translateX(0); opacity: 1; }
  100% { transform: translateX(100%); opacity: 0; }
`;

const slideLeft = keyframes`
  0% { transform: translateX(0); opacity: 1; }
  100% { transform: translateX(-100%); opacity: 0; }
`;

const fadeIn = keyframes`
  0% { transform: translateX(50%); opacity: 0; }
  100% { transform: translateX(0); opacity: 1; }
`;

export default function RecommendationEnginePage() {
  const router = useRouter();
  const { userID, email } = router.query;
  const [userEmail, setUserEmail] = useState(null);
  const [loading, setLoading] = useState(true);
  const [profilePicture, setProfilePicture] = useState(null);
  const [openDialog, setOpenDialog] = useState(false);
  const [profilePictureFile, setProfilePictureFile] = useState(null);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [user, setUser] = useState(null);
  const [isLiked, setIsLiked] = useState(null);
  const [error, setError] = useState(null);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [animationDirection, setAnimationDirection] = useState(null);
  const [showBackdrop, setShowBackdrop] = useState(false);
  const [allPets, setAllPets] = useState([]);
  const [allPetDetails, setAllPetDetails] = useState([]);
  const [progressValue, setProgressValue] = useState(0); // Progress value from 0 to 10
  const [nextRoundDialogOpen, setNextRoundDialogOpen] = useState(false); // Dialog state for next round
  const petsPerRound = 10;
  const apiUrl = process.env.NEXT_PUBLIC_API_URL;

  useEffect(() => {
    const fetchPets = async () => {
      try {
        const token = localStorage.getItem('token');
        const petsRes = await fetch(`${apiUrl}/RecEng/getSampleDefault`, {
          headers: { 'Authorization': `Bearer ${token}` },
        });

        if (!petsRes.ok) throw new Error('Failed to fetch pet data');

        const petsData = await petsRes.json();
        const formattedPets = petsData.map(pet => {
          const imageUrl = pet.profilePicture?.imageData 
            ? `data:image/png;base64,${pet.profilePicture.imageData}` 
            : '/petImages/DEFAULT_IMG-612x612.jpg'; // Fallback to default image if no profilePicture
          return {
            ...pet,
            name: formatOption(pet.name),
            species: formatOption(pet.species),
            coatLength: formatOption(pet.coatLength),
            furType: formatOption(pet.furType),
            petSize: formatOption(pet.petSize),
            healthStatus: formatOption(pet.healthStatus),
            sex: formatOption(pet.sex),
            furColor: pet.furColor.map(color => formatOption(color)),
            temperament: pet.temperament.map(temp => formatOption(temp)),
            dogBreed: pet.dogBreed ? pet.dogBreed.map(breed => formatOption(breed)) : [],
            catBreed: pet.catBreed ? pet.catBreed.map(breed => formatOption(breed)) : [],
            profilePictureUrl: imageUrl,
            adoptionCenter: pet.center.centerName,
          };
        });
        console.log("Pets:", formattedPets);

        setAllPets(formattedPets.map(pet => pet.profilePictureUrl));
        setAllPetDetails(formattedPets);
      } catch (error) {
        console.error('Error fetching pets:', error);
        setError("Failed to load pets.");
      } finally {
        setLoading(false);
      }
    };

    fetchPets();
  }, [apiUrl]);

   useEffect(() => {
    return () => {
      allPets.forEach(url => URL.revokeObjectURL(url));
    };
  }, [allPets]);

  const formatOption = (str) => {
    if (!str) return '';
    return str
        .toLowerCase()
        .split('_')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(' ');
  };

  const fetchRate = async () => {
    // Get the pet ID of the currently viewed pet
    const petID = allPetDetails[currentIndex]?.id;
    
    // Check if petID is available
    if (!petID) {
      console.error('No pet ID found for the current pet.');
      return;
    }
    
    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`${apiUrl}/RecEng/ratePet`, {
        method: 'PUT', // Ensure that the method matches your API
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          userId: Number(userID),
          petId: Number(petID),
          like: Boolean(isLiked),
        }),
      });

      if (!response.ok) {
        throw new Error(`Failed to fetch adoption rate. Status: ${response.status}`);
      }

      const data = await response.json();
      console.log("Adoption data fetched successfully:", data);
    } catch (error) {
      console.error('Error during adoption rate fetch:', error);
    }
  };
  
  const addLikedPet = async () => {
    // Get the pet ID of the currently viewed pet
    const petID = allPetDetails[currentIndex]?.id;
  
    // Check if petID is available
    if (!petID) {
      console.error('No pet ID found for the current pet.');
      return;
    }
  
    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`${apiUrl}/likedPets/addPet`, {
        method: 'POST', // PUT request to add the pet to likePetRepository
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          userId: Number(userID),
          petId: Number(petID),
        }),
      });
  
      if (!response.ok) {
        throw new Error(`Failed to add pet to likePetRepository. Status: ${response.status}`);
      }
  
      const data = await response.json();
      console.log("Pet added to likePetRepository successfully:", data);
    } catch (error) {
      console.error('Error adding pet to likePetRepository:', error);
    }
  };
  
  // Function to handle yes or no button click
  const handleYes = () => {
    setIsLiked(true);
    addLikedPet();
    fetchRate();
    setAnimationDirection('left');
    setSnackbarOpen(true);
    setTimeout(() => {
      incrementProgress();
      handleNextPet();
    }, 500);
  };

  const handleNo = () => {
    setIsLiked(false);
    fetchRate();
    setAnimationDirection('right');
    setSnackbarOpen(true);
    setTimeout(() => {
      incrementProgress();
      handleNextPet();
    }, 500);
  };

  // Increment the progress value
  const incrementProgress = () => {
    setProgressValue((prevValue) => {
      const newValue = Math.min(prevValue + 1, 10);
      if (newValue === 10) {
        setNextRoundDialogOpen(true);
      }
      return newValue;
    });
  };

  const handleNextPet = () => {
    setAnimationDirection(null);
    setCurrentIndex((prevIdx) => (prevIdx + 1) % allPets.length);
  };

  const handleDialogClose = () => {
    setNextRoundDialogOpen(false);
  };

  const handleStartNextRound = () => {
    setProgressValue(0);
    setNextRoundDialogOpen(false);
  };

  // Function to fetch user data
  const fetchUserData = async (email) => {
    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`${apiUrl}/users/email/${email}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });
      if (!response.ok) throw new Error('Failed to fetch user data');
      const data = await response.json();
      setUser(data);
      if (data.profilePicture && data.profilePicture.imageData) {
        setProfilePicture(`data:image/png;base64,${data.profilePicture.imageData}`);
      }
    } catch (error) {
      console.error('Error fetching user:', error);
    }
  };

  const handleAdopt = () => {
    // Get the pet ID of the currently viewed pet
    const petID = allPetDetails[currentIndex]?.id;
    
    // Check if petID is available
    if (!petID) {
      console.error('No pet ID found for the current pet.');
      return;
    }
  
    setShowBackdrop(true);
  
    // Function to handle the adoption rate request
    const fetchAdoptRate = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${apiUrl}/RecEng/rateAdoptedPet`, {
          method: 'PUT', // Ensure that the method matches your API
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            userId: Number(userID),
            petId: Number(petID),
          }),
        });
  
        if (!response.ok) {
          throw new Error(`Failed to fetch adoption rate. Status: ${response.status}`);
        }
  
        const data = await response.json();
        console.log("Adoption data fetched successfully:", data);
      } catch (error) {
        console.error('Error during adoption rate fetch:', error);
      }
    };
  
    addLikedPet();
    fetchAdoptRate();
  
    setTimeout(() => {
      setShowBackdrop(false);
      router.push(`/message?email=${email}&userID=${userID}`);
    }, 1000);
  };
  

  return (
    <main>
      <NavBar />

      <Backdrop
        sx={{ backgroundColor: 'rgba(0,0,0,0.8)', color: 'green', zIndex: (theme) => theme.zIndex.drawer + 1 }}
        open={showBackdrop}
      >
        <Typography variant="h3" color="primary">Congratulations on choosing to adopt!</Typography>
      </Backdrop>

      <Stack sx={{ paddingTop: 2 }} alignItems="center" gap={2}>
        <Typography variant="h3">Start Matching!</Typography>
        <Box sx={{ width: '100%', maxWidth: 600 }}>
          <LinearProgress variant="determinate" value={(progressValue / 10) * 100} sx={{ marginBottom: 2 }} />
        </Box>
        <Box sx={{ display: 'flex', justifyContent: 'center', padding: 1 }}>
          <Card
            key={currentIndex}
            sx={{
              maxWidth: 600,
              animation: animationDirection === 'left' ? `${slideLeft} 0.5s forwards` : animationDirection === 'right' ? `${slideRight} 0.5s forwards` : `${fadeIn} 0.5s ease-in-out`
            }}
          >
            <CardMedia
              component="img"
              alt="Pet Image"
              height="500"
              width="400"
              src={allPets[currentIndex] || '/petImages/DEFAULT_IMG-612x612.jpg'} // Replace with real image URL
              sx={{ objectFit: 'cover' }}
            />
            <CardActions sx={{ justifyContent: 'space-between' }}>
              <LikeDislikeButtons handleLike={handleYes} handleDislike={handleNo} handleAdopt={handleAdopt} />
            </CardActions>

            <Box sx={{ padding: 3 }}>
            <Typography variant="body1" sx={{ textAlign: 'center', color: '#555' }}>
                <strong>Adoption Center:</strong> {allPetDetails[currentIndex]?.adoptionCenter}
              </Typography>
              <Typography variant="body1" sx={{ textAlign: 'center', color: '#555' }}>
                <strong>Name:</strong> {allPetDetails[currentIndex]?.name}
              </Typography>
              <Typography variant="body1" sx={{ textAlign: 'center', color: '#555' }}>
                <strong>Species:</strong> {allPetDetails[currentIndex]?.species}
              </Typography>
              <Typography variant="body1" sx={{ textAlign: 'center', color: '#555' }}>
                <strong>Breed:</strong> {allPetDetails[currentIndex]?.species === 'Dog' ? allPetDetails[currentIndex]?.dogBreed?.join(', ') : allPetDetails[currentIndex]?.catBreed?.join(', ')}
              </Typography>
              <Typography variant="body1" sx={{ textAlign: 'center', color: '#555' }}>
                <strong>Coat Length:</strong> {allPetDetails[currentIndex]?.coatLength}
              </Typography>
              <Typography variant="body1" sx={{ textAlign: 'center', color: '#555' }}>
                <strong>Fur Type:</strong> {allPetDetails[currentIndex]?.furType}
              </Typography>
              <Typography variant="body1" sx={{ textAlign: 'center', color: '#555' }}>
                <strong>Pet Size:</strong> {allPetDetails[currentIndex]?.petSize}
              </Typography>
              <Typography variant="body1" sx={{ textAlign: 'center', color: '#555' }}>
                <strong>Health Status:</strong> {allPetDetails[currentIndex]?.healthStatus}
              </Typography>
              <Typography variant="body1" sx={{ textAlign: 'center', color: '#555' }}>
                <strong>Temperament:</strong> {allPetDetails[currentIndex]?.temperament}
              </Typography>
            </Box>
          </Card>
        </Box>

        <Snackbar
          open={snackbarOpen}
          onClose={() => setSnackbarOpen(false)}
          autoHideDuration={2000}
          message={isLiked ? "You liked this pet!" : "You disliked this pet."}
        />

        <Dialog open={nextRoundDialogOpen} onClose={handleDialogClose}>
          <DialogTitle>End of Round</DialogTitle>
          <DialogContent>
            <Typography>You've reached the end of this round of pet matching. Would you like to start a new round or view your liked pets?</Typography>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleDialogClose}>Cancel</Button>
            <Button onClick={handleStartNextRound} color="primary">Next Round</Button>
            <Button onClick={() => router.push(`/viewUserPets?email=${email}&userID=${userID}`)} color="secondary">View Liked Pets</Button>
          </DialogActions>
        </Dialog>
      </Stack>
    </main>
  );
}
