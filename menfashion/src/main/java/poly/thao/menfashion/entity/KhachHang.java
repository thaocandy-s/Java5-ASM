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
@Table(name = "khach_hang")
public class KhachHang extends PrimaryEntity implements Serializable {

    @Column(name = "ten", length = 50)
    private String ten;

    @Column(name = "so_dien_thoai", length = 15)
    private String soDienThoai;

    public KhachHang(Integer id, String ma, EntityStatus trangThai, String ten, String soDienThoai) {
        super(id, ma, trangThai);
        this.ten = ten;
        this.soDienThoai = soDienThoai;
    }

    public KhachHang(int id, String ten, String sdt) {
        this.setId(id);
        this.ten = ten;
        this.soDienThoai = sdt;
    }
}
