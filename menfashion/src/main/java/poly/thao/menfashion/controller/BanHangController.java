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
import poly.thao.menfashion.model.response.ValidationResult;
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
            @RequestParam(value = "sanPhamsInCart", required = false) String[] selectedSP,
            @RequestParam Map<String, String> allParams,
            Model model, RedirectAttributes red) {

        if (currentUser() == null) {
            return "redirect:/loi";
        }
        try {
            ValidationResult validationResult = validatePayConfirm(selectedSP, allParams, cart, sanPhamChiTietService);
            if (!validationResult.isValid()) {
                return handleError(red, cart, validationResult.getMessage(), validationResult.getRedirectUrl());
            }
            updateCart(selectedSP, allParams);
            String idKH = allParams.get("idKhachHang");
            this.cart.setIdKhachHang(Integer.parseInt(idKH));
            this.cart.setIdNhanVien(currentUser().getId());
            prepareModelForView(model);

            return "banhang/pay";
        } catch (Exception ex) {
            return handleError(red, cart, "Đã có lỗi xảy ra, vui lòng chọn lại thông tin hóa đơn", "redirect:/ban-hang");
        }
    }

    public ValidationResult validatePayConfirm(
            String[] selectedSP, Map<String, String> allParams, Cart cart, SanPhamChiTietService sanPhamChiTietService) {
        if (selectedSP == null || selectedSP.length == 0) {
            return new ValidationResult(false, "redirect:/ban-hang", "Vui lòng chọn sản phẩm!");
        }
        for (String productId : selectedSP) {
            int id = Integer.parseInt(productId);
            String sl = allParams.get("soLuongs[" + id + "]");
            if (sl == null) {
                return new ValidationResult(false, "redirect:/ban-hang", "Số lượng must not null!");
            }
            try {
                int quantity = sl.isBlank() ? 0 : Integer.parseInt(sl);
                SanPhamChiTiet spct = sanPhamChiTietService.findById(id).data;

                if (spct == null) {
                    return new ValidationResult(false, "redirect:/ban-hang", "SPCT not found!");
                }
                if (quantity < 0 || quantity > spct.getSoLuong()) {
                    return new ValidationResult(false, "redirect:/ban-hang", "Số lượng nhập không hợp lệ, vui lòng nhập lại!");
                }
            }catch (NumberFormatException e){
                throw new NumberFormatException("Quantity must be integer");
            }

        }
        if (allParams.get("idKhachHang") == null || allParams.get("idKhachHang").isBlank()) {
            return new ValidationResult(false, "redirect:/ban-hang", "Vui lòng chọn khách hàng!");
        } else {
            KhachHang kh = khachHangService.findById(Integer.parseInt(allParams.get("idKhachHang"))).data;
            if (kh == null) {
                return new ValidationResult(false, "redirect:/ban-hang", "KH not found!");
            }
        }
        return new ValidationResult(true, null, null);
    }

    private void updateCart(String[] selectedSP, Map<String, String> allParams) {
        if (this.cart.mapSanPham == null) {
            this.cart.mapSanPham = new HashMap<>();
        } else {
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
    }

    private void prepareModelForView(Model model) {
        model.addAttribute("ngayMuaHang", LocalDate.now());
        model.addAttribute("khachHang", khachHangService.findById(cart.idKhachHang).data);
        model.addAttribute("nhanVien", currentUser());

        Map<SanPhamChiTiet, Integer> listSP = sanPhamChiTietService.listSPInCart(cart.getMapSanPham());
        model.addAttribute("listSP", listSP);
        model.addAttribute("amount", service.getAmount(listSP));
    }

    private String handleError(RedirectAttributes red, Cart cart, String message, String redirectUrl) {
        red.addFlashAttribute("cart", cart);
        red.addFlashAttribute("mess", message);
        return redirectUrl;
    }


    @PostMapping("/pay")
    public String pay(Model model, RedirectAttributes red) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }

        ResponseObject<String> pay = service.pay(this.cart);
        this.cart = new Cart(currentUser().getId(), null, new HashMap<>());
        red.addFlashAttribute("paid", pay);
        return "redirect:/ban-hang";
    }

}
