package com.smartwaste.smart_waste.controller;

import com.smartwaste.smart_waste.entity.Bin;
import com.smartwaste.smart_waste.service.BinService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/bins")
@CrossOrigin(origins = "http://localhost:5173")
public class BinController {

    private final BinService binService;

    public BinController(BinService binService){
        this.binService = binService;
    }

    @PostMapping
    public Bin createBin(@RequestBody Bin bin){
        return binService.createBin(bin);
    }

    @GetMapping
    public List<Bin> getBins(){
        return binService.getAllBins();
    }
    @PutMapping("/collect/{id}")
    public Bin collectBin(@PathVariable Long id){
        return binService.collectBin(id);
    }

}