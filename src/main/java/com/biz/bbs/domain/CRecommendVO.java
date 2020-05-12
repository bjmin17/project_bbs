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
public class CRecommendVO {

	private long c_r_id;// BIGINT AUTO_INCREMENT PRIMARY KEY,
	private String c_r_username;// VARCHAR(50) UNIQUE,
	private String c_r_board_id;// BIGINT
}
