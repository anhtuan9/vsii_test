package vn.com.vsii.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.com.vsii.model.Bill;
import vn.com.vsii.model.BillStatus;
import vn.com.vsii.service.BillService;
import vn.com.vsii.service.BillStatusService;

import java.security.Principal;
import java.util.Date;
import java.util.Random;

@Controller
public class TransactionController {

    @Autowired
    private BillService billService;
    @Autowired
    private BillStatusService billStatusService;

    @ModelAttribute("billstatus")
    public Iterable<BillStatus> roles() {
        return billStatusService.findAll();
    }

    @GetMapping("/teller/create")
    public ModelAndView showCreateBillForm() {
        ModelAndView modelAndView = new ModelAndView("/teller/create");
        modelAndView.addObject("bill", new Bill());
        return modelAndView;
    }

    @PostMapping("/create-bill")
    public ModelAndView saveBill(@ModelAttribute("bill") Bill bill, Principal principal) {
        Date date = new Date();
        bill.setCreate_date(date);
        bill.setCreate_user(principal.getName());
        Random rd = new Random();
        while (true) {
            int number = rd.nextInt() + 1000000;
            if (billService.findByTransId(number) == null && number > 0) {
                bill.setTransId(number);
                break;
            }
        }
        billService.save(bill);
        ModelAndView modelAndView = new ModelAndView("/teller/create");
        modelAndView.addObject("bill", new Bill());
        modelAndView.addObject("message", "New bill created successfully");
        return modelAndView;

    }

    @GetMapping("/teller")
    public ModelAndView listBills(Pageable pageable) {
        Page<Bill> bills = billService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("teller/list");
        modelAndView.addObject("bills", bills);
        return modelAndView;
    }

    @GetMapping("/delete-bill/{id}")
    public ModelAndView showDeleteBillForm(@PathVariable Long id) {
        Bill bill = billService.findById(id);
        if (bill != null) {
            ModelAndView modelAndView = new ModelAndView("/teller/delete");
            modelAndView.addObject("bill", bill);
            return modelAndView;

        } else {
            return new ModelAndView("teller/error.404");
        }
    }

    @PostMapping("/delete-bill")
    public String deleteBill(@ModelAttribute("bill") Bill bill) {
        billService.remove(bill.getBill_id());
        return "redirect:/teller/";
    }

    @GetMapping("/agree-bill-status/{id}")
    public String agree(@PathVariable Long id) {
        Bill bill = billService.findById(id);
        if (bill != null) {
            BillStatus billStatus = billStatusService.findByName("approved");
            bill.setTrans_status_code(billStatus);
            return "redirect:/teller";
        } else {
            return "/error.404";
        }
    }

    @GetMapping("/refuse-bill-status/{id}")
    public String refuse(@PathVariable Long id) {
        Bill bill = billService.findById(id);
        if (bill != null) {
            BillStatus billStatus = billStatusService.findByName("refuse");
            bill.setTrans_status_code(billStatus);
            return "redirect:/teller";
        } else {
            return "/error.404";
        }
    }
}
