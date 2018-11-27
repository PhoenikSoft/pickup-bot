package com.phoeniksoft.pickupbot.infrastructure.neo4j;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity("Advice")
@Data
public class AdviceDto {

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
