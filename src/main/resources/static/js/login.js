document.addEventListener("DOMContentLoaded", function() {
    var userSession = getCookie("JSESSIONID");

    if (userSession) {
        fetch('/auth/check-authentication', {
            method: 'GET',
            credentials: 'include' // 쿠키를 포함하여 요청
        })
            .then(response => response.json()) // JSON 형태로 변환
            .then(data => {
                if (data.authenticated) {
                    console.log("Authenticated");
                    console.log("Session ID:", data.sessionId); // 세션 ID 출력
                    window.location.href = "/boards"; // 인증된 사용자라면 리디렉션
                } else {
                    console.log("Not authenticated");
                }
            })
            .catch(error => {
                console.error("Error during authentication check:", error);
            });
    } else {
        console.log("JSESSIONID 쿠키를 찾을 수 없습니다.");
    }
});

function getCookie(name) {
    var matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([.$?*|{}()[]\/+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}
