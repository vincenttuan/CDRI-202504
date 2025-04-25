package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
	private Integer id; // 序號
	private String text; // 工作項目
	private Boolean completed; // 項目是否完成?
}
