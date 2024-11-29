package poly.thao.menfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import poly.thao.menfashion.entity.MauSac;
import poly.thao.menfashion.entity.SanPham;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.model.response.SanPhamDTO;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.repository.SanPhamRepository;
import poly.thao.menfashion.service.base.Service;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class SanPhamService implements Service<SanPham> {


    private final SanPhamRepository repository;
    private List<SanPham> list;

    public SanPhamService(SanPhamRepository repository) {
        this.repository = repository;
        this.list = new ArrayList<>();
        list.add(new SanPham(1, "SP001", EntityStatus.ACTIVE, "San Pham 1"));
        list.add(new SanPham(2, "SP002", EntityStatus.ACTIVE, "San Pham 2"));
        list.add(new SanPham(3, "SP003", EntityStatus.ACTIVE, "San Pham 3"));
        list.add(new SanPham(4, "SP004", EntityStatus.ACTIVE, "San Pham 4"));
        list.add(new SanPham(5, "SP005", EntityStatus.ACTIVE, "San Pham 5"));
        list.add(new SanPham(6, "SP006", EntityStatus.ACTIVE, "San Pham 6"));
        list.add(new SanPham(7, "SP007", EntityStatus.ACTIVE, "San Pham 7"));
        list.add(new SanPham(8, "SP008", EntityStatus.ACTIVE, "San Pham 8"));

    }



    @Override
    public ResponseObject<List<SanPham>> getList() {
//        repository.saveAll(list);
        return new ResponseObject<List<SanPham>>(false, this.repository.findAll(), "Lấy danh sách SP thành công");
    }

    public List<SanPhamDTO> getListDTO(List<SanPham> listSanPham){
        List<SanPhamDTO> listDTO = new ArrayList<>();
        for (SanPham o:listSanPham){
            listDTO.add(new SanPhamDTO(o, soSanPhamChiTiet(o.getId())));
        }
        return listDTO;
    }

    @Override
    public ResponseObject<SanPham> add(SanPham e) {
        try {
            if(e.getMa() == null || e.getMa().isBlank()){
                return new ResponseObject<SanPham>(true, e, "Mã không được trống");
            }
            if(e.getTen() == null || e.getTen().isBlank()){
                return new ResponseObject<SanPham>(true, e, "Tên không được trống");
            }
            if (e.getMa().length() > 10) {
                return new ResponseObject<SanPham>(true, e, "Mã sản phẩm 5 - 10 ký tự");
            }
            String validate = validate(e);
            if(validate != null){
                return new ResponseObject<>(true, e, validate);
            }
            boolean isExistMa = repository.existsByMa(e.getMa());
            if(isExistMa){
                return new ResponseObject<SanPham>(true, e, "Mã bị trùng");
            }

            this.repository.save(e);
            return new ResponseObject<SanPham>(false, e, "Thêm SP thành công");
        } catch (Exception ex) {
            return new ResponseObject<SanPham>(true, e, "Thêm SP thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<SanPham> update(SanPham e) {
        if(e.getMa() == null || e.getMa().isBlank()){
            return new ResponseObject<SanPham>(true, e, "Mã không được trống");
        }
        if(e.getMa().length() > 10){
            return new ResponseObject<SanPham>(true, e, "Mã sản phẩm 5 - 10 ký tự");
        }
        String validate = validate(e);
        if(validate != null){
            return new ResponseObject<>(true, e, validate);
        }
        boolean isExistMa = repository.existsByMa(e.getMa(), e.getId());

        if(isExistMa){
            return new ResponseObject<SanPham>(true, e, "Mã bị trùng");
        }

        if(e.getTen() == null || e.getTen().isBlank()){
            return new ResponseObject<SanPham>(true, e, "Tên không được trống");
        }
        try {
            this.repository.save(e);
            return new ResponseObject<SanPham>(false, e, "Sửa SP thành công");
        } catch (Exception ex) {
            return new ResponseObject<SanPham>(true, e, "Sửa SP thất bại: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<SanPham> findById(Integer id) {

        try {
            boolean  isExists = this.repository.existsById(id);
            if(isExists){
                return new ResponseObject<SanPham>(false, this.repository.findById(id).get(), "Lấy data SP thành công");
            }else {
                return new ResponseObject<SanPham>(true, null, "SP không tồn tại");
            }
        } catch (Exception ex) {
            return new ResponseObject<SanPham>(true, null, "Lấy SP không thành công");
        }
    }

    public ResponseObject<SanPham> findById(long id){

        if (id > Integer.MAX_VALUE || id < Integer.MIN_VALUE){
            return new ResponseObject<SanPham>(true, null, "Id vượt quá giới hạn int");
        }

        if (id < 1) {
            return new ResponseObject<SanPham>(true, null, "Id phải là số lớn hơn 0");
        }

        int idInt = (int) id;
        try {
            boolean  isExists = this.repository.existsById(idInt);
            if(isExists){
                return new ResponseObject<SanPham>(false, this.repository.findById(idInt).get(), "Lấy data SP thành công");
            }else {
                return new ResponseObject<SanPham>(true, null, "SP không tồn tại");
            }
        } catch (Exception ex) {
            return new ResponseObject<SanPham>(true, null, "Lấy SP không thành công");
        }
    }

    @Override
    public ResponseObject<SanPham> findByCode(String code) {
        if(code == null || code.isBlank()){
            return new ResponseObject<SanPham>(true, null, "Mã không được trống");
        }
        if (code.length() > 10 || code.length() < 5) {
            return new ResponseObject<SanPham>(true, null, "Mã phải từ 5 đến 10 kí tự");
        }
        if (!code.matches("^SP\\d{3,10}$")){
            return new ResponseObject<SanPham>(true, null, "Mã không được chứa khoảng trắng và kí tự đặc biệt");
        }
        try {
            for (SanPham o : this.repository.findAll()) {
                if (o.getMa().equals(code)) {
                    return new ResponseObject<SanPham>(false, o, "Lấy data SP thành công");
                }
            }
            return new ResponseObject<SanPham>(true, null, "SP không tồn tại");
        } catch (Exception ex) {
            return new ResponseObject<SanPham>(true, null, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<Integer> deleteById(Integer id) {
        try {
            ResponseObject<SanPham> find = this.findById(id);
            if (find.isHasError) {
                return new ResponseObject<Integer>(true, id, "Xóa không thành công");
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
            ResponseObject<SanPham> find = this.findByCode(code);
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

    public Integer soSanPhamChiTiet(Integer idSp){
        return repository.countSPCTbySanPhamId(idSp);
    }

    public List<SanPham> search(List<SanPham> list, String key) {
        List<SanPham> list1 = new ArrayList<>();
        key = key.toLowerCase();
        for (SanPham o: list){
            if(o.getMa().toLowerCase().contains(key)
            || o.getTen().toLowerCase().contains(key)){
                list1.add(o);
            }
        }
        return list1;
    }

    public String validate(SanPham e){

        String regexMa = "^SP\\d{3,10}$";
        String regexTen = "^[a-zA-ZÀ-ỹ\\s]{5,50}$";

        if(!e.getMa().matches(regexMa)){
            return  "Mã sản phẩm 5 - 10 ký tự";
        }
        if(!e.getTen().matches(regexTen)){
            return  "Tên sản phẩm cần 5-50 ký tự, không số và ký tự đặc biệt";
        }
        return null;
    }
}

