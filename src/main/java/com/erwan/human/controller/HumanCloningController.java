package com.erwan.human.controller;

import com.erwan.human.dao.CloneRepository;
import com.erwan.human.domaine.Clone;
import com.erwan.human.exceptions.BeanNotFound;
import com.erwan.human.services.BarCodeService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kamino/clones")
@OpenAPIDefinition(info = @Info(title = "Human cloning API",
                                description = "API for creating clone who will fight in the clones wars",
                                version = "2.0",
                                contact = @Contact(
                                        name = "LE TUTOUR Erwan",
                                        email = "erwanletutour.elt@gmail.com",
                                        url = "https://github.com/ErwanLT"
                                ),
                                license = @License(
                                        name = "MIT Licence",
                                        url = "https://opensource.org/licenses/mit-license.php"
                                )
))
public class HumanCloningController {

    private static final Logger LOG = LoggerFactory.getLogger(HumanCloningController.class);

    @Autowired
    private CloneRepository repository;

    @Autowired
    private BarCodeService barCodeService;

    @Operation(summary = "Find all clones", description = "Find all clones present in database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found clones", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Clone.class)))}),
            @ApiResponse(responseCode = "404", description = "No clones found", content = @Content) })
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_KAMINOAIN', 'ROLE_EMPEROR')")
    public List<Clone> findAll() throws BeanNotFound {
        LOG.info("searching all clones");
        List<Clone> cloneList = repository.findAll();
        if(cloneList.isEmpty()){
            throw new BeanNotFound("Can't find any clone");
        }
        LOG.info("find {} clone(s)", cloneList.size());
        LOG.info("clones : {}", cloneList);
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
        LOG.info("create clone : {}", clone);
        return repository.save(clone);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_KAMINOAIN', 'ROLE_EMPEROR')")
    @Operation(summary = "Find one clone", description = "Find a clone by it's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clone found",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Clone.class)) }),
            @ApiResponse(responseCode = "404", description = "No clones found", content = @Content)
    })
    public Clone findById(@Parameter(name = "The clone ID", example = "12",required = true) @PathVariable("id") Long id) throws BeanNotFound {
        LOG.info("Searching clone by id : {}", id);
        return getOne(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_KAMINOAIN', 'ROLE_EMPEROR')")
    public void delete(@PathVariable("id") Long id) throws BeanNotFound {
        Clone clone = getOne(id);
        LOG.info("Deleting clone : {}", clone);
        repository.delete(clone);
    }

    @PutMapping("/order66")
    @PreAuthorize("hasAuthority('ROLE_EMPEROR')")
    public List<Clone> executeOrder66(){
        List<Clone> clones = repository.findAll();
        clones.forEach(clone -> clone.setAffiliation("Galactic Empire"));
        return repository.saveAll(clones);
    }

    @GetMapping(value = "/generateQR/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_KAMINOAIN', 'ROLE_EMPEROR')")
    public @ResponseBody byte[] generateQRCode(@PathVariable("id") Long id) throws Exception {
        Clone clone = getOne(id);
        return barCodeService.generateQRCodeImage(clone.toString());
    }

    protected Clone getOne(Long id) throws BeanNotFound {
        Optional<Clone> clone = repository.findById(id);
        if(clone.isEmpty()){
            throw new BeanNotFound("Can't find clone with id : " + id);
        }
        return clone.get();
    }

}
