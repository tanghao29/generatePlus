<%--
  Created by IntelliJ IDEA.
  User: HAO
  Date: 2019/12/6
  Time: 9:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>    <script src="/js/jquery-3.3.1.js"></script>
    <script src="/js/jquery-3.3.1.min.js"></script>
    <title>添加用户</title>

    <script type="text/javascript">
        $.ajax({
            type:"post",
            url:"${pageContext.request.contextPath}/courses/query_course_ajax.do",
            contentType:"application/json",
            dataType:"json",
            success:function(data){
                var option1="<option value=''>请选择</option>";
                for(var i=0;i<data.length;i++){
                    option1+="<option value="+data[i].cid+">"+data[i].cname+"</option>";
                }
                $("#selt").html(option1);
            },
            error:function(){
                alert("出错啦");
            }
        });
    </script>


</head>
<body>

    <form action="/accounts/Add.do" method="post">
        姓名:<input type="text" name="aname" maxlength="10" placeholder="请输入姓名！" /> <br/>
        密码:<input type="text" name="apass" maxlength="10" placeholder="请输入密码！" /> <br/>
        性别: 男:<input type="radio" checked name="asex" value="1"/> 女:<input type="radio" name="asex" value="2" /> <br/>
        年龄:<input type="text" name="age" maxlength="3" placeholder="请输入年龄！"/> <br/>
        课程:<select id="selt" name="cid"></select><br/>
            <input type="submit" value="注册" /> | <input type="reset" value="清空" />
    </form>


</body>
</html>
