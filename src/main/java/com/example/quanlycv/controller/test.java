package com.example.quanlycv.controller;

import com.example.quanlycv.entity.QlTuyenDung;
import com.example.quanlycv.Rep.TuyenDungRep;
import com.example.quanlycv.Service.DotTuyenDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class test {
    @Autowired
    private DotTuyenDungService dotTuyenDungService;
    @Autowired
    private TuyenDungRep tuyendungRep;




    @GetMapping("/")
    public String viewTest1(){
        return "Menu";
    }


//    @GetMapping("/tuyen-dung")
//    public String listtuyendung( Model model){
//        model.addAttribute("tuyenDung", dotTuyenDungService.findAll());
//        model.addAttribute("qlTuyenDung", new QlTuyenDung());
//
//        return "tuyen-dung";
//    }

    @GetMapping("/tuyen-dung")
    public String listTuyenDung(Model model,
                                @RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                                @Param("keyword") String keyword) {
        LocalDate today = LocalDate.now();

        // Ngày sau 6 tháng
        LocalDate sixMonthsLater = today.plusMonths(6);

        // Định dạng ngày theo kiểu yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String minDate = today.format(formatter); // ngày hiện tại
        String maxDate = sixMonthsLater.format(formatter); // ngày sau 6 tháng

        // Thêm các biến vào model để sử dụng trong view Thymeleaf
        model.addAttribute("minDate", minDate);
        model.addAttribute("maxDate", maxDate);

        Page<QlTuyenDung> tuyenDungPage = dotTuyenDungService.getAllPagination(pageNo);
                model.addAttribute("tuyenDungPage", tuyenDungPage);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", tuyenDungPage.getTotalPages());
        model.addAttribute("qlTuyenDung", new QlTuyenDung());


        return "tuyen-dung";
    }
    @GetMapping("/tuyen-dung/search")
    public String searchTuyenDung(@RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(defaultValue = "1") int pageNo,
                                  Model model) {
        int pageSize = 5; // Số lượng item trên mỗi trang

        Page<QlTuyenDung> tuyenDungPage = dotTuyenDungService.search(keyword, pageNo, pageSize);

        model.addAttribute("tuyenDungPage", tuyenDungPage);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", tuyenDungPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("qlTuyenDung", new QlTuyenDung());

        return "tuyen-dung";
    }



    @PostMapping("/tuyen-dung/add")
    public String addDotTuyenDung(@ModelAttribute QlTuyenDung qlTuyenDung, RedirectAttributes redirectAttributes) {
        dotTuyenDungService.saveDotTuyenDung(qlTuyenDung);
        redirectAttributes.addFlashAttribute("message", "Đợt tuyển dụng đã được thêm thành công!");
        return "redirect:/tuyen-dung";
    }

//    @DeleteMapping("/tuyen-dung/delete/{id}")
//    public String deleteDotTuyenDung(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
//      // System.out.println(id);
//        dotTuyenDungService.deleteDotTuyenDung(id);
//        redirectAttributes.addFlashAttribute("message", "Đợt tuyển dụng đã được xóa thành công!");
//        return "redirect:/tuyen-dung";
//    }
    @GetMapping("/tuyen-dung/delete/{id}")
    public String deleteTuyenDung(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            dotTuyenDungService.deleteDotTuyenDung(id);
            redirectAttributes.addFlashAttribute("message", "Xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Xóa không thành công!");
        }
        return "redirect:/tuyen-dung";
    }


    @GetMapping("/tuyen-dung/detail/{id}")
    public String viewDetail(@PathVariable("id") Integer id, Model model) {



        // Tìm đợt tuyển dụng theo id
        QlTuyenDung tuyenDung = dotTuyenDungService.getTuyenDungById(id);
        // Đưa thông tin đợt tuyển dụng vào model để hiển thị trên trang chi tiết
        model.addAttribute("tuyenDung", tuyenDung);
        return "tuyen-dung-detail";  // Tên file HTML của trang chi tiết
    }

    // Show the update form
//    @GetMapping("/tuyen-dung/edit/{id}")
//    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
//        QlTuyenDung tuyenDung = dotTuyenDungService.getTuyenDungById(id);
//        model.addAttribute("tuyenDung", tuyenDung);
//        return "tuyen-dung-detail"; // Name of the HTML file for the form
//    }

    // Handle the update request
    @PostMapping("/tuyen-dung/update/{id}")
    public String updateTuyenDung(@PathVariable("id") Integer id, @ModelAttribute("tuyenDung") QlTuyenDung tuyenDung,Model model) {
        LocalDate today = LocalDate.now();

        // Ngày sau 6 tháng
        LocalDate sixMonthsLater = today.plusMonths(6);

        // Định dạng ngày theo kiểu yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String minDate = today.format(formatter); // ngày hiện tại
        String maxDate = sixMonthsLater.format(formatter); // ngày sau 6 tháng

        // Thêm các biến vào model để sử dụng trong view Thymeleaf
        model.addAttribute("minDate", minDate);
        model.addAttribute("maxDate", maxDate);

        try {
            dotTuyenDungService.update(id, tuyenDung);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "tuyen-dung-detail"; // Trả về form với thông báo lỗi
        }

      //  dotTuyenDungService.update(id, tuyenDung); // Update method in the service
        return "redirect:/tuyen-dung"; // Redirect back to the list
    }

//    @GetMapping("/tuyen-dung/search")
//    public String searchTuyenDung(@RequestParam("keyword") String keyword, Model model) {
//        // Gọi đến service để tìm kiếm đợt tuyển dụng
//        List<QlTuyenDung> searchResults = dotTuyenDungService.searchTuyenDung(keyword);
//        System.out.println( "++"+searchResults);
//        // Đưa kết quả tìm kiếm vào model
//        model.addAttribute("qlTuyenDung", new QlTuyenDung());
//        model.addAttribute("tuyenDung", searchResults);
//        return "tuyen-dung";
//    }

}





