package poly.thao.menfashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import poly.thao.menfashion.entity.HoaDon;
import poly.thao.menfashion.entity.HoaDonChiTiet;
import poly.thao.menfashion.entity.KhachHang;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.entity.SanPhamChiTiet;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.model.request.Cart;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.service.AppService;
import poly.thao.menfashion.service.BanHangService;
import poly.thao.menfashion.service.HoaDonChiTietService;
import poly.thao.menfashion.service.HoaDonService;
import poly.thao.menfashion.service.KhachHangService;
import poly.thao.menfashion.service.NhanVienService;
import poly.thao.menfashion.service.SanPhamChiTietService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ban-hang")
public class BanHangController {

    @Autowired
    private BanHangService service;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @ModelAttribute("currentUser")
    private NhanVien currentUser() {
        return AppService.currentUser;
    }

    @ModelAttribute("isAdmin")
    private Boolean isAdmin() {
        return AppService.isAdmin(AppService.currentUser);
    }


    private Cart cart = new Cart();

    @ModelAttribute("cart")
    private Cart getCart() {
        return this.cart;
    }


    @GetMapping("")
    public String dashBoard(Model model) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        model.addAttribute("listKH", khachHangService.getList().data);
        model.addAttribute("listSPCT", sanPhamChiTietService.listSPCTSale());
        return "banhang/dash-board";
    }

    @PostMapping("/pay/confirm")
    public String payConfirm(
            @RequestParam(value = "sanPhamsInCart",required = false) String[] selectedSP,
            @RequestParam Map<String, String> allParams,
            Model model, RedirectAttributes red) {

        if (currentUser() == null) {
            return "redirect:/loi";
        }

        if(selectedSP == null || selectedSP.length == 0){
            red.addFlashAttribute("cart",cart);
            red.addFlashAttribute("mess", "Vui lòng chọn sản phẩm!");
            return "redirect:/ban-hang";
        }

        if (this.cart.mapSanPham == null) {
            this.cart.mapSanPham = new HashMap<>();
        }else {
            this.cart.mapSanPham.clear();
        }
        for (String productId : selectedSP) {
            int id = Integer.parseInt(productId);
            String sl = allParams.get("soLuongs[" + id + "]");
            int quantity = sl.isBlank() ? 0 : Integer.parseInt(sl);

            if (quantity > 0) {
                this.cart.getMapSanPham().put(id, quantity);
            }
        }
        if(cart.mapSanPham.isEmpty()){
            red.addFlashAttribute("cart", cart);
            red.addFlashAttribute("mess", "Vui lòng nhập đầy đủ thông tin số lượng sản phẩm cần bán!");
            return "redirect:/ban-hang";
        }

        String idKH = allParams.get("idKhachHang");
        if(idKH == null || idKH.isBlank()){
            red.addFlashAttribute("cart", cart);
            red.addFlashAttribute("mess", "Vui lòng chọn khách hàng!");
            return "redirect:/ban-hang";
        }
        Integer idKhachHang = Integer.parseInt(idKH);
        this.cart.setIdKhachHang(idKhachHang);
        this.cart.setIdNhanVien(currentUser().getId());
        System.out.println(this.cart);

        model.addAttribute("ngayMuaHang", LocalDate.now());
        model.addAttribute("khachHang", khachHangService.findById(cart.idKhachHang).data);
        model.addAttribute("nhanVien", currentUser());

        Map<SanPhamChiTiet, Integer> listSP = sanPhamChiTietService.listSPInCart(cart.getMapSanPham());
        model.addAttribute("listSP", listSP);
        model.addAttribute("amount", service.getAmount(listSP));

        return "banhang/pay";
    }

    @PostMapping("/pay")
    public String pay(Model model, RedirectAttributes red) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }

        ResponseObject<String> pay = this.pay(this.cart);
        this.cart = new Cart(currentUser().getId(), null, new HashMap<>());
        red.addFlashAttribute("paid", pay);
        return "redirect:/ban-hang";
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseObject<String> pay(Cart cart) {
        try {
            NhanVien nv = AppService.currentUser;
            KhachHang kh = khachHangService.findById(cart.idKhachHang).data;

            HoaDon hoaDon = new HoaDon();
            hoaDon.setNhanVien(nv);
            hoaDon.setKhachHang(kh);
            hoaDon.setTrangThai(EntityStatus.ACTIVE);
            ResponseObject<HoaDon> savedHoadon = hoaDonService.add(hoaDon);
            if (savedHoadon.isHasError) {
                throw new RuntimeException(savedHoadon.message);
            }

            for (Map.Entry<Integer, Integer> sp : cart.mapSanPham.entrySet()) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setHoaDon(savedHoadon.data);
                SanPhamChiTiet spct = sanPhamChiTietService.findById(sp.getKey()).data;
                hoaDonChiTiet.setSanPhamChiTiet(spct);
                hoaDonChiTiet.setDonGia(spct.getDonGia());
                hoaDonChiTiet.setSoLuong(sp.getValue());
                hoaDonChiTiet.setTrangThai(EntityStatus.ACTIVE);
                ResponseObject<HoaDonChiTiet> savedHDCT = hoaDonChiTietService.add(hoaDonChiTiet);

                if (savedHDCT.isHasError) {
                    throw new RuntimeException(savedHDCT.message);
                }
                spct.setSoLuong(spct.getSoLuong() - sp.getValue());
                sanPhamChiTietService.update(spct);
            }

            return new ResponseObject<>(false, null, "Thanh toán thành công.");
        } catch (Exception ex) {
            return new ResponseObject<>(true, null, "Lỗi: " + ex.getMessage());
        }
    }

}
