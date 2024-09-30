package poly.thao.menfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import poly.thao.menfashion.entity.HoaDon;
import poly.thao.menfashion.entity.MauSac;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.repository.MauSacRepository;
import poly.thao.menfashion.service.base.Service;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class MauSacService implements Service<MauSac> {

    @Autowired
    private MauSacRepository repository;
    private List<MauSac> list;

    public MauSacService() {
        this.list = new ArrayList<>();
        list.add(new MauSac(1, "MS001", EntityStatus.ACTIVE, "MAU 1"));
        list.add(new MauSac(2, "MS002", EntityStatus.ACTIVE, "MAU 2"));
        list.add(new MauSac(3, "MS003", EntityStatus.ACTIVE, "MAU 3"));
        list.add(new MauSac(4, "MS004", EntityStatus.ACTIVE, "MAU 4"));
        list.add(new MauSac(5, "MS005", EntityStatus.ACTIVE, "MAU 5"));
        list.add(new MauSac(6, "MS006", EntityStatus.ACTIVE, "MAU 6"));
        list.add(new MauSac(7, "MS007", EntityStatus.ACTIVE, "MAU 7"));
        list.add(new MauSac(8, "MS008", EntityStatus.ACTIVE, "MAU 8"));

    }



    @Override
    public ResponseObject<List<MauSac>> getList() {
//        repository.saveAll(list);
        return new ResponseObject<List<MauSac>>(false, this.repository.findAll(), "Lấy danh sách MS thành công");
    }

    @Override
    public ResponseObject<MauSac> add(MauSac e) {
        try {
            boolean isExistMa = repository.existsByMa(e.getMa());
            if(isExistMa){
                return new ResponseObject<MauSac>(true, e, "Mã bị trùng");
            }
//            boolean isExistTen = repository.existsByTen(e.getTen());
//            if(isExistTen){
//                return new ResponseObject<MauSac>(true, e, "Tên bị trùng");
//            }
            this.repository.save(e);
            return new ResponseObject<MauSac>(false, e, "Thêm MS thành công");
        } catch (Exception ex) {
            return new ResponseObject<MauSac>(true, e, "Thêm MS thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<MauSac> update(MauSac e) {

        boolean isExistMa = repository.existsByMa(e.getMa(), e.getId());
        if(isExistMa){
            return new ResponseObject<MauSac>(true, e, "Mã bị trùng");
        }
//        boolean isExistTen = repository.existsByTen(e.getTen(), e.getId());
//        if(isExistTen){
//            return new ResponseObject<MauSac>(true, e, "Tên đăng nhập bị trùng");
//        }
        try {
            this.repository.save(e);
            return new ResponseObject<MauSac>(false, e, "Sửa MS thành công");
        } catch (Exception ex) {
            return new ResponseObject<MauSac>(true, e, "Sửa MS thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<MauSac> findById(Integer id) {
        try {
            boolean  isExists = this.repository.existsById(id);
            if(isExists){
                return new ResponseObject<MauSac>(false, this.repository.findById(id).orElse(null), "Lấy data MS thành công");
            }else {
                return new ResponseObject<MauSac>(true, null, "MS không tồn tại");
            }
        } catch (Exception ex) {
            return new ResponseObject<MauSac>(true, null, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<MauSac> findByCode(String code) {
        try {
            for (MauSac o : this.repository.findAll()) {
                if (o.getMa().equals(code)) {
                    return new ResponseObject<MauSac>(false, o, "Lấy data MS thành công");
                }
            }
            return new ResponseObject<MauSac>(true, null, "MS không tồn tại");
        } catch (Exception ex) {
            return new ResponseObject<MauSac>(true, null, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<Integer> deleteById(Integer id) {
        try {
            ResponseObject<MauSac> find = this.findById(id);
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
            ResponseObject<MauSac> find = this.findByCode(code);
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

    public List<MauSac> search(List<MauSac> list, String key) {
        List<MauSac> listMS = new ArrayList<>();
        key = key.toLowerCase();
        for (MauSac e : list) {
            if (e.getMa().toLowerCase().contains(key) || e.getTen().toLowerCase().contains(key)){
                listMS.add(e);
            }
        }
        return listMS;
    }
}

