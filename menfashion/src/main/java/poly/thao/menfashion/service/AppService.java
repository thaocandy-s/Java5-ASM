package poly.thao.menfashion.service;

import poly.thao.menfashion.entity.NhanVien;

public class AppService {
    public static NhanVien currentUser = null;

    public static Boolean isAdmin(NhanVien nv){
        if(nv == null){
            return false;
        }else if(nv.getTenDangNhap().equals("Hieu1197@") && nv.getMa().equals("NV1")){
            return true;
        }else {
            return false;
        }
    }
}
