import React, { useState, useEffect } from 'react';

function ModifyMeeting({ meetingID }) {
    const [meeting, setMeeting] = useState(null);
    const [petID, setPetID] = useState('');
    const [userID, setUserID] = useState('');
    const [date, setDate] = useState('');

    useEffect(() => {
        const fetchMeeting = async () => {
            try {
                const response = await fetch(`/api/meetings/${meetingID}`);
                const data = await response.json();
                setMeeting(data);
                setPetID(data.pet.id);
                setUserID(data.user.id);
                setDate(new Date(data.date).toISOString().split('T')[0]); // Format to YYYY-MM-DD
            } catch (error) {
                console.error('Error fetching meeting:', error);
            }
        };

        fetchMeeting();
    }, [meetingID]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const updatedMeeting = {
            petID: petID,
            userID: userID,
            date: new Date(date),
        };

        try {
            const response = await fetch(`/api/meetings/${meetingID}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updatedMeeting),
            });

            if (response.ok) {
                alert('Meeting updated successfully!');
                // Clear form or redirect to meetings list
            } else {
                const error = await response.json();
                alert(`Error: ${error.message}`);
            }
        } catch (error) {
            console.error('Error updating meeting:', error);
            alert('Error updating meeting');
        }
    };

    if (!meeting) return <p>Loading meeting data...</p>;

    return (
        <div>
            <h2>Modify Meeting</h2>
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
                <button type="submit">Update Meeting</button>
            </form>
        </div>
    );
}

export default ModifyMeeting;
