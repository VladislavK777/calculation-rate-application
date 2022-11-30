package io.diana.calculaterate.service.station;

import io.diana.calculaterate.domain.station.Road;
import io.diana.calculaterate.repository.station.RoadRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoadService {
    private final RoadRepository roadRepository;

    public RoadService(RoadRepository roadRepository) {
        this.roadRepository = roadRepository;
    }

    @Transactional(readOnly = true)
    public List<Road> findAll() {
        return roadRepository.findAll(PageRequest.of(0, 50, Sort.Direction.ASC, "name")).getContent();
    }
}
