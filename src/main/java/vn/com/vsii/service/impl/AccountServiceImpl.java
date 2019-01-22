package vn.com.vsii.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.com.vsii.model.Account;
import vn.com.vsii.model.Role;
import vn.com.vsii.repository.AccountRepository;
import vn.com.vsii.repository.RoleRepository;
import vn.com.vsii.service.AccountService;

import java.util.HashSet;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Account user) {
        accountRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public void setupRole(String role, Account account) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(account.getRole()));
        account.setRoles(roles);
        accountRepository.save(account);
    }

    @Override
    public Page<Account> findAllByStudentName(String nickname, Pageable pageable) {
        return null;
    }

    @Override
    public Account findByUserName(String username) {
        return accountRepository.findByUserName(username);
    }
}