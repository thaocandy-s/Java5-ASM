create database MEN_FASHION
go
use MEN_FASHION
go

alter table khach_hang
    alter column ten nvarchar(50) null
go
alter table nhan_vien
    alter column ten nvarchar(50) null
go
alter table kich_thuoc
    alter column ten nvarchar(50) null
go
alter table mau_sac
    alter column ten nvarchar(50) null
go
alter table san_pham
    alter column ten nvarchar(50) null
go

insert into nhan_vien (trang_thai, mat_khau, ma, ten, ten_dang_nhap)
values (1, 'admin', 'nv1', 'admin', 'admin');
