package poly.thao.menfashion.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cart {
    //    public List<SanPhamsInCart> listSanPham;
    public Integer idNhanVien;
    public Integer idKhachHang;
    public Map<Integer, Integer> mapSanPham;


    public List<Integer> getSanPhamIds(){
        List<Integer> listId = new ArrayList<>();
        for (Map.Entry<Integer, Integer> sp: mapSanPham.entrySet()){
            listId.add(sp.getKey());
        };
        return listId;
    }


}


