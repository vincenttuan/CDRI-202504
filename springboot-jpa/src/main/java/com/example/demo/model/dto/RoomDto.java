package com.example.demo.model.dto;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
	@NotNull(message = "房號不可以是空值")
	@Range(min = 1, max = 9999, message = "房號必須介於 {min} ~ {max} 之間")
	private Integer roomId;
	
	@NotNull(message = "房名不可以是空值")
	@Size(min = 2, message = "房名至少要有 {min} 個字")
	private String roomName;
	
	@NotNull(message = "房間人數不可以是空值")
	@Range(min = 1, max = 500, message = "房間人數必須介於 {min} ~ {max} 之間")
	private Integer roomSize;
}
