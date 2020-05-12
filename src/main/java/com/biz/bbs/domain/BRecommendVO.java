package com.biz.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BRecommendVO {

	private long b_r_id;// BIGINT AUTO_INCREMENT PRIMARY KEY,
	private String b_r_username;// VARCHAR(50) UNIQUE,
	private String b_r_board_id;// BIGINT
}
