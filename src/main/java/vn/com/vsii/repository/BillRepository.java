package vn.com.vsii.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vn.com.vsii.model.Bill;

@Repository
public interface BillRepository  extends PagingAndSortingRepository<Bill, Long> {
    Bill findByTransId (Long id);
}