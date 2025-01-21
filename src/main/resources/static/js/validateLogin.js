document.getElementById('signupForm').addEventListener('submit', function(event) {
    // Input fields
    const userName = document.getElementById('userName').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    // Regular expressions for validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

    // Validation checks
    let valid = true;
    let errorMessage = '';

    // Nickname validation
    if (userName === '') {
        valid = false;
        errorMessage += '닉네임(활동명)을 입력해 주세요.\n';
    }

    // Email validation
    if (!emailRegex.test(email)) {
        valid = false;
        errorMessage += '유효한 이메일 주소를 입력해 주세요.\n';
    }

    // Password validation
    if (!passwordRegex.test(password)) {
        valid = false;
        errorMessage += '비밀번호는 최소 8자 이상이어야 하며, 숫자와 문자를 포함해야 합니다.\n';
    }

    // Confirm password validation
    if (password !== confirmPassword) {
        valid = false;
        errorMessage += '비밀번호가 일치하지 않습니다.\n';
    }

    // If any validation fails, prevent form submission and alert the user
    if (!valid) {
        event.preventDefault();
        alert(errorMessage);
    }else {
        // If validation is successful
        alert('성공적으로 회원가입되었습니다.');
    }
});
