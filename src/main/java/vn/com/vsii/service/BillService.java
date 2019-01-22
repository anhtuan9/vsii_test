package vn.com.vsii.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.com.vsii.model.Bill;

public interface BillService {
    Page<Bill> findAll(Pageable pageable);

    Bill findById(Long id);

    void save(Bill bill);

    void remove(Long id);

    Bill findByTransId(long id);

}
