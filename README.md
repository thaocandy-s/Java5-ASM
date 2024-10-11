

Spring boot

SQL Server

Các bước cần thiết:

1. Đổi username và password của MSSQL trong application.properties
2. Create database name: MEN_FASHION
3. Chạy dự án lần 1 để spring boot tự tạo bảng
4. Mở SSMS, chạy đoạn script Java5-AMS.sql (để đảm bảo tên được lưu có dấu)
5. Tại SSMS, insert một bản ghi vào bảng nhan_vien với trường mã = **'admin01'** để đăng nhập là role admin
6. Chạy lại dự án -> xong
