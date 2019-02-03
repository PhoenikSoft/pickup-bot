package com.phoeniksoft.pickupbot.common

import com.phoeniksoft.pickupbot.infrastructure.jpa.history.UserHistoryDto
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserDto
import com.phoeniksoft.pickupbot.infrastructure.neo4j.AdviceDto

import java.time.LocalDateTime

trait TestData {

    UserDto validUserDto() {
        def userDto = new UserDto()
        userDto.id = 1
        userDto.telegramId = 'testTelegram'
        userDto
    }

    UserHistoryDto validUserHistoryDto() {
        def dto = new UserHistoryDto()
        dto.id = 1
        dto.adviceId = 1
        dto.user = validUserDto()
        dto.created = LocalDateTime.now()
        dto
    }

    AdviceDto validAdviceDto(long id = 1, String msg = 'testMsg') {
        def dto = new AdviceDto()
        dto.id = id
        dto.msg = msg
        dto
    }

    List<AdviceDto> validAdviceDtoList(int num = 1) {
        def result = []
        1.upto(num) {
            result << validAdviceDto(it, "testMsg${it}")
        }
        result
    }
}