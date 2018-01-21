package org.examples.pbk.otus.dao;

import org.examples.pbk.otus.dao.qualifiers.InMemory;
import org.examples.pbk.otus.model.Department;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
@InMemory
public class InMemoryDepartmentDao implements DepartmentDao {

    Map<Long, Department> memory = new TreeMap<>();

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(memory.values());
    }

    @Override
    public Department findById(long id) {
        return memory.get(id);
    }

    @Override
    public void create(Department department) {
        memory.put(department.getId(), department);
    }

    @Override
    public void update(Department department) {
        memory.replace(department.getId(), department);
    }

    @Override
    public void delete(long id) {
        memory.remove(id);
    }
}
