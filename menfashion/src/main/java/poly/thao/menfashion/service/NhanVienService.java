package poly.thao.menfashion.service;

import org.springframework.stereotype.Component;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.repository.NhanVienRepository;
import poly.thao.menfashion.service.base.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Component
public class NhanVienService implements Service<NhanVien> {

    public final NhanVienRepository repository;

    private List<NhanVien> list;

    public NhanVienService(NhanVienRepository repository) {

        this.list = new ArrayList<>();
        list.add(new NhanVien(1, "NV001", "Nhan Vien 001", "nhanvien001", "123", EntityStatus.ACTIVE));
        list.add(new NhanVien(2, "NV002", "Nhan Vien 002", "nhanvien002", "123", EntityStatus.ACTIVE));
        list.add(new NhanVien(3, "NV003", "Nhan Vien 003", "nhanvien003", "123", EntityStatus.DELETED));
        list.add(new NhanVien(4, "NV004", "Nhan Vien 004", "nhanvien004", "123", EntityStatus.ACTIVE));
        list.add(new NhanVien(5, "NV005", "Nhan Vien 005", "nhanvien005", "123", EntityStatus.ACTIVE));
        list.add(new NhanVien(6, "NV006", "Nhan Vien 006", "nhanvien006", "123", EntityStatus.DELETED));
        list.add(new NhanVien(7, "NV007", "Nhan Vien 007", "nhanvien007", "123", EntityStatus.ACTIVE));
        list.add(new NhanVien(8, "NV008", "Nhan Vien 008", "nhanvien008", "123", EntityStatus.ACTIVE));
        list.add(new NhanVien(9, "NV009", "Nhan Vien 009", "nhanvien009", "123", EntityStatus.DELETED));
        this.repository = repository;
    }


    @Override
    public ResponseObject<List<NhanVien>> getList() {
//        repository.saveAll(list);
        return new ResponseObject<>(false, this.repository.findAll(), "Lấy danh sách NV thành công");
    }

    @Override
    public ResponseObject<NhanVien> add(NhanVien e) {
        try {
            String validate = validate(e);
            if(validate != null){
                return new ResponseObject<>(true, e, validate);
            }
            boolean isExistMa = repository.existsByMa(e.getMa());
            if (isExistMa) {
                return new ResponseObject<>(true, e, "Mã bị trùng");
            }
            boolean isExistTdn = repository.existsByTenDangNhap(e.getTenDangNhap());
            if (isExistTdn) {
                return new ResponseObject<>(true, e, "Tên đăng nhập bị trùng");
            }
            this.repository.save(e);
            return new ResponseObject<>(false, e, "Thêm NV thành công");
        } catch (Exception ex) {
            return new ResponseObject<>(true, e, "Thêm NV thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<NhanVien> update(NhanVien e) {

        String validate = validate(e);
        if(validate != null){
            return new ResponseObject<>(true, e, validate);
        }
        boolean isExistMa = repository.existsByMa(e.getMa(), e.getId());
        if (isExistMa) {
            return new ResponseObject<>(true, e, "Mã bị trùng");
        }
        boolean isExistTdn = repository.existsByTenDangNhap(e.getTenDangNhap(), e.getId());
        if (isExistTdn) {
            return new ResponseObject<>(true, e, "Tên đăng nhập bị trùng");
        }
        try {
            this.repository.save(e);
            return new ResponseObject<>(false, e, "Sửa NV thành công");
        } catch (Exception ex) {
            return new ResponseObject<>(true, e, "Sửa NV thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<NhanVien> findById(Integer id) {
        try {

            boolean isExists = this.repository.existsById(id);
            if (isExists) {
                return new ResponseObject<>(false, this.repository.findById(id).get(), "Lấy data NV thành công");
            } else {
                return new ResponseObject<>(true, null, "NV không tồn tại");
            }
        } catch (Exception ex) {
            return new ResponseObject<>(true, null, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<NhanVien> findByCode(String code) {
        try {
            for (NhanVien o : this.repository.findAll()) {
                if (o.getMa().equals(code)) {
                    return new ResponseObject<>(false, o, "Lấy data NV thành công");
                }
            }
            return new ResponseObject<>(true, null, "NV không tồn tại");
        } catch (Exception ex) {
            return new ResponseObject<>(true, null, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<Integer> deleteById(Integer id) {
        try {
            ResponseObject<NhanVien> find = this.findById(id);
            if (find.isHasError) {
                return new ResponseObject<>(true, id, find.getMessage());
            } else {
                find.data.setTrangThai(EntityStatus.DELETED);
                this.repository.save(find.data);
                return new ResponseObject<>(false, id, "Xóa thành công");
            }
        } catch (Exception ex) {
            return new ResponseObject<>(true, id, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<String> deleteByCode(String code) {
        try {
            ResponseObject<NhanVien> find = this.findByCode(code);
            if (find.isHasError) {
                return new ResponseObject<>(true, code, find.getMessage());
            } else {
                find.data.setTrangThai(EntityStatus.DELETED);
                this.repository.save(find.data);
                return new ResponseObject<>(false, code, "Xóa thành công");
            }
        } catch (Exception ex) {
            return new ResponseObject<>(true, code, "Lỗi: " + ex.getMessage());
        }
    }

    public List<NhanVien> search(List<NhanVien> list, String key) {
        List<NhanVien> list1 = new ArrayList<>();
        key = key.toLowerCase();
        for (NhanVien e : list) {
            if (e.getMa().toLowerCase().contains(key)
                    || e.getTen().toLowerCase().contains(key)
                    || e.getTenDangNhap().toLowerCase().contains(key)
            ){
                list1.add(e);
            }
//            if (key == null) {
//                throw new NullPointerException("Key must not be null");
//            }
        }
        return list1;
    }

//    public List<NhanVien> search(List<NhanVien> nhanViens, String key) {
//        return nhanViens.stream()
//                .filter(nv -> nv.getMa().equals(key))
//                .collect(Collectors.toList());
//    }

    public String validate(NhanVien e){
//        String regexMa = "ssss"; // NV + 3 so nguyen bat ky
//        String regexTen = "ssss"; // 10 ~ 50 ky tu, không so va ky tu dac biet
//        String regexTenDangNhap = "ssss"; // 10 ~ 30 ky tu, ít nhất 1 chữ hoa và 1 ký tự đặc biệt
//        String regexMatKhau = "ssss"; // 5 ~ 10 ky tu; co so va ky tu dac biet

        String regexMa = "^NV\\d{3}$";
        String regexTen = "^[a-zA-ZÀ-ỹ\\s]{10,20}$";
        String regexTenDangNhap = "^(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$";
        String regexMatKhau = "^(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,10}$";

        if(!e.getMa().matches(regexMa)){
            return  "Mã nhân viên cần 5 ký tự: NV+3 số bất kỳ";
        }
        if(!e.getTen().matches(regexTen)){
            return  "Tên nhân viên cần 10-20 ký tự, không số và ký tự đặc biệt";
        }
        if(!e.getTenDangNhap().matches(regexTenDangNhap)){
            return  "Tên đăng nhập cần 10-20 ký tự, phải có ít nhất 1 chữ hoa và 1 ký tự đặc biệt";
        }
        if(!e.getMatKhau().matches(regexMatKhau)){
            return  "Mật khẩu cần 5-10 ký tự, phải có ít nhất 1 số và 1 ký tự đặc biệt";
        }
        return null;
    }
}
