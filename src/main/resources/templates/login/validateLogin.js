document.addEventListener('DOMContentLoaded', (event) => {
    // Select the signup form
    const signupForm = document.getElementById('signupForm');

    // Select the signup button
    const signupButton = document.getElementById('signupButton');

    // Add event listener for form submission
    signupForm.addEventListener('submit', (event) => {
        // Prevent the form from submitting normally
        event.preventDefault();


        if (validateForm()) {
            signupForm.submit();
            alert('성공적으로 회원가입되었습니다.');
        }
    });

    // Function to validate the form
    function validateForm() {
        let isValid = true;

        // Example validation checks
        const userId = document.getElementById('userId').value;
        const name = document.getElementById('name').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('floatingConfirmPassword').value;

        if (userId.trim() === '') {
            isValid = false;
            alert('User ID is required');
        }
        if (name.trim() === '') {
            isValid = false;
            alert('Name is required');
        }
        if (email.trim() === '' || !validateEmail(email)) {
            isValid = false;
            alert('A valid email is required');
        }
        if (password.trim() === '' || password !== confirmPassword) {
            isValid = false;
            alert('Passwords must match');
        }

        return isValid;
    }

    // Function to validate email
    function validateEmail(email) {
        const re = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        return re.test(email);
    }
});
