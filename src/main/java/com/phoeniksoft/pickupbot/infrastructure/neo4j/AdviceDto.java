package com.phoeniksoft.pickupbot.infrastructure.neo4j;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.ArrayList;
import java.util.List;

@NodeEntity("Advice")
@Data
public class AdviceDto {

    @Id
    @GeneratedValue
    private Long id;

    private String msg;

    private List<String> tags = new ArrayList<>(0);

    public Advice toAdvice() {
        return Advice.builder()
                .id(id.toString())
                .msg(msg)
                .build();
    }
}
