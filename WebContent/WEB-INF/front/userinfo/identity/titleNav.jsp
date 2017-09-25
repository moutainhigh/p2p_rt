<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
     <table class="uniceTable siteInfoWidth">
            <tbody>
                <tr id="attul">
                    <td id="title1"><a href="${path }/verify/userAttestation.do">实名认证</a></td>
                    <td id="title2"><a href="${path }/verify/toEmail.do">邮箱认证</a></td>
                    <td id="title3"><a href="${path }/verify/toPhone.do">手机认证</a></td>
                    <td id="title4"><a href="${path }/verify/toVideo.do">视频认证</a></td>
                    <td id="title5"><a href="${path }/verify/toScene.do">现场认证</a></td>
                    <td id="title6"><a href="${path }/verify/toUpload.do">资料上传</a></td>   
                    <td id="title7"><a href="${path }/verify/list.do">证明材料</a></td>   
                    <td id="title8"><a href="${path }/verify/toVip.do">VIP申请</a></td>                    
                </tr>
            </tbody>
      </table>
	<script type="text/javascript">
	try{
		var curNav = "${curNav }";
		$("#"+curNav).addClass("userDh");
	}catch(e){}
	</script>
	