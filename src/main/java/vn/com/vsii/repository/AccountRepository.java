package vn.com.vsii.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vn.com.vsii.model.Account;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
    Account findByUserName (String username);
    Page<Account> findAllByUserName(String username, Pageable pageable);
}
