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
import poly.thao.menfashion.model.EntityStatus;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "hoa_don_chi_tiet")
public class HoaDonChiTiet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_hoa_don")
    public HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "id_spct")
    public SanPhamChiTiet sanPhamChiTiet;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "don_gia")
    private Double donGia;

    @Column(name = "trang_thai")
    @Enumerated(EnumType.ORDINAL)
    private EntityStatus trangThai;

}
