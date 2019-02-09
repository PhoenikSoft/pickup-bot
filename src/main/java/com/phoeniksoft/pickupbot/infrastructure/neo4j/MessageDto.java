package com.phoeniksoft.pickupbot.infrastructure.neo4j;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity("Message")
@Data
public class MessageDto {

    @Id
    @GeneratedValue
    private Long id;

    private String msg;

    public Advice toAdvice() {
        return Advice.builder()
                .id(id.toString())
                .msg(msg)
                .build();
    }
}
