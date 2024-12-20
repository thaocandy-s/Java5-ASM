package poly.thao.menfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import poly.thao.menfashion.entity.KichThuoc;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.repository.KichThuocRepository;
import poly.thao.menfashion.service.base.Service;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class KichThuocService implements Service<KichThuoc> {

    @Autowired
    private KichThuocRepository repository;
    private List<KichThuoc> list;

    public KichThuocService() {
        this.list = new ArrayList<>();
        list.add(new KichThuoc(1, "KT001", EntityStatus.ACTIVE, "KICH THUOC 1"));
        list.add(new KichThuoc(2, "KT002", EntityStatus.ACTIVE, "KICH THUOC 2"));
        list.add(new KichThuoc(3, "KT003", EntityStatus.ACTIVE, "KICH THUOC 3"));
        list.add(new KichThuoc(4, "KT004", EntityStatus.ACTIVE, "KICH THUOC 4"));
        list.add(new KichThuoc(5, "KT005", EntityStatus.ACTIVE, "KICH THUOC 5"));
        list.add(new KichThuoc(6, "KT006", EntityStatus.ACTIVE, "KICH THUOC 6"));
        list.add(new KichThuoc(7, "KT007", EntityStatus.ACTIVE, "KICH THUOC 7"));
        list.add(new KichThuoc(8, "KT008", EntityStatus.ACTIVE, "KICH THUOC 8"));

    }



    @Override
    public ResponseObject<List<KichThuoc>> getList() {
//        repository.saveAll(list);
        return new ResponseObject<List<KichThuoc>>(false, this.repository.findAll(), "Lấy danh sách KT thành công");
    }

    public List<KichThuoc> search(List<KichThuoc> list, String key) {
        List<KichThuoc> listKT = new ArrayList<>();
        key = key.toLowerCase();
        for (KichThuoc e : list) {
            if (e.getMa().toLowerCase().contains(key) || e.getTen().toLowerCase().contains(key)){
                listKT.add(e);
            }
        }
        return listKT;
    }

    @Override
    public ResponseObject<KichThuoc> add(KichThuoc e) {
        try {
            String validate = validate(e);
            if(validate != null){
                return new ResponseObject<>(true, e, validate);
            }
            boolean isExistMa = repository.existsByMa(e.getMa());
            if(isExistMa){
                return new ResponseObject<KichThuoc>(true, e, "Mã bị trùng");
            }
            this.repository.save(e);
            return new ResponseObject<KichThuoc>(false, e, "Thêm KT thành công");
        } catch (Exception ex) {
            return new ResponseObject<KichThuoc>(true, e, "Thêm KT thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<KichThuoc> update(KichThuoc e) {
        String validate = validate(e);
        if(validate != null){
            return new ResponseObject<>(true, e, validate);
        }
        boolean isExistMa = repository.existsByMa(e.getMa(), e.getId());
        if(isExistMa){
            return new ResponseObject<KichThuoc>(true, e, "Mã bị trùng");
        }
        try {
            this.repository.save(e);
            return new ResponseObject<KichThuoc>(false, e, "Sửa KT thành công");
        } catch (Exception ex) {
            return new ResponseObject<KichThuoc>(true, e, "Sửa KT thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<KichThuoc> findById(Integer id) {
        try {
            boolean  isExists = this.repository.existsById(id);
            if(isExists){
                return new ResponseObject<KichThuoc>(false, this.repository.findById(id).orElse(null), "Lấy data KT thành công");
            }else {
                return new ResponseObject<KichThuoc>(true, null, "KT không tồn tại");
            }
        } catch (Exception ex) {
            return new ResponseObject<KichThuoc>(true, null, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<KichThuoc> findByCode(String code) {
        try {
            for (KichThuoc o : this.repository.findAll()) {
                if (o.getMa().equals(code)) {
                    return new ResponseObject<KichThuoc>(false, o, "Lấy data KT thành công");
                }
            }
            return new ResponseObject<KichThuoc>(true, null, "KT không tồn tại");
        } catch (Exception ex) {
            return new ResponseObject<KichThuoc>(true, null, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<Integer> deleteById(Integer id) {
        try {
            ResponseObject<KichThuoc> find = this.findById(id);
            if (find.isHasError) {
                return new ResponseObject<Integer>(true, id, find.getMessage());
            } else {
                find.data.setTrangThai(EntityStatus.DELETED);
                this.repository.save(find.data);
                return new ResponseObject<Integer>(false, id, "Xóa thành công");
            }
        } catch (Exception ex) {
            return new ResponseObject<Integer>(true, id, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<String> deleteByCode(String code) {
        try {
            ResponseObject<KichThuoc> find = this.findByCode(code);
            if (find.isHasError) {
                return new ResponseObject<String>(true, code, find.getMessage());
            } else {
                find.data.setTrangThai(EntityStatus.DELETED);
                this.repository.save(find.data);
                return new ResponseObject<String>(false, code, "Xóa thành công");
            }
        } catch (Exception ex) {
            return new ResponseObject<String>(true, code, "Lỗi: " + ex.getMessage());
        }
    }

    public String validate(KichThuoc e){

        String regexMa = "^KT\\d{3}$";
        String regexTen = "^[a-zA-ZÀ-ỹ\\s]{3,30}$";

        if(!e.getMa().matches(regexMa)){
            return  "Mã kích thước cần 5 ký tự: KT+3 số bất kỳ";
        }
        if(!e.getTen().matches(regexTen)){
            return  "Tên kích thước cần 3-30 ký tự, không số và ký tự đặc biệt";
        }
        return null;
    }
}

