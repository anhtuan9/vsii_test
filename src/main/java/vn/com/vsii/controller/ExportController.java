package vn.com.vsii.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.com.vsii.model.Bill;
import vn.com.vsii.service.BillService;

import java.util.Optional;

@Controller
public class ExportController {
    @Autowired
    private BillService billService;

    @GetMapping("/sup")
    public ModelAndView listBill(@RequestParam("s") Optional<String> s, Pageable pageable) {
        Page<Bill> bills = billService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("sup/list");
        modelAndView.addObject("bills", bills);
        return modelAndView;
    }
    @GetMapping("/views-bill/{id}")
    public ModelAndView showViews(@PathVariable Long id) {
        Bill bill = billService.findById(id);
        if (bill != null) {
            ModelAndView modelAndView = new ModelAndView("/sup/view");
            modelAndView.addObject("bill", bill);
            return modelAndView;

        } else {
            return new ModelAndView("/error.404");
        }
    }

}
