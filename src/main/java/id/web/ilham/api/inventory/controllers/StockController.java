package id.web.ilham.api.inventory.controllers;

import id.web.ilham.api.inventory.entities.Item;
import id.web.ilham.api.inventory.entities.Stock;
import id.web.ilham.api.inventory.entities.StockSummary;
import id.web.ilham.api.inventory.entities.StockWithItem;
import id.web.ilham.api.inventory.exceptions.EntityNotFoundException;
import id.web.ilham.api.inventory.exceptions.ItemNotFoundException;
import id.web.ilham.api.inventory.models.ResponseMessage;
import id.web.ilham.api.inventory.models.item.ItemElement;
import id.web.ilham.api.inventory.models.stock.StockRequest;
import id.web.ilham.api.inventory.models.stock.StockResponse;
import id.web.ilham.api.inventory.models.stock.StockSummaryResponse;
import id.web.ilham.api.inventory.services.ItemService;
import id.web.ilham.api.inventory.services.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/stocks")
@RestController
public class StockController {

    @Autowired
    private StockService service;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Add stock", description = "Add stock to item", tags = {"stock"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseMessage<StockResponse> add(@RequestBody @Valid StockRequest model) {
        Item item = itemService.findById(model.getItemId());
        if (item == null) {
            throw new ItemNotFoundException();
        }
        Stock entity = modelMapper.map(model, Stock.class);
        service.save(entity);
        ItemElement itemElement = modelMapper.map(item, ItemElement.class);
        StockResponse data = modelMapper.map(entity, StockResponse.class);
        data.setItemElement(itemElement);
        return ResponseMessage.success(data);
    }

    @Operation(summary = "Edit stock", description = "Edit stock", tags = {"stock"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseMessage<StockResponse> edit(@PathVariable Integer id, @RequestBody @Valid StockRequest model) {
        Item item = itemService.findById(model.getItemId());
        if (item == null) {
            throw new ItemNotFoundException();
        }

        Stock entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        modelMapper.map(model, entity);
        service.save(entity);
        ItemElement itemElement = modelMapper.map(item, ItemElement.class);
        StockResponse data = modelMapper.map(entity, StockResponse.class);
        data.setItemElement(itemElement);
        return ResponseMessage.success(data);
    }

    @Operation(summary = "Delete stock", description = "Delete stock", tags = {"stock"}, security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseMessage<String> removeById(@PathVariable Integer id) {
        if (!service.removeById(id)) {
            throw new EntityNotFoundException();
        }
        String data = "Stock with id " + id + " is successfully deleted";
        return ResponseMessage.success(data);
    }

    @Operation(summary = "Find stock", description = "Find stock data", tags = {"stock"})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseMessage<StockResponse> findById(@PathVariable Integer id) {
        StockWithItem entity = service.findByIdWithItem(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        ItemElement itemElement = modelMapper.map(entity.getItem(), ItemElement.class);
        StockResponse data = modelMapper.map(entity, StockResponse.class);
        data.setItemElement(itemElement);
        return ResponseMessage.success(data);
    }

    @Operation(summary = "Get all stock", description = "Get all stock data", tags = {"stock"})
    @GetMapping(produces = "application/json")
    public ResponseMessage<List<Stock>> findAll(
    ) {
        List<Stock> data = service.findAll();
        return ResponseMessage.success(data);
    }

    @Operation(summary = "Get all stock summary", description = "Get all stock summary data", tags = {"stock"})
    @GetMapping(value = "/summaries", produces = "application/json")
    public ResponseMessage<List<StockSummaryResponse>> findAllSummaries() {
        List<StockSummary> summaries = service.findAllSummaries();

        List<StockSummaryResponse> data = summaries.stream()
                .map(e -> modelMapper.map(e, StockSummaryResponse.class))
                .collect(Collectors.toList());

        return ResponseMessage.success(data);
    }

}
