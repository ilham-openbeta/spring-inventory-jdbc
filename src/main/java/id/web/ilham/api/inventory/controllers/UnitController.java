package id.web.ilham.api.inventory.controllers;

import id.web.ilham.api.inventory.entities.Unit;
import id.web.ilham.api.inventory.exceptions.EntityNotFoundException;
import id.web.ilham.api.inventory.models.ResponseMessage;
import id.web.ilham.api.inventory.models.unit.UnitRequest;
import id.web.ilham.api.inventory.models.unit.UnitResponse;
import id.web.ilham.api.inventory.services.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/units")
@RestController
public class UnitController {

    @Autowired
    private UnitService service;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Add unit", description = "Add an unit for item measurement", tags = {"unit"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseMessage<UnitResponse> add(@RequestBody @Valid UnitRequest model) {
        Unit entity = modelMapper.map(model, Unit.class);
        service.save(entity);
        UnitResponse data = modelMapper.map(entity, UnitResponse.class);
        return ResponseMessage.success(data);
    }

    @Operation(summary = "Edit unit", description = "Edit an unit for item measurement", tags = {"unit"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseMessage<UnitResponse> edit(@PathVariable Integer id, @RequestBody @Valid UnitRequest model) {
        Unit entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        modelMapper.map(model, entity);
        service.save(entity);

        UnitResponse data = modelMapper.map(entity, UnitResponse.class);
        return ResponseMessage.success(data);
    }

    @Operation(summary = "Delete unit", description = "Delete an unit", tags = {"unit"}, security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseMessage<String> removeById(@PathVariable Integer id) {
        if (!service.removeById(id)) {
            throw new EntityNotFoundException();
        }
        String data = "Unit with id " + id + " is successfully deleted";

        return ResponseMessage.success(data);
    }

    @Operation(summary = "Find unit", description = "Find an unit", tags = {"unit"})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseMessage<UnitResponse> findById(@PathVariable Integer id) {
        Unit entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        UnitResponse data = modelMapper.map(entity, UnitResponse.class);

        return ResponseMessage.success(data);
    }

    @Operation(summary = "Get all unit", description = "Get all unit", tags = {"unit"})
    @GetMapping(produces = "application/json")
    public ResponseMessage<List<Unit>> findAll(
    ) {
        List<Unit> data = service.findAll();
        return ResponseMessage.success(data);
    }

}
