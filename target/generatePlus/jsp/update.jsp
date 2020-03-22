<%--
  Created by IntelliJ IDEA.
  User: HAO
  Date: 2019/12/6
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>修改页面</title>

    <script src="/js/jquery-3.3.1.js"></script>
    <script src="/js/jquery-3.3.1.min.js"></script>

    <script type="text/javascript">
        $.ajax({
            type:"post",
            url:"${pageContext.request.contextPath}/courses/query_course_ajax.do",
            contentType:"application/json",
            dataType:"json",
            success:function(data){
                var option1="<option value=''>请选择</option>";
                for(var i=0;i<data.length;i++){
                    var num=data[i].cid;
                    var a=${account.cid};
                    if(a==num){
                        option1+="<option value="+data[i].cid+" selected>"+data[i].cname+"</option>";
                    }else{
                        option1+="<option value="+data[i].cid+" >"+data[i].cname+"</option>";
                    }
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

    <form action="${pageContext.request.contextPath}/accounts/updates.do" method="post">
        <input type="hidden" name="aid" value="${account.aid}"/>
        姓名:<input type="text" name="aname" value="${account.aname}" maxlength="10" /><br/>
        密码:<input type="text" name="apass" value="${account.apass}" maxlength="10" /><br/>
        性别 男:<input type="radio" name="asex" value="1" ${account.asex eq 1 ? "checked":""}/>
        女:<input type="radio" name="asex" value="2" ${account.asex eq 2 ? "checked":""}/><br/>
        年龄:<input type="text" name="age" value="${account.age}"/><br/>
        课程:<select id="selt" name="cid"></select><br/>
        <input type="submit" value="提交修改"/>
    </form>

</body>
</html>
