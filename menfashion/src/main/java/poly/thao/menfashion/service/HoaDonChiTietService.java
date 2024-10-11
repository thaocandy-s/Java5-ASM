package poly.thao.menfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import poly.thao.menfashion.entity.HoaDonChiTiet;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.repository.HoaDonChiTietRepository;
import poly.thao.menfashion.service.base.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class HoaDonChiTietService implements Service<HoaDonChiTiet> {

    @Autowired
    public HoaDonChiTietRepository repository;
    private List<HoaDonChiTiet> list;

    public HoaDonChiTietService() {
        this.list = new ArrayList<>();
    }


    @Override
    public ResponseObject<List<HoaDonChiTiet>> getList() {
//        repository.saveAll(list);
        return new ResponseObject<List<HoaDonChiTiet>>(false, this.repository.findAll(), "Lấy danh sách HD thành công");
    }

    public List<HoaDonChiTiet> searchHoaDonChiTiet(List<HoaDonChiTiet> list, String key) {
        List<HoaDonChiTiet> listHDCT = new ArrayList<>();
        key = key.toLowerCase();
        for (HoaDonChiTiet e : list) {
            String id = e.getId() + "";
            if (id.equals(key)
                    || e.getSanPhamChiTiet().getMa().toLowerCase().contains(key)
                    || (e.getHoaDon().getId()+"").toLowerCase().contains(key)){
                listHDCT.add(e);
            }
        }
        return listHDCT;
    }

    @Override
    public ResponseObject<HoaDonChiTiet> add(HoaDonChiTiet e) {
        try {
            if (e.getHoaDon() == null) {
                return new ResponseObject<HoaDonChiTiet>(true, e, "Không có hóa đơn mã ");
            }
            if (e.getSanPhamChiTiet() == null) {
                return new ResponseObject<HoaDonChiTiet>(true, e, "Không có SPCT mã");
            }
            if (e.getSoLuong() == null || e.getSoLuong() < 0) {
                return new ResponseObject<HoaDonChiTiet>(true, e, "Số lượng phải > 0");
            }
            this.repository.save(e);
            return new ResponseObject<HoaDonChiTiet>(false, e, "Thêm HD thành công");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseObject<HoaDonChiTiet>(true, e, "Thêm HD thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<HoaDonChiTiet> update(HoaDonChiTiet e) {
        try {
            if (e.getHoaDon() == null) {
                return new ResponseObject<HoaDonChiTiet>(true, e, "Không có hóa đơn mã ");
            }
            if (e.getSanPhamChiTiet() == null) {
                return new ResponseObject<HoaDonChiTiet>(true, e, "Không có SPCT mã");
            }
            if (e.getSoLuong() == null || e.getSoLuong() < 0) {
                return new ResponseObject<HoaDonChiTiet>(true, e, "Số lượng phải > 0");
            }
            this.repository.save(e);
            return new ResponseObject<HoaDonChiTiet>(false, e, "Sửa HD thành công");
        } catch (Exception ex) {
            return new ResponseObject<HoaDonChiTiet>(true, e, "Sửa HD thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<HoaDonChiTiet> findById(Integer id) {
        try {
            Optional<HoaDonChiTiet> opHoaDonChiTiet = this.repository.findById(id);
            if (opHoaDonChiTiet.orElse(null) == null) {
                return new ResponseObject<HoaDonChiTiet>(true, null, "Hóa đơn CT mã " + id + " không tồn tại");
            } else {
                return new ResponseObject<HoaDonChiTiet>(false, opHoaDonChiTiet.get(), "Hóa đơn CT mã " + id + " tồn tại");
            }
        } catch (Exception ex) {
            return new ResponseObject<HoaDonChiTiet>(true, null, "Lỗi: " + ex.getMessage());
        }
    }


    @Override
    public ResponseObject<HoaDonChiTiet> findByCode(String code) {
        try {
            return null;
        } catch (Exception ex) {
            return new ResponseObject<HoaDonChiTiet>(true, null, "Lỗi: " + ex.getMessage());
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

    public List<HoaDonChiTiet> getListByHoaDonId(){
        return null;
    }
}

