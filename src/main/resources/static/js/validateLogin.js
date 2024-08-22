document.addEventListener('DOMContentLoaded', (event) => {
    // Select the signup form
    const signupForm = document.getElementById('signupForm');

    // Add event listener for form submission
    signupForm.addEventListener('submit', async (event) => {
        // Prevent the form from submitting normally
        event.preventDefault();

        // Validate form and check if email exists in the database
        if (await validateForm()) {
            signupForm.submit();
            alert('성공적으로 회원가입되었습니다.');
        }
    });

    // Function to validate the form
    async function validateForm() {
        let isValid = true;

        // Retrieve form values
        const userName = document.getElementById('userName').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('floatingConfirmPassword').value;

        // Check if userName is empty
        if (userName.trim() === '') {
            isValid = false;
            alert('이름을 입력해주세요');
        }

        // Check if email is valid and not empty
        if (email.trim() === '' || !validateEmail(email)) {
            isValid = false;
            alert('유효한 이메일을 입력해주세요');
        } else {
            // Check if the email is already registered in the database
            const emailExists = await checkEmailExists(email);
            if (emailExists) {
                isValid = false;
                alert('이미 등록된 이메일입니다.');
            }
        }

        // Check if password is not empty and matches confirmPassword
        if (password.trim() === '') {
            isValid = false;
            alert('비밀번호를 입력해주세요');
        } else if (password !== confirmPassword) {
            isValid = false;
            alert('비밀번호가 일치해야 합니다.');
        }

        return isValid;
    }

    // Function to validate email format
    function validateEmail(email) {
        const re = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        return re.test(email);
    }

    // Function to check if the email exists in the database
    async function checkEmailExists(email) {
        try {
            // Send a request to the server to check if the email exists
            const response = await fetch('/check-email', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email: email })
            });

            const data = await response.json();
            return data.exists;  // Assuming the server responds with { exists: true/false }
        } catch (error) {
            console.error('Error checking email:', error);
            alert('이메일 확인 중 오류가 발생했습니다. 나중에 다시 시도해주세요.');
            return false;  // If an error occurs, consider the email as non-existing
        }
    }
});
