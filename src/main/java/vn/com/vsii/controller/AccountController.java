package vn.com.vsii.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.com.vsii.model.Account;
import vn.com.vsii.model.Role;
import vn.com.vsii.repository.RoleRepository;
import vn.com.vsii.service.AccountService;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @ModelAttribute("roles")
    public Iterable<Role> roles() {
        return roleRepository.findAll();
    }


    @GetMapping("/admin/account/create-account")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/admin/account/create");
        modelAndView.addObject("account", new Account());
        return modelAndView;
    }

    @PostMapping("/create-account")
    public ModelAndView saveUser(@Validated @ModelAttribute("account")Account account, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()){
            return new ModelAndView("/admin/account/create");
        }else {
            if (accountService.findByUserName(account.getUserName()) == null) {
                account.setPassword(passwordEncoder.encode(account.getPassword()));
                accountService.setupRole(account.getRole(),account);
                accountService.save(account);
                ModelAndView modelAndView = new ModelAndView("/admin/account/create");
                modelAndView.addObject("account", new Account());
                modelAndView.addObject("message", "New account created successfully");
                return modelAndView;
            } else {
                    ModelAndView modelAndView = new ModelAndView("/admin/account/create");
                    modelAndView.addObject("account", new Account());
                    modelAndView.addObject("message", "Tên bị trùng");
                    return modelAndView;
            }
        }}


    @GetMapping("/admin/account")
    public ModelAndView listUser(@RequestParam("s") Optional<String> s,
                                 @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                 @RequestParam(name = "size", required = false, defaultValue = "6") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accounts;
        if (s.isPresent()) {
            accounts = accountService.findAllByStudentName(s.get(), pageable);
        } else {
            accounts = accountService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("admin/account/list");
        modelAndView.addObject("accounts", accounts);
        return modelAndView;
    }

    @GetMapping("/edit-account/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Account account = accountService.findById(id);
        if (account != null) {
            ModelAndView modelAndView = new ModelAndView("/admin/account/edit");
            modelAndView.addObject("account", account);
            return modelAndView;

        } else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/edit-account")
    public ModelAndView updateUser(@ModelAttribute("account") Account account) {
        accountService.setupRole(account.getRole(),account);
        accountService.save(account);
        ModelAndView modelAndView = new ModelAndView("/admin/account/edit");
        modelAndView.addObject("account", account);
        modelAndView.addObject("message", "updated successfully");
        return modelAndView;
    }

    @GetMapping("/admin/delete-account/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Account account = accountService.findById(id);
        if (account != null) {
            ModelAndView modelAndView = new ModelAndView("/admin/account/delete");
            modelAndView.addObject("account", account);
            return modelAndView;

        } else {
            return new ModelAndView("admin/account/error.404");
        }
    }

    @PostMapping("/admin/delete-account")
    public String deleteUser(@ModelAttribute("account") Account account) {
        accountService.remove(account.getId());
        return "redirect:/admin/account";
    }
    @GetMapping("user/change_password")
    public ModelAndView showChangePass(){
        return new ModelAndView("/user/change_password");
    }

    @PostMapping("/change_password")
    public ModelAndView ChangePass(Principal principal,
                                   @RequestParam(name = "oldpassword") String oldpassword,
                                   @RequestParam(name = "newpassword") String newpassword,
                                   @RequestParam(name = "newpassword1") String newpassword1){
        ModelAndView modelAndView = new ModelAndView("user/change_password");
        Account account = accountService.findByUserName(principal.getName());
        String oldpass = (passwordEncoder.encode(oldpassword));
        if (account.getPassword().equals(oldpass)){
            if (newpassword.equals(newpassword1)){
                account.setPassword(passwordEncoder.encode(newpassword));
                accountService.save(account);
                modelAndView.addObject("message", "updated successfully");
                return modelAndView;
            }else {
                modelAndView.addObject("message", "mật khẩu không trùng khớp");
                return modelAndView;
            }
        }else {
            modelAndView.addObject("message", "mật khẩu cũ không trùng khớp");
            return modelAndView;
        }
    }
    @GetMapping("/user")
    public ModelAndView ShowInfo(Principal principal){
        ModelAndView modelAndView = new ModelAndView("user/view");
        Account account = accountService.findByUserName(principal.getName());
        modelAndView.addObject("account", account);
        return modelAndView;
    }

}
