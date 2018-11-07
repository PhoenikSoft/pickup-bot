package com.phoeniksoft.pickupbot.infrastructure.neo4j;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity("Category")
@Data
public class AdviceDto {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
}
