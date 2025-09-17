package Myaong.Gangajikimi.postlost.service;

import Myaong.Gangajikimi.postlost.repository.PostLostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLostQueryService {

    private final PostLostRepository postLostRepository;

}
