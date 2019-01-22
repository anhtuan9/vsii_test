package vn.com.vsii.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vn.com.vsii.model.Role;

@Repository
public interface RoleRepository  extends PagingAndSortingRepository<Role, Long> {
    Role findByName (String name);
}