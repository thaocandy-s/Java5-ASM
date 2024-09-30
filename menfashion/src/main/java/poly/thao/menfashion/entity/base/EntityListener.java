package poly.thao.menfashion.entity.base;

import jakarta.persistence.PrePersist;
import poly.thao.menfashion.entity.HoaDon;

import java.time.LocalDateTime;

public class EntityListener {
    @PrePersist
    private void onCreate(HoaDon hoaDon){
        hoaDon.setNgayMuaHang(LocalDateTime.now());
    }
}
