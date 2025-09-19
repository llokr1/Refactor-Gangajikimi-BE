package Myaong.Gangajikimi.facade;

import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.member.service.MemberService;
import Myaong.Gangajikimi.postfound.entity.PostFound;
import Myaong.Gangajikimi.postfound.service.PostFoundCommandService;
import Myaong.Gangajikimi.postfound.web.dto.request.PostFoundRequest;
import Myaong.Gangajikimi.postfound.web.dto.response.PostFoundResponse;
import Myaong.Gangajikimi.templocation.entity.TempLocation;
import Myaong.Gangajikimi.templocation.service.TempLocationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFoundFacade {

    private final MemberService memberService;
    private final PostFoundCommandService postFoundCommandService;
    private final TempLocationService tempLocationService;
    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @Transactional
    public PostFoundResponse postPostFound(PostFoundRequest request, Long memberId){

        // Member 생성
        Member member = memberService.findMemberById(memberId);

        // TODO: 생성된 AI 이미지 추가

        // 게시글 생성
        PostFound postFound = postFoundCommandService.postPostFound(request, member);

        // 사용자가 목격한 임시 좌표 생성
        Point newPoint = geometryFactory.createPoint(new Coordinate(request.getFoundLongitude(), request.getFoundLatitude()));

        TempLocation tempLocation =TempLocation.of(postFound, newPoint);

        // 임시 좌표 저장
        tempLocationService.saveTempLocation(tempLocation);

        return PostFoundResponse.of(postFound.getId(), member.getMemberName(), postFound.getTitle(), postFound.getCreatedAt());
    }

}
