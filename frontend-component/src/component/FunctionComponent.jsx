import React, { useState, useEffect } from 'react';

const UserDataFunc = ({ userId }) => {
    const [user, setUser] = useState(null);
    const [seconds, setSeconds] = useState(0);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUserData = () => {
            fetch(`https://secret.url/user/${userId}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error fetching user data');
                    }
                    return response.json();
                })
                .then(data => {
                    setUser(data);
                    setError(null);
                })
                .catch(error => setError(error.toString()));
        };

        fetchUserData();

        const intervalId = setInterval(() => {
            setSeconds(prevSeconds => prevSeconds + 1);
        }, 1000);

        return () => clearInterval(intervalId);
    }, [userId]);

    return (
        <div>
            <h1>User Data Component</h1>
            {error ? (
                <p>Error: {error}</p>
            ) : user ? (
                <div>
                    <p>Name: {user.name}</p>
                    <p>Email: {user.email}</p>
                </div>
            ) : (
                <p>Loading user data...</p>
            )}
            <p>Timer: {seconds} seconds</p>
        </div>
    );
};

export default UserDataFunc;
