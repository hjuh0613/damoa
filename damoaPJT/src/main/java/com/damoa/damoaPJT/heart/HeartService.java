package com.damoa.damoaPJT.heart;

import com.damoa.damoaPJT.entity.Heart;
import com.damoa.damoaPJT.heart.dto.AddHeartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HeartService {

    private final HeartRepository heartRepository;

    @Transactional
    public String addOrDeleteHeart(AddHeartRequest addHeartRequest){

        Optional<Heart> optionalHeart = heartRepository.findByUserUserNoAndNoAndType(
                addHeartRequest.getUserNo()
                , Integer.parseInt(addHeartRequest.getNo())
                , addHeartRequest.getType()
        );

        String returnStr = "";

        if(optionalHeart.isEmpty()) {
            heartRepository.save(addHeartRequest.toEntity());

            returnStr = "add";
        } else {
            heartRepository.deleteById(optionalHeart.get().getHeartNo());

            returnStr = "delete";
        }

        return returnStr;
    }

}
