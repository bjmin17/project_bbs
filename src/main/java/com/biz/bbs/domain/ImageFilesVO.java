package com.biz.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImageFilesVO {

	private long img_file_seq;//	
	private long img_file_p_code;//	
	private String img_file_origin_name;//	
	private String img_file_upload_name;//	
}
