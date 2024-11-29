package poly.thao.menfashion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import poly.thao.menfashion.entity.base.EntityListener;
import poly.thao.menfashion.entity.base.PrimaryEntity;
import poly.thao.menfashion.model.EntityStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "hoa_don")
@EntityListeners(EntityListener.class)
public class HoaDon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    public NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    public KhachHang khachHang;

    @Column(name = "ngay_mua_hang")
    private LocalDateTime ngayMuaHang;

    @Column(name = "trang_thai")
    @Enumerated(EnumType.ORDINAL)
    private EntityStatus trangThai;

    public HoaDon(Integer id, NhanVien nhanVien, KhachHang khachHang, LocalDateTime ngayMuaHang) {
        this.id = id;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.ngayMuaHang = ngayMuaHang;
    }
}
