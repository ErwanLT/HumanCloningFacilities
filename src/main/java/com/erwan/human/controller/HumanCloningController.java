package com.erwan.human.controller;

import com.erwan.human.dao.CloneRepository;
import com.erwan.human.domaine.Clone;
import com.erwan.human.exceptions.BeanNotFound;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kamino/clones")
public class HumanCloningController {

    @Autowired
    private CloneRepository repository;

    @Operation(summary = "Find all clones", description = "Find all clones present in database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found clones", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Clone.class)))}),
            @ApiResponse(responseCode = "404", description = "No clones found", content = @Content) })
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_KAMINOAIN', 'ROLE_EMPEROR')")
    public List<Clone> findAll() throws BeanNotFound {
        List<Clone> cloneList = repository.findAll();
        if(cloneList.isEmpty()){
            throw new BeanNotFound("Can't find any clone");
        }
        return cloneList;
    }

    @Operation(summary = "Create a clone", description = "Create a clone with the information of the body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clone created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Clone.class)) }),
            @ApiResponse(responseCode = "500", description = "An error occured.", content = @Content) })
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_KAMINOAIN', 'ROLE_EMPEROR')")
    public Clone createClone(@RequestBody Clone clone){
        return repository.save(clone);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_KAMINOAIN', 'ROLE_EMPEROR')")
    @Operation(description = "Find a clone by it's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clone found",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Clone.class)) }),
            @ApiResponse(responseCode = "404", description = "No clones found", content = @Content)
    })
    public Clone findById(@Parameter(name = "The clone ID", example = "12",required = true) @PathVariable("id") Long id) throws BeanNotFound {
        return getOne(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_KAMINOAIN', 'ROLE_EMPEROR')")
    public void delete(@PathVariable("id") Long id) throws BeanNotFound {
        Clone clone = getOne(id);
        repository.delete(clone);
    }

    @PutMapping("/order66")
    @PreAuthorize("hasAuthority('ROLE_EMPEROR')")
    public List<Clone> executeOrder66(){
        List<Clone> clones = repository.findAll();
        clones.stream().forEach(clone -> clone.setAffiliation("Galactic Empire"));
        return repository.saveAll(clones);
    }

    protected Clone getOne(Long id) throws BeanNotFound {
        Optional<Clone> clone = repository.findById(id);
        if(!clone.isPresent()){
            throw new BeanNotFound("Can't find clone with id : " + id);
        }
        return clone.get();
    }

}
