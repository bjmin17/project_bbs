package com.biz.bbs.domain;

import java.util.List;

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
	private long b_count;//	bigint	default 0
	private long b_recommend;
	private String b_file;
	
	// 같은이름으로 생성된 input box에 담긴 값을 수신하기
//	private List<String> img_file_origin_name;
	// form에서 보낸 파일이름을 담을 변수
	private List<String> img_file_upload_name;

	// 1. DB table에서 파일정보를 select 했을 때 담을 변수
	// 2. form에서 파일들을 업로드하기 위한 정보를 담을 변수로 활용
	private List<ImageFilesVO> img_files;
	
	private List<ImageFilesVO> img_up_files;
}
