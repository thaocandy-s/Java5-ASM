create database MEN_FASHION
GO

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

select * from nhan_vien
select * from khach_hang

update nhan_vien
set mat_khau= 'mia21@'
where ma = 'admin01'

DELETE FROM khach_hang
WHERE id = 7