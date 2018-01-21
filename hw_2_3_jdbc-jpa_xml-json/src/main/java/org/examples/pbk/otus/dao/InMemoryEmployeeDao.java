package org.examples.pbk.otus.dao;

import org.examples.pbk.otus.dao.qualifiers.InMemory;
import org.examples.pbk.otus.model.Employee;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@ApplicationScoped
@InMemory
public class InMemoryEmployeeDao implements EmployeeDao {

    Map<Long, Employee> memory = new TreeMap<>();

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(memory.values());
    }

    @Override
    public Employee findById(long id) {
        return memory.get(id);
    }

    @Override
    public void create(Employee employee) {
        memory.put(employee.getId(), employee);
    }

    @Override
    public void update(Employee employee) {
        memory.replace(employee.getId(), employee);
    }

    @Override
    public void delete(long id) {
        memory.remove(id);
    }
}
