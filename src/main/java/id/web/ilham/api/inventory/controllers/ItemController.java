package id.web.ilham.api.inventory.controllers;

import id.web.ilham.api.inventory.entities.Item;
import id.web.ilham.api.inventory.entities.ItemWithUnit;
import id.web.ilham.api.inventory.entities.Unit;
import id.web.ilham.api.inventory.exceptions.EntityNotFoundException;
import id.web.ilham.api.inventory.exceptions.UnitNotFoundException;
import id.web.ilham.api.inventory.models.ImageUploadRequest;
import id.web.ilham.api.inventory.models.ResponseMessage;
import id.web.ilham.api.inventory.models.item.ItemRequest;
import id.web.ilham.api.inventory.models.item.ItemResponse;
import id.web.ilham.api.inventory.models.unit.UnitResponse;
import id.web.ilham.api.inventory.services.FileService;
import id.web.ilham.api.inventory.services.ItemService;
import id.web.ilham.api.inventory.services.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;

@RequestMapping("/api/items")
@RestController
public class ItemController {

    @Autowired
    private ItemService service;

    @Autowired
    private UnitService unitService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Add item", description = "Add an item without image", tags = {"item"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseMessage<ItemResponse> add(@RequestBody @Valid ItemRequest model) {
        Unit unit = unitService.findById(model.getUnitId());
        if (unit == null) {
            throw new UnitNotFoundException();
        }
        Item entity = modelMapper.map(model, Item.class);
        service.save(entity);
        UnitResponse unitResponse = modelMapper.map(unit, UnitResponse.class);
        ItemResponse data = modelMapper.map(entity, ItemResponse.class);
        data.setUnitResponse(unitResponse);
        return ResponseMessage.success(data);
    }

    @Operation(summary = "Edit item", description = "Edit an item without image", tags = {"item"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseMessage<ItemResponse> edit(@PathVariable Integer id, @RequestBody @Valid ItemRequest model) {
        Unit unit = unitService.findById(model.getUnitId());
        if (unit == null) {
            throw new UnitNotFoundException();
        }

        Item entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        modelMapper.map(model, entity);
        service.save(entity);
        UnitResponse unitResponse = modelMapper.map(unit, UnitResponse.class);
        ItemResponse data = modelMapper.map(entity, ItemResponse.class);
        data.setUnitResponse(unitResponse);
        return ResponseMessage.success(data);
    }

    @Operation(summary = "Delete item", description = "Delete an item", tags = {"item"}, security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseMessage<String> removeById(@PathVariable Integer id) {
        if (!service.removeById(id)) {
            throw new EntityNotFoundException();
        }
        String data = "Item with id " + id + " is successfully deleted";
        return ResponseMessage.success(data);
    }

    @Operation(summary = "Find item", description = "Find an Item", tags = {"item"})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseMessage<ItemResponse> findById(@PathVariable Integer id) {
        ItemWithUnit entity = service.findByIdWithUnit(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        UnitResponse unitResponse = modelMapper.map(entity.getUnit(), UnitResponse.class);
        ItemResponse data = modelMapper.map(entity, ItemResponse.class);
        data.setUnitResponse(unitResponse);
        return ResponseMessage.success(data);
    }

    @Operation(summary = "Get all item", description = "Get all item", tags = {"item"})
    @GetMapping(produces = "application/json")
    public ResponseMessage<List<Item>> findAll(
    ) {
        List<Item> data = service.findAll();
        return ResponseMessage.success(data);
    }

    @Operation(summary = "Add image", description = "Add an image to item. Note: Authorization bearer token is required", tags = {"item"})
    @PostMapping(value = "/{id}/image", consumes = "multipart/form-data", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseMessage<ItemResponse> upload(@PathVariable Integer id,
                                                @Valid ImageUploadRequest model) throws Exception {
        Item entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(model.getFile().getInputStream());
        String filename = fileService.upload("item", bufferedInputStream);
        entity.setImageUrl("/api/items/image/" + filename);
        entity.setOriginalFilename(model.getFile().getOriginalFilename());
        service.save(entity);
        ItemResponse itemResponse = modelMapper.map(entity, ItemResponse.class);
        return ResponseMessage.success(itemResponse);
    }

    @Operation(summary = "View image", description = "View image", tags = {"item"})
    @GetMapping(value = "/image/{filename}", produces = "image/jpeg")
    public void download(
            @PathVariable String filename, HttpServletResponse response
    ) throws IOException {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + filename + "\"");
        fileService.download("item", filename, response.getOutputStream());
    }
}
