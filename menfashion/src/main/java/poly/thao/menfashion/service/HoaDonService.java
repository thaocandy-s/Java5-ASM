package poly.thao.menfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import poly.thao.menfashion.entity.HoaDon;
import poly.thao.menfashion.entity.HoaDonChiTiet;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.repository.HoaDonChiTietRepository;
import poly.thao.menfashion.repository.HoaDonRepository;
import poly.thao.menfashion.service.base.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class HoaDonService implements Service<HoaDon> {

    @Autowired
    public HoaDonRepository repository;

    @Autowired
    public HoaDonChiTietRepository hoaDonChiTietRepository;

    private List<HoaDon> list;

    public HoaDonService() {
        this.list = new ArrayList<>();
    }

    public boolean isExistById(Integer id){
        return repository.existsById(id);
    }

    public List<HoaDonChiTiet> getListHDCTByHoaDonId(Integer id){
        return hoaDonChiTietRepository.findAllByHoaDon_Id(id);
    }

    @Override
    public ResponseObject<List<HoaDon>> getList() {
//        repository.saveAll(list);
        return new ResponseObject<List<HoaDon>>(false, this.repository.findAll(), "Lấy danh sách HD thành công");
    }

    public List<Double> listTongGiaHD() {
        return this.repository.listTongGiaHoaDon();
    }

    public List<HoaDon> searchHoaDon(List<HoaDon> list, String key) {
        List<HoaDon> listHD = new ArrayList<>();
        key = key.toLowerCase();
        for (HoaDon e : list) {
            String id = e.getId() + "";
            if (id.equals(key)
                    || e.getKhachHang().getMa().toLowerCase().contains(key)
                    || e.getKhachHang().getTen().contains(key)
                    || e.getNhanVien().getMa().toLowerCase().contains(key)
                    || e.getNhanVien().getTen().toLowerCase().contains(key)
                    || e.getKhachHang().getSoDienThoai().contains(key)) {
                listHD.add(e);
            }
        }
        return listHD;
    }

    public Double getTongGia(Integer id){
        return repository.getTongGiaByHoaDonId(id);
    }

    @Override
    public ResponseObject<HoaDon> add(HoaDon e) {
        try {
            if (e.getKhachHang() == null) {
                return new ResponseObject<HoaDon>(true, e, "Khách hàng không hợp lệ");
            }
            if (e.getNhanVien() == null) {
                return new ResponseObject<HoaDon>(true, e, "Nhân viên không hợp lệ");
            }
            e.setNgayMuaHang(LocalDateTime.now());
            HoaDon hoaDon = this.repository.save(e);
            return new ResponseObject<HoaDon>(false, hoaDon, "Thêm HD thành công");
        } catch (Exception ex) {
            return new ResponseObject<HoaDon>(true, e, "Thêm HD thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<HoaDon> update(HoaDon e) {
        try {
            if (e.getId() == null) {
                return new ResponseObject<>(true, e, "Không có Id HD hợp lệ");
            }
            if (e.getKhachHang() == null) {
                return new ResponseObject<HoaDon>(true, e, "Khách hàng không hợp lệ");
            }
            if (e.getNhanVien() == null) {
                return new ResponseObject<HoaDon>(true, e, "Nhân viên không hợp lệ");
            }
            this.repository.save(e);
            return new ResponseObject<HoaDon>(false, e, "Sửa HD thành công");
        } catch (Exception ex) {
            return new ResponseObject<HoaDon>(true, e, "Sửa HD thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<HoaDon> findById(Integer id) {
        try {
            Optional<HoaDon> opHoaDon = this.repository.findById(id);
            if (opHoaDon.orElse(null) == null) {
                return new ResponseObject<HoaDon>(false, null, "Hóa đơn mã " + id + " không tồn tại");
            } else {
                return new ResponseObject<HoaDon>(false, opHoaDon.get(), "Hóa đơn mã " + id + " tồn tại");
            }
        } catch (Exception ex) {
            return new ResponseObject<HoaDon>(true, null, "Lỗi: " + ex.getMessage());
        }
    }


    @Override
    public ResponseObject<HoaDon> findByCode(String code) {
        try {
            return null;
        } catch (Exception ex) {
            return new ResponseObject<HoaDon>(true, null, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<Integer> deleteById(Integer id) {
        try {
            return null;
        } catch (Exception ex) {
            return new ResponseObject<Integer>(true, id, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<String> deleteByCode(String code) {
        try {
            return null;
        } catch (Exception ex) {
            return new ResponseObject<String>(true, code, "Lỗi: " + ex.getMessage());
        }
    }
}

