import React, { useState } from 'react';

function AddMeeting() {
    const [petID, setPetID] = useState('');
    const [userID, setUserID] = useState('');
    const [date, setDate] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        const meetingData = {
            petID: petID,
            userID: userID,
            date: new Date(date),
        };

        try {
            const response = await fetch('/api/meetings', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(meetingData),
            });

            if (response.ok) {
                alert('Meeting created successfully!');
                // Clear form or redirect to meetings list
            } else {
                const error = await response.json();
                alert(`Error: ${error.message}`);
            }
        } catch (error) {
            console.error('Error creating meeting:', error);
            alert('Error creating meeting');
        }
    };

    return (
        <div>
            <h2>Add Meeting</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Pet ID:
                    <input
                        type="text"
                        value={petID}
                        onChange={(e) => setPetID(e.target.value)}
                        required
                    />
                </label>
                <br />
                <label>
                    User ID:
                    <input
                        type="text"
                        value={userID}
                        onChange={(e) => setUserID(e.target.value)}
                        required
                    />
                </label>
                <br />
                <label>
                    Date:
                    <input
                        type="date"
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                        required
                    />
                </label>
                <br />
                <button type="submit">Create Meeting</button>
            </form>
        </div>
    );
}

export default AddMeeting;
