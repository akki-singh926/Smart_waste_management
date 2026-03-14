package com.smartwaste.smart_waste.service;

import com.smartwaste.smart_waste.entity.Bin;
import com.smartwaste.smart_waste.repository.BinRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BinService {

    private final BinRepository binRepository;

    public BinService(BinRepository binRepository){
        this.binRepository = binRepository;
    }

    public Bin createBin(Bin bin){

        if(bin.getCurrentFill() >= 80){
            bin.setStatus("NEEDS_PICKUP");
        } else {
            bin.setStatus("NORMAL");
        }

        return binRepository.save(bin);
    }

    public List<Bin> getAllBins(){
        return binRepository.findAll();
    }
}