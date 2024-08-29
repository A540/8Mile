document.addEventListener('DOMContentLoaded', () => {
    const themeToggle = document.querySelector('[data-bs-theme]');
    const currentTheme = localStorage.getItem('theme') || 'auto';

    // 현재 테마 적용
    document.documentElement.setAttribute('data-bs-theme', currentTheme);

    // 테마 토글 버튼 클릭 시 동작
    document.querySelectorAll('.dropdown-menu button').forEach(button => {
        button.addEventListener('click', (e) => {
            const newTheme = e.target.getAttribute('data-bs-theme-value');
            document.documentElement.setAttribute('data-bs-theme', newTheme);
            localStorage.setItem('theme', newTheme);
        });
    });
});
