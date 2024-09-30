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
@Table(name = "kich_thuoc")
public class KichThuoc extends PrimaryEntity implements Serializable {

    @Column(name = "ten", length = 50)
    private String ten;

    public KichThuoc(Integer id, String ma, EntityStatus trangThai, String ten) {
        super(id, ma, trangThai);
        this.ten = ten;
    }

}
