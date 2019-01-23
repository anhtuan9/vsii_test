package vn.com.vsii.service;

import vn.com.vsii.model.Bill;
import vn.com.vsii.model.BillStatus;


public interface BillStatusService {
    Iterable<BillStatus> findAll();

    BillStatus findById(Long id);

    void save(BillStatus bill);

    void remove(Long id);

    BillStatus findByName(String name);
}

