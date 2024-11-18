package poly.thao.menfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import poly.thao.menfashion.entity.KhachHang;
import poly.thao.menfashion.entity.KichThuoc;
import poly.thao.menfashion.entity.SanPham;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.repository.KhachHangRepository;
import poly.thao.menfashion.service.base.Service;
import poly.thao.menfashion.utils.helper.Helper;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class KhachHangService implements Service<KhachHang> {

    @Autowired
    public KhachHangRepository repository;

    private List<KhachHang> list;

    public KhachHangService() {
        this.list = new ArrayList<>();
        list.add(new KhachHang(1, "KH001", EntityStatus.ACTIVE, "Khach hang 1", "0123456789"));
        list.add(new KhachHang(2, "KH002", EntityStatus.ACTIVE, "Khach hang 2", "0123456789"));
        list.add(new KhachHang(3, "KH003", EntityStatus.ACTIVE, "Khach hang 3", "0123456789"));
        list.add(new KhachHang(4, "KH004", EntityStatus.ACTIVE, "Khach hang 4", "0123456789"));
        list.add(new KhachHang(5, "KH005", EntityStatus.ACTIVE, "Khach hang 5", "0123456789"));
        list.add(new KhachHang(6, "KH006", EntityStatus.ACTIVE, "Khach hang 6", "0123456789"));
        list.add(new KhachHang(7, "KH007", EntityStatus.ACTIVE, "Khach hang 7", "0123456789"));
        list.add(new KhachHang(8, "KH008", EntityStatus.ACTIVE, "Khach hang 8", "0123456789"));

    }


    @Override
    public ResponseObject<List<KhachHang>> getList() {
//        repository.saveAll(list);
        return new ResponseObject<List<KhachHang>>(false, this.repository.findAll(), "Lấy danh sách KH thành công");
    }

    @Override
    public ResponseObject<KhachHang> add(KhachHang e) {
        try {
            String validate = validate(e);
            if(validate != null){
                return new ResponseObject<>(true, e, validate);
            }
            boolean isExistMa = repository.existsByMa(e.getMa());
            if(isExistMa){
                return new ResponseObject<KhachHang>(true, e, "Mã bị trùng");
            }
            boolean ckSDT = Helper.isValidPhoneNumber(e.getSoDienThoai());
            if(!ckSDT){
                return new ResponseObject<KhachHang>(true, e, "Số điện thoại không hợp lệ, <br> - Phải có số 0 ở đầu <br> - Phải từ 10 - 15 chữ số");
            }
            this.repository.save(e);
            return new ResponseObject<KhachHang>(false, e, "Thêm KH thành công");
        } catch (Exception ex) {
            return new ResponseObject<KhachHang>(true, e, "Thêm KH thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<KhachHang> update(KhachHang e) {
        String validate = validate(e);
        if(validate != null){
            return new ResponseObject<>(true, e, validate);
        }
        boolean isExistMa = repository.existsByMa(e.getMa(), e.getId());
        if(isExistMa){
            return new ResponseObject<KhachHang>(true, e, "Mã bị trùng");
        }
        boolean ckSDT = Helper.isValidPhoneNumber(e.getSoDienThoai());
        if(!ckSDT){
            return new ResponseObject<KhachHang>(true, e, "Số điện thoại không hợp lệ, <br> - Phải có số 0 ở đầu <br> - Phải từ 10 - 15 chữ số");
        }
        try {
            this.repository.save(e);
            return new ResponseObject<KhachHang>(false, e, "Sửa KH thành công");
        } catch (Exception ex) {
            return new ResponseObject<KhachHang>(true, e, "Sửa KH thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<KhachHang> findById(Integer id) {
        try {

            boolean  isExists = this.repository.existsById(id);
            if(isExists){
                KhachHang kh = this.repository.findById(id).orElse(null);
                return new ResponseObject<KhachHang>(false, kh, "Lấy data KH thành công");
            }else {
                return new ResponseObject<KhachHang>(true, null, "KH không tồn tại");
            }
        } catch (Exception ex) {
            return new ResponseObject<KhachHang>(true, null, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<KhachHang> findByCode(String code) {
        try {

            for (KhachHang o : this.repository.findAll()) {
                if (o.getMa().equals(code)) {
                    return new ResponseObject<KhachHang>(false, o, "Lấy data KH thành công");
                }
            }
            return new ResponseObject<KhachHang>(true, null, "KH không tồn tại");
        } catch (Exception ex) {
            return new ResponseObject<KhachHang>(true, null, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<Integer> deleteById(Integer id) {
        try {
            ResponseObject<KhachHang> find = this.findById(id);
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
            ResponseObject<KhachHang> find = this.findByCode(code);
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

    public List<KhachHang> search(List<KhachHang> list, String key) {
        List<KhachHang> list1 = new ArrayList<>();
        key = key.toLowerCase();
        for (KhachHang o: list){
            if(o.getMa().toLowerCase().contains(key)
                    || o.getTen().toLowerCase().contains(key)){
                list1.add(o);
            }
        }
        return list1;
    }

    public String validate(KhachHang e){

        String regexMa = "^KH\\d{3}$";
        String regexTen = "^[a-zA-ZÀ-ỹ\\s]{5,50}$";
        String regexSoDienThoai = "^0\\d{9,12}$";

        if(!e.getMa().matches(regexMa)){
            return  "Mã kích thước cần 5 ký tự: KH+3 số bất kỳ";
        }
        if(!e.getTen().matches(regexTen)){
            return  "Tên kích thước cần 3-30 ký tự, không số và ký tự đặc biệt";
        }
        if(!e.getSoDienThoai().matches(regexSoDienThoai)){
            return  "Số điện thoại khách hàng cần 10-13 chữ số, số 0 ở đầu";
        }
        return null;
    }
}
