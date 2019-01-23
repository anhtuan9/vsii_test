package vn.com.vsii.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.vsii.model.BillStatus;
import vn.com.vsii.repository.BillStatusRepository;
import vn.com.vsii.service.BillStatusService;

@Service
public class BillStatusServiceImpl implements BillStatusService {
    @Autowired
    private BillStatusRepository billStatusRepository;
    @Override
    public Iterable<BillStatus> findAll() {
        return billStatusRepository.findAll();
    }

    @Override
    public BillStatus findById(Long id) {
        return billStatusRepository.findById(id).orElse(null);
    }

    @Override
    public void save(BillStatus billStatus) {
        billStatusRepository.save(billStatus);
    }

    @Override
    public void remove(Long id) {
        billStatusRepository.deleteById(id);
    }

    @Override
    public BillStatus findByName(String name) {
        return billStatusRepository.findByName(name);
    }
}
