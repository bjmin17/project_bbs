
var upFile = function(formData) {

	$.ajax({
			url : rootPath + "/image/files_up",
			method : 'POST',
			data : formData,
			
			// 파일을 올릴 때는 이 두가지가 필수
			processData : false, /* 파일업로드 필수 옵션 */
			contentType : false, /* 파일업로드 필수 옵션 */
			
			success : function(result) {
				if(result == 'FAIL') {
					alert("파일 업로드 오류")
				} else {
					$("#d_d_box").html(result)
				}
				
				alert(result)
			},
			
			error:function() {
				alert("서버 통신 오류")
			}
		})
		
	return false
}
		
		

		

					
