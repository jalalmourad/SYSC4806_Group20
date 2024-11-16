document.addEventListener('DOMContentLoaded', (event) => {
    // Selects the form element by its ID
    const loginForm = document.getElementById('login-form');

    // Adds a submit event listener to the form
    loginForm.addEventListener('submit', function(event) {
        event.preventDefault(); // Prevents the default form submission behavior

        // Grab the username and password values from the form
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        // Encrypt the password using Base64 encoding
        const encryptedPassword = btoa(password); // Base64 encode the password

        // Create a JSON object with username and encrypted password
        const loginData = {
            username: username,
            password: encryptedPassword
        };

        // Send JSON data to the server
        fetch('/api/userAccount/login', { // Ensure the URL matches the Spring controller's endpoint
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData) // Send username and Base64 encoded password as JSON
        })
            .then(response => response.text()) // Since the backend sends a plain text response
            .then(data => {
                if (data === 'Login successful!') {
                    // Login successful
                    alert('Login Successful');
                    // Redirect to the home page
                    window.location.href = '../home.html'; // Update with actual path
                } else if (data === 'Invalid credentials!') {
                    // Display specific error message
                    alert('Invalid username or password. Please try again.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('There was a problem logging in. Please try again.');
            });
    });
});
