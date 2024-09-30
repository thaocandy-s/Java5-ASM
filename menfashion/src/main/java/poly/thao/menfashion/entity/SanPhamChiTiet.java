package poly.thao.menfashion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "san_pham_chi_tiet")
public class SanPhamChiTiet extends PrimaryEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_kich_thuoc")
    public KichThuoc kichThuoc;

    @ManyToOne
    @JoinColumn(name = "id_mau_sac")
    public MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "id_san_pham")
    public SanPham sanPham;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "don_gia")
    private Double donGia;

    public SanPhamChiTiet(Integer id, String ma, EntityStatus trangThai, KichThuoc kichThuoc, MauSac mauSac, SanPham sanPham, Integer soLuong, Double donGia) {
        super(id, ma, trangThai);
        this.kichThuoc = kichThuoc;
        this.mauSac = mauSac;
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }
}
