import { useState, useEffect } from 'react';
import axios from 'axios';

export default function HealthIndicator() {
    const [status, setStatus] = useState('Checking...');

    useEffect(() => {
        const checkHealth = async () => {
            try {
                const response = await axios.get('/api/status');
                setStatus(response.data.status === 'UP' ? 'Online' : 'Offline');
            } catch (error) {
                setStatus('Offline (Error)');
            }
        };
        const interval = setInterval(checkHealth, 5000); // Ping setiap 5 detik
        checkHealth();
        return () => clearInterval(interval);
    }, []);

    return <div>Status Sistem: {status}</div>;
}