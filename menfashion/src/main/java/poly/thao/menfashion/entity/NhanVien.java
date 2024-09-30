package poly.thao.menfashion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import poly.thao.menfashion.entity.base.PrimaryEntity;
import poly.thao.menfashion.model.EntityStatus;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "nhan_vien")
public class NhanVien extends PrimaryEntity implements Serializable {

    @Column(name = "ten", length = 50)
    private String ten;

    @Column(name = "ten_dang_nhap")
    private String tenDangNhap;

    @Column(name = "mat_khau", length = 6)
    private String matKhau;


    public NhanVien(Integer id, String ma, String ten, String tenDangNhap, String matKhau, EntityStatus trangThai) {
        super(id, ma, trangThai);
        this.ten = ten;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }

}
