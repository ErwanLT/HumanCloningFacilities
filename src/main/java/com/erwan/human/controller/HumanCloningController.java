package com.erwan.human.controller;

import com.erwan.human.dao.CloneRepository;
import com.erwan.human.domaine.Clone;
import com.erwan.human.exceptions.BeanNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kamino")
public class HumanCloningController {

    @Autowired
    private CloneRepository repository;

    @GetMapping("/")
    public List<Clone> findAll() {
        return repository.findAll();
    }

    @GetMapping("/pages")
    public Page<Clone> findAllPages(@PageableDefault(page = 0, size = 20)
                                        @SortDefault.SortDefaults({
                                                @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                                        }) Pageable pageable) {
        return repository.findAll(pageable);
    }

    @PostMapping()
    public Clone createClone(@RequestBody Clone clone){
        return repository.save(clone);
    }

    @GetMapping("/{id}")
    public Clone findById(@PathVariable("id") Long id) throws BeanNotFound {
        return getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) throws BeanNotFound {
        Clone clone = getOne(id);
        repository.delete(clone);
    }

    protected Clone getOne(Long id) throws BeanNotFound {
        Optional<Clone> clone = repository.findById(id);
        if(!clone.isPresent()){
            throw new BeanNotFound("Can't find clone with id : " + id);
        }
        return clone.get();
    }

}
