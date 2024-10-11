// Lấy tất cả các link trong nav
const links = document.querySelectorAll('.sidebar a');

// Lấy URL hiện tại
const currentUrl = window.location.pathname.split('/').pop();

// Kiểm tra từng link
links.forEach(link => {
    // Nếu href của link trùng với URL hiện tại
    if (link.getAttribute('href') === currentUrl) {
        // Thêm class 'active' vào link đó
        link.classList.add('active');
    }
});
