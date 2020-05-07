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
public class BBsVO {
	
	private long b_id;//	bigint	auto_increment	primary key,
	private long b_p_id;//	bigint,	-- default 	,
	private String b_writer;//	varchar(50)	not null	,
	private String b_date;//	varchar(10)	not null	,
	private String b_time;//	varchar(10)	not null	,
	private String b_subject;//	varchar(125)	not null	,
	private String b_text;//	varchar(1000)	not null	,
	private String b_count;//	bigint	default 0
}
