

Spring boot

SQL Server


Các bước cần thiết:

Checkout sang nhánh dev để chạy

1. Đổi username và password của MSSQL trong application.properties
2. Create database name: MEN_FASHION
3. Chạy dự án lần 1 để spring boot tự tạo bảng
4. Mở SSMS, chạy đoạn script Java5-AMS.sql (để đảm bảo tên được lưu có dấu)
5. Tại SSMS, insert một bản ghi vào bảng nhan_vien để đăng nhập là role admin:

    - tên đăng nhập: 8-30 ký tự, ít nhất 1 chữ hoa và 1 ký tự đặc biệt
    - mật khẩu: 5-10 ký tự, ít nhất 1 số và 1 ký tự đặc biệt
    - trường mã = **'admin01'** 
    
    - vào lớp AppService: sửa 'Thaocandy@' thành tên đăng nhập của mình vừa lưu trên

6. Chạy lại dự án, thử đăng nhập bằng tài khoản đã cài -> xong

