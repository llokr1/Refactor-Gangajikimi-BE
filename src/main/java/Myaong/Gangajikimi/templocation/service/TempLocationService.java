package Myaong.Gangajikimi.templocation.service;

import Myaong.Gangajikimi.templocation.entity.TempLocation;
import Myaong.Gangajikimi.templocation.repository.TempLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TempLocationService {

    private final TempLocationRepository tempLocationRepository;

    public TempLocation saveTempLocation(TempLocation tempLocation) {

        return tempLocationRepository.save(tempLocation);

    }

}
