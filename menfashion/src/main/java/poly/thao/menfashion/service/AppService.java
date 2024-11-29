package poly.thao.menfashion.service;

import poly.thao.menfashion.entity.NhanVien;

public class AppService {
    public static NhanVien currentUser = null;

    public static Boolean isAdmin(NhanVien nv){
        if(nv == null){
            return false;
        }else if(nv.getTenDangNhap().equals("Lananh1@") && nv.getMa().equals("nv1")){
            return true;
        }else {
            return false;
        }
    }

}
