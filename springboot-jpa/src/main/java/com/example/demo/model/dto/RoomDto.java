package com.example.demo.model.dto;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
	private Integer roomId;
	
	private String roomName;
	
	@NotNull(message = "房間人數不可以是空值")
	@Range(min = 1, max = 500, message = "房間人數必須介於 {min} ~ {max} 之間")
	private Integer roomSize;
}
