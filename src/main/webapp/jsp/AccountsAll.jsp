<%--
  Created by IntelliJ IDEA.
  User: HAO
  Date: 2019/12/6
  Time: 9:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="/js/jquery-3.3.1.js"></script>
        <script src="/js/jquery-3.3.1.min.js"></script>
        <title>查询所有</title>

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

        function clicks(c){
            var arr=document.getElementsByName("boxs");
            for (var i=0;i<arr.length;i++) {
                if (c.checked) {
                    arr[i].checked=true;
                }else{
                    arr[i].checked=false;
                }
            }
        }

        function sc() {
            var fc=document.getElementById("fm")
            var a=document.getElementsByName("boxs");
            if(a.length!=0){
                fc.submit();
            }else{
                alert("内部出错！");
            }
        }

    </script>


<head/>
<body>


    <form action="${pageContext.request.contextPath}/accounts/queryAll.do" method="get">
        姓名:<input type="text" name="aname" placeholder="支持模糊查询！"/>
        性别: 男:<input type="radio" name="asex" value="1"/> 女:<input type="radio" name="asex" value="2" />
        年龄:<input type="text" name="age" maxlength="3" placeholder="请输入年龄！"/>
        课程:<select id="selt" name="cid"></select>
        <input type="submit" value="单个或联合查询！">
    </form>
    <button onclick="sc()">多个删除</button>
    <table border="1">
        <caption>所有的用户</caption>
        <tr><th colspan="8"><a href="/jsp/AccountAdd.jsp" style="text-decoration:none" >添加新用户</a></th></tr>
        <tr><th>全选<input type="checkbox" onclick="clicks(this)"/> </th><th>序号</th><th>姓名</th><th>密码</th><th>性别</th><th>年龄</th><th>课程</th><th>操作</th></tr>
            <form id="fm" method="post" action="${pageContext.request.contextPath}/accounts/deDuoge.do">
            <c:forEach items="${list}" var="account" varStatus="start">
                <tr>
                    <td><input type="checkbox" name="boxs" value="${account.aid}">
                    <td>${start.index+1}</td>
                    <td>${account.aname}</td>
                    <td>${account.apass}</td>
                    <td>${account.asex eq 1?"男":"女"}</td>
                    <td>${account.age}</td>
                    <td>${account.courses.cname}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/accounts/queryById.do?aid=${account.aid}" style="text-decoration:none">修改</a> | <a href="${pageContext.request.contextPath}/accounts/delete.do?aid=${account.aid}" style="text-decoration:none">删除</a>
                    </td>
                </tr>
            </c:forEach>
           </form>
    </table>

    <c:if test="${dqy-1>0}">
        <a href="${pageContext.request.contextPath}/accounts/queryAll.do?dqy=${1}&aname=${account1.aname}&asex=${account1.asex}&age=${account1.age}&cid=${account1.cid}" >首页</a>
        <a href="${pageContext.request.contextPath}/accounts/queryAll.do?dqy=${dqy-1}&aname=${account1.aname}&asex=${account1.asex}&age=${account1.age}&cid=${account1.cid}">上一页</a>
    </c:if>

    <c:forEach begin="1" end="${num}" var="lists">
        <a href="${pageContext.request.contextPath}/accounts/queryAll.do?dqy=${lists}&aname=${account1.aname}&asex=${account1.asex}&age=${account1.age}&cid=${account1.cid}">${lists}</a>
    </c:forEach>

    <c:if test="${dqy+1<num+1}">
        <a href="${pageContext.request.contextPath}/accounts/queryAll.do?dqy=${dqy+1}&aname=${account1.aname}&asex=${account1.asex}&age=${account1.age}&cid=${account1.cid}">下一页</a>
        <a href="${pageContext.request.contextPath}/accounts/queryAll.do?dqy=${num}&aname=${account1.aname}&asex=${account1.asex}&age=${account1.age}&cid=${account1.cid}">尾页</a>
    </c:if>
    当为第${dqy}页共${num}页

</body>
</html>
