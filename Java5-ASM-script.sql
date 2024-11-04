create table khach_hang
(
    id            int identity
        primary key,
    trang_thai    smallint
        check ([trang_thai] >= 0 AND [trang_thai] <= 2),
    ma            varchar(10),
    so_dien_thoai varchar(15),
    ten           nvarchar(50),
    column_name   int
)
go

create table kich_thuoc
(
    id          int identity
        primary key,
    trang_thai  smallint
        check ([trang_thai] >= 0 AND [trang_thai] <= 2),
    ma          varchar(10),
    ten         nvarchar(50),
    column_name int
)
go

create table loai_phong
(
    id             int identity
        primary key,
    ten_loai_phong nvarchar(50),
    column_name    int
)
go

create table mau_sac
(
    id          int identity
        primary key,
    trang_thai  smallint
        check ([trang_thai] >= 0 AND [trang_thai] <= 2),
    ma          varchar(10),
    ten         nvarchar(50),
    column_name int
)
go

create table nhan_vien
(
    id            int identity
        primary key,
    trang_thai    smallint
        check ([trang_thai] >= 0 AND [trang_thai] <= 2),
    mat_khau      varchar(6),
    ma            varchar(10),
    ten           nvarchar(50),
    ten_dang_nhap varchar(50),
    column_name   int
)
go

create table hoa_don
(
    id            int identity
        primary key,
    id_khach_hang int
        constraint FKrygimdf5nr1g2t6u03gvtr1te
            references khach_hang,
    id_nhan_vien  int
        constraint FKkuxkrkgq8yftm4d8d7o0w6nbv
            references nhan_vien,
    trang_thai    smallint
        check ([trang_thai] >= 0 AND [trang_thai] <= 2),
    ngay_mua_hang datetime2(6)
)
go

create table phong
(
    id             int identity
        primary key,
    ghi_chu        varchar(255),
    so_luong_phong varchar(255),
    trang_thai     varchar(255),
    id_loai_phong  int
        constraint FKdq3pw5hymlspd7wcs7sy8e1ja
            references loai_phong,
    ten_phong      varchar(255)
)
go

create table san_pham
(
    id          int identity
        primary key,
    trang_thai  smallint
        check ([trang_thai] >= 0 AND [trang_thai] <= 2),
    ma          varchar(10),
    ten         nvarchar(50),
    column_name int
)
go

create table san_pham_chi_tiet
(
    don_gia       float,
    id            int identity
        primary key,
    id_kich_thuoc int
        constraint FKdtq9hr7xwf8dq6cr9imci52dd
            references kich_thuoc,
    id_mau_sac    int
        constraint FKij5n0goopp1ireh8su018u039
            references mau_sac,
    id_san_pham   int
        constraint FKan8cgqvo87bbk9p46t8csbhe8
            references san_pham,
    so_luong      int,
    trang_thai    smallint
        check ([trang_thai] >= 0 AND [trang_thai] <= 2),
    ma            varchar(10)
)
go

create table hoa_don_chi_tiet
(
    don_gia    float,
    id         int identity
        primary key,
    id_hoa_don int
        constraint FK5adopt864mjisuy5xmgmy8iun
            references hoa_don,
    id_spct    int
        constraint FKtg7wf3f4noic4uhrdn3lju7k6
            references san_pham_chi_tiet,
    so_luong   int,
    trang_thai smallint
        check ([trang_thai] >= 0 AND [trang_thai] <= 2)
)
go


