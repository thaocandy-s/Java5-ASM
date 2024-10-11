package poly.thao.menfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.model.request.LoginReq;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.repository.NhanVienRepository;
import poly.thao.menfashion.service.base.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class NhanVienService implements Service<NhanVien> {

    @Autowired
    public NhanVienRepository repository;

    private List<NhanVien> list;

    public NhanVienService() {
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
    }


    @Override
    public ResponseObject<List<NhanVien>> getList() {
//        repository.saveAll(list);
        return new ResponseObject<List<NhanVien>>(false, this.repository.findAll(), "Lấy danh sách NV thành công");
    }

    @Override
    public ResponseObject<NhanVien> add(NhanVien e) {
        try {
            boolean isExistMa = repository.existsByMa(e.getMa());
            if (isExistMa) {
                return new ResponseObject<NhanVien>(true, e, "Mã bị trùng");
            }
            boolean isExistTdn = repository.existsByTenDangNhap(e.getTenDangNhap());
            if (isExistTdn) {
                return new ResponseObject<NhanVien>(true, e, "Tên đăng nhập bị trùng");
            }
            this.repository.save(e);
            return new ResponseObject<NhanVien>(false, e, "Thêm NV thành công");
        } catch (Exception ex) {
            return new ResponseObject<NhanVien>(true, e, "Thêm NV thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<NhanVien> update(NhanVien e) {

        boolean isExistMa = repository.existsByMa(e.getMa(), e.getId());
        if (isExistMa) {
            return new ResponseObject<NhanVien>(true, e, "Mã bị trùng");
        }
        boolean isExistTdn = repository.existsByTenDangNhap(e.getTenDangNhap(), e.getId());
        if (isExistTdn) {
            return new ResponseObject<NhanVien>(true, e, "Tên đăng nhập bị trùng");
        }
        try {
            this.repository.save(e);
            return new ResponseObject<NhanVien>(false, e, "Sửa NV thành công");
        } catch (Exception ex) {
            return new ResponseObject<NhanVien>(true, e, "Sửa NV thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<NhanVien> findById(Integer id) {
        try {
            System.out.println("Nhan vien: " + id);
            boolean isExists = this.repository.existsById(id);
            if (isExists) {
                return new ResponseObject<NhanVien>(false, this.repository.findById(id).get(), "Lấy data NV thành công");
            } else {
                return new ResponseObject<NhanVien>(true, null, "NV không tồn tại");
            }
        } catch (Exception ex) {
            return new ResponseObject<NhanVien>(true, null, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<NhanVien> findByCode(String code) {
        try {
            for (NhanVien o : this.repository.findAll()) {
                if (o.getMa().equals(code)) {
                    return new ResponseObject<NhanVien>(false, o, "Lấy data NV thành công");
                }
            }
            return new ResponseObject<NhanVien>(true, null, "NV không tồn tại");
        } catch (Exception ex) {
            return new ResponseObject<NhanVien>(true, null, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<Integer> deleteById(Integer id) {
        try {
            ResponseObject<NhanVien> find = this.findById(id);
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
            ResponseObject<NhanVien> find = this.findByCode(code);
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

    public ResponseObject<NhanVien> login(LoginReq loginReq) {
        Optional<NhanVien> opUser = repository.findByTenDangNhapAndMatKhau(loginReq);
        NhanVien nv = opUser.orElse(null);

        ResponseObject<NhanVien> currentUser;
        if (nv == null) {
            currentUser = new ResponseObject<NhanVien>(true, null, "Tài khoản không hợp lệ");
        } else {
            currentUser = new ResponseObject<NhanVien>(false, nv, "Login thành công");
        }
        return currentUser;
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
        }
        return list1;
    }
}