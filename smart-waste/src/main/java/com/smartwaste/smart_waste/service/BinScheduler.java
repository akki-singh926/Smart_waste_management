package com.smartwaste.smart_waste.service;

import com.smartwaste.smart_waste.entity.Bin;
import com.smartwaste.smart_waste.repository.BinRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class BinScheduler {

    private final BinRepository binRepository;

    public BinScheduler(BinRepository binRepository){
        this.binRepository = binRepository;
    }

    @Scheduled(fixedRate = 1000000) // every 10 seconds
    public void simulateBinFill(){

        List<Bin> bins = binRepository.findAll();
        Random random = new Random();

        for(Bin bin : bins){

            double newFill = bin.getCurrentFill() + random.nextInt(10);

            if(newFill > 100){
                newFill = 100;
            }

            bin.setCurrentFill(newFill);

            if(newFill >= 80){
                bin.setStatus("NEEDS_PICKUP");
            } else {
                bin.setStatus("NORMAL");
            }

            binRepository.save(bin);
        }

        System.out.println("Bin fill simulation executed");
    }
}