package com.smartwaste.smart_waste.service;

import com.smartwaste.smart_waste.entity.Bin;
import com.smartwaste.smart_waste.entity.CollectionLog;
import com.smartwaste.smart_waste.repository.BinRepository;
import com.smartwaste.smart_waste.repository.CollectionLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BinService {

    private final BinRepository binRepository;
    private final CollectionLogRepository collectionLogRepository;

    public BinService(BinRepository binRepository, CollectionLogRepository collectionLogRepository){
        this.binRepository = binRepository;
        this.collectionLogRepository=collectionLogRepository;
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
    public Bin collectBin(Long binId){

        Bin bin = binRepository.findById(binId)
                .orElseThrow(() -> new RuntimeException("Bin not found"));

        bin.setCurrentFill(0);
        bin.setStatus("NORMAL");

        // Create collection log
        CollectionLog log = new CollectionLog();
        log.setBinId(bin.getId());
        log.setTruckId(1L); // temporary for now
        log.setCollectedAt(LocalDateTime.now());

        collectionLogRepository.save(log);

        return binRepository.save(bin);
    }

}