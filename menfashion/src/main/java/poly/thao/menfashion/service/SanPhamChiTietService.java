package poly.thao.menfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import poly.thao.menfashion.entity.HoaDon;
import poly.thao.menfashion.entity.KichThuoc;
import poly.thao.menfashion.entity.MauSac;
import poly.thao.menfashion.entity.SanPham;
import poly.thao.menfashion.entity.SanPhamChiTiet;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.model.response.SanPhamChiTietDTO;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.repository.SanPhamChiTietRepository;
import poly.thao.menfashion.service.base.Service;
import poly.thao.menfashion.utils.helper.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class SanPhamChiTietService implements Service<SanPhamChiTiet> {

    @Autowired
    private SanPhamChiTietRepository repository;
    private List<SanPhamChiTiet> list;

    public SanPhamChiTietService() {
        this.list = new ArrayList<>();

        KichThuocService KTser = new KichThuocService();
        MauSacService Mauser = new MauSacService();
        SanPhamService SPser = new SanPhamService();

        list.add(new SanPhamChiTiet(1, "SPCT01", EntityStatus.ACTIVE, KTser.findById(1).data, Mauser.findById(2).data, SPser.findById(1).data, 20, 1000D));
        list.add(new SanPhamChiTiet(2, "SPCT02", EntityStatus.ACTIVE, KTser.findById(1).data, Mauser.findById(4).data, SPser.findById(2).data, 23, 1000D));
        list.add(new SanPhamChiTiet(3, "SPCT03", EntityStatus.ACTIVE, KTser.findById(2).data, Mauser.findById(3).data, SPser.findById(3).data, 25, 1000D));
        list.add(new SanPhamChiTiet(4, "SPCT04", EntityStatus.ACTIVE, KTser.findById(3).data, Mauser.findById(2).data, SPser.findById(1).data, 29, 1000D));
        list.add(new SanPhamChiTiet(5, "SPCT05", EntityStatus.ACTIVE, KTser.findById(3).data, Mauser.findById(1).data, SPser.findById(2).data, 19, 1000D));
        list.add(new SanPhamChiTiet(6, "SPCT06", EntityStatus.INACTIVE, KTser.findById(3).data, Mauser.findById(5).data, SPser.findById(4).data, 21, 1000D));
        list.add(new SanPhamChiTiet(7, "SPCT07", EntityStatus.ACTIVE, KTser.findById(4).data, Mauser.findById(4).data, SPser.findById(5).data, 20, 1000D));
        list.add(new SanPhamChiTiet(8, "SPCT08", EntityStatus.ACTIVE, KTser.findById(5).data, Mauser.findById(3).data, SPser.findById(2).data, 20, 1000D));
        list.add(new SanPhamChiTiet(9, "SPCT09", EntityStatus.ACTIVE, KTser.findById(6).data, Mauser.findById(2).data, SPser.findById(1).data, 20, 1000D));
        list.add(new SanPhamChiTiet(10, "SPCT010", EntityStatus.ACTIVE, KTser.findById(6).data, Mauser.findById(1).data, SPser.findById(5).data, 20, 1000D));

    }


    @Override
    public ResponseObject<List<SanPhamChiTiet>> getList() {
        return new ResponseObject<List<SanPhamChiTiet>>(false, this.repository.findAll(), "Lấy danh sách SP thành công");
    }

    public ResponseObject<List<SanPhamChiTietDTO>> getListDTO() {
//        repository.saveAll(list);
        return new ResponseObject<List<SanPhamChiTietDTO>>(false, this.repository.getSPCTDTO(), "Lấy danh sách SPDTO thành công");
    }

    public List<SanPhamChiTiet> listSPCTActive() {
        List<SanPhamChiTiet> list = getList().data;
        list.removeIf(o -> o.getTrangThai() == EntityStatus.INACTIVE || o.getTrangThai() == EntityStatus.DELETED);
        return list;
    }

    public List<SanPhamChiTiet> listSPCTSale() {
        List<SanPhamChiTiet> list = this.repository.getListSPCTSale();
        for (SanPhamChiTiet o : list) {
        }
        return this.repository.getListSPCTSale();
    }

    public Map<SanPhamChiTiet, Integer> listSPInCart(Map<Integer, Integer> mapSP) {
        Map<SanPhamChiTiet, Integer> map = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : mapSP.entrySet()) {
            map.put(findById(entry.getKey()).data, entry.getValue());
        }
        return map;
    }


    @Override
    public ResponseObject<SanPhamChiTiet> add(SanPhamChiTiet e) {
        try {
            if (e.getMa().trim().isBlank()
            || e.getDonGia() == null
            || e.getSoLuong() == null) {
                return new ResponseObject<SanPhamChiTiet>(true, e, "Không được để trống các trường mã-đơn giá-số lượng");
            }
            if(Helper.isChuCoDau(e.getMa().trim())){
                return new ResponseObject<SanPhamChiTiet>(true, e, "Mã chỉ chứa các chữ không dấu");
            }
            boolean isExistMa = repository.existsByMa(e.getMa());
            if (isExistMa) {
                return new ResponseObject<SanPhamChiTiet>(true, e, "Mã bị trùng");
            }

            boolean ckSoLuong = e.getSoLuong() > 0;
            if (!ckSoLuong) {
                return new ResponseObject<SanPhamChiTiet>(true, e, "Số lượng phải từ 0");
            }

            boolean ckDonGia = e.getDonGia() > 0;
            if (!ckDonGia) {
                return new ResponseObject<SanPhamChiTiet>(true, e, "Đơn giá phải từ 0");
            }

            this.repository.save(e);
            return new ResponseObject<SanPhamChiTiet>(false, e, "Thêm SP thành công");
        } catch (Exception ex) {
            return new ResponseObject<SanPhamChiTiet>(true, e, "Thêm SP thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<SanPhamChiTiet> update(SanPhamChiTiet e) {

        if (e.getMa().trim().isBlank()
                || e.getDonGia() == null
                || e.getSoLuong() == null) {
            return new ResponseObject<SanPhamChiTiet>(true, e, "Không được để trống các trường mã-đơn giá-số lượng");
        }
        if(Helper.isChuCoDau(e.getMa().trim())){
            return new ResponseObject<SanPhamChiTiet>(true, e, "Mã chỉ chứa các chữ không dấu");
        }

        boolean isExistMa = repository.existsByMa(e.getMa(), e.getId());
        if (isExistMa) {
            return new ResponseObject<SanPhamChiTiet>(true, e, "Mã bị trùng");
        }

        boolean ckSoLuong = e.getSoLuong() > 0;
        if (!ckSoLuong) {
            return new ResponseObject<SanPhamChiTiet>(true, e, "Số lượng phải từ 0");
        }

        boolean ckDonGia = e.getDonGia() >= 0;
        if (!ckDonGia) {
            return new ResponseObject<SanPhamChiTiet>(true, e, "Đơn giá phải từ 0");
        }
//        boolean isExistTen = repository.existsByTen(e.getTen(), e.getId());
//        if(isExistTen){
//            return new ResponseObject<SanPhamChiTiet>(true, e, "Tên đăng nhập bị trùng");
//        }
        try {
            this.repository.save(e);
            return new ResponseObject<SanPhamChiTiet>(false, e, "Sửa SP thành công");
        } catch (Exception ex) {
            return new ResponseObject<SanPhamChiTiet>(true, e, "Sửa SP thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<SanPhamChiTiet> findById(Integer id) {
        try {
            boolean isExists = this.repository.existsById(id);
            if (isExists) {
                return new ResponseObject<SanPhamChiTiet>(false, this.repository.findById(id).orElse(null), "Lấy data SP thành công");
            } else {
                return new ResponseObject<SanPhamChiTiet>(true, null, "SP không tồn tại");
            }
        } catch (Exception ex) {
            return new ResponseObject<SanPhamChiTiet>(true, null, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<SanPhamChiTiet> findByCode(String code) {
        try {
            for (SanPhamChiTiet o : this.repository.findAll()) {
                if (o.getMa().equals(code)) {
                    return new ResponseObject<SanPhamChiTiet>(false, o, "Lấy data SP thành công");
                }
            }
            return new ResponseObject<SanPhamChiTiet>(true, null, "SP không tồn tại");
        } catch (Exception ex) {
            return new ResponseObject<SanPhamChiTiet>(true, null, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<Integer> deleteById(Integer id) {
        try {
            ResponseObject<SanPhamChiTiet> find = this.findById(id);
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
            ResponseObject<SanPhamChiTiet> find = this.findByCode(code);
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

    public List<SanPhamChiTietDTO> search(List<SanPhamChiTietDTO> list, String key) {
        List<SanPhamChiTietDTO> list1 = new ArrayList<>();
        key = key.toLowerCase();
        for (SanPhamChiTietDTO o : list) {
            if (o.getMa().toLowerCase().contains(key)
                    || o.getTenSanPham().toLowerCase().contains(key)
                    || o.getTenMauSac().toLowerCase().contains(key)
                    || o.getTenKichThuoc().toLowerCase().contains(key)) {
                list1.add(o);
            }
        }
        return list1;
    }
}

