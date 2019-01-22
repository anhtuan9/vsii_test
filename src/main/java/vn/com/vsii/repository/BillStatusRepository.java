package vn.com.vsii.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vn.com.vsii.model.BillStatus;

@Repository
public interface BillStatusRepository  extends PagingAndSortingRepository<BillStatus, Long> {
    BillStatus findByName(String name);
}
