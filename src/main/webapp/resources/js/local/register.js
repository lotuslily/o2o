$(function() {
    var registerUrl = '/o2o/local/localuserregister';
    $('#submit').click(function() {
        var localAuth = {};
        var personInfo = {};
        var password =$('#password').val();
        var confirmPassword = $('#confirmPassword').val();
        if(password != confirmPassword){
            $.toast('两次密码不一致！');
            return;
        }
        localAuth.username = $('#username').val();
        localAuth.password = $('#password').val();
        personInfo.email = $('#email').val();
        /*使用的下拉菜单来进行选择，获取值的方法*/
        personInfo.gender = $('#gender').find('option').not(function(){
            return !this.selected;
        }).data('id');
        /*$('select#gender').find('option:selected').val();*/
        personInfo.userType = $('#usertype').find('option').not(function(){
            return !this.selected;
        }).data('id');
        // personInfo.nickname = $('#nickname').val();
        localAuth.personInfo = personInfo;
        var thumbnail = $('#small-img')[0].files[0];
        console.log(thumbnail);
        var formData = new FormData();
        formData.append('thumbnail', thumbnail);
        formData.append('localAuth', JSON.stringify(localAuth));
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码！');
            return;
        }
        formData.append("verifyCodeActual", verifyCodeActual);
        $.ajax({
            url : registerUrl,
            type : 'POST',
            data : formData,
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if (data.success) {
                    $.toast('提交成功！');
                    window.location.href = '/local/login';
                } else {
                    $.toast('提交失败！'+ data.errMsg);
                    $('#captcha_img').click();
                }
            }
        });
    });

    $('#back').click(function() {
        window.location.href = '/local/login';
    });
});