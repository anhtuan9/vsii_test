package vn.com.vsii.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.vsii.model.Bill;
import vn.com.vsii.repository.BillRepository;
import vn.com.vsii.service.BillService;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;
    @Override
    public Page<Bill> findAll(Pageable pageable) {
        return billRepository.findAll(pageable);
    }

    @Override
    public Bill findById(Long id) {
        return billRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Bill bill) {
        billRepository.save(bill);
    }

    @Override
    public void remove(Long id) {
        billRepository.deleteById(id);
    }

    @Override
    public Bill findByTransId(long id) {
        return billRepository.findByTransId(id);
    }
}
