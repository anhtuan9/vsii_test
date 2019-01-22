package vn.com.vsii.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.com.vsii.model.Account;

public interface AccountService {
    Page<Account> findAll(Pageable pageable);

    Account findById(Long id);

    void save(Account user);

    void remove(Long id);

    void setupRole(String role, Account account);

    Page<Account> findAllByStudentName(String nickname, Pageable pageable);

    Account findByUserName(String username);
}
