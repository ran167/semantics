<%@page import="java.util.List"%>
<%@page import="com.abb.nlp.PosDisplayData"%>
<%@page import="com.abb.nlp.DisplayData"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
#bd{
    position: absolute;
    top: 15%;
    left: 30%;
  
}
#hd{
    position: absolute;
    top: 1%;
    left: 20%;
    
}
#content{
    position: absolute;
    top: 28%;
    left: .5%;
  background-color: #FFFFFF;
}

</style>
<script type="text/javascript">
function vlidate(){
	var v1=document.getElementById("file").value;
	var v2=document.getElementById("file2").value;
	if(v1!='' && v2!=''){
	  
		return true;
	}
	else{
		alert("Please upload 2 documents");
		return false;
	}
	
	return true;
}
</script>
</head>
<body>
 <div id="hd">
 <h1></h1>
 </div>
<div id="bd">

 <form method="POST" action="UploadReadServlet"  onsubmit="return vlidate()">
            File 1: <input type="file" id="file" name="file" id="file" /> </br>
            </br>
             File 2: <input type="file" id="file2" name="file2" id="file" /> </br>
            </br>
            Select Option:<select name="op">
            <option value="sentence">Split into Sentence</option>
            <option value="pos">Parts of Speech</option>
            </select>
            </br></br>
            <input type="submit" value="Click here" name="upload" id="upload" />
        </form>
     </div>
         <div id="content">
         
 <% 
 if(request.getAttribute("message")!=null){
	 
	 String msg=(String)request.getAttribute("message");%>
	 <br><br><b><%= msg%></br></br></b>
	 <%
	 if(request.getParameter("op").equals("sentence")){
	 String sen[]=(String[])request.getAttribute("sentencelist");
	 String sen2[]=(String[])request.getAttribute("sentencelist2");
	 for(int i=0;i<sen.length;++i){
	%>
	</br>
	<%=i+1%>:<%=sen[i] %></br>
	<%
		 }
	 for(int j=0;j<sen2.length;++j){
			%>
			</br>
			<%=j+1%>:<%=sen[j] %></br>
			<%
				 }
	 }
	 
	 else if(request.getParameter("op").equals("pos")){
		 ArrayList<PosDisplayData> al=(ArrayList)request.getAttribute("poslist");
		 ArrayList<PosDisplayData> a2=(ArrayList)request.getAttribute("poslist2");
		 for(int i=0;i<al.size();++i){
			 PosDisplayData d=al.get(i);
		  %>
		 <b> <i><%=i+1 %>:</i><%=d.getSen() %></br></b>
		 <table border="1" bgcolor="#FFFFFF">
		 <tr><td>Token</td><td>Tag</td></tr>
		 <%
		  for(int k=0;k<d.getTokens().length;k++){%>
		  <tr><td><%=d.getTokens()[k] %></td><td><%=d.getTags()[k] %></td></tr>
          <% }
		 %>
		 </table>
		  </br>
		 <%
		 }
		 
		  for(int i=0;i<a2.size();++i){
			 PosDisplayData d=a2.get(i);
		  %>
		 <b> <i><%=i+1 %>:</i><%=d.getSen() %></br></b>
		 <table border="1" bgcolor="#FFFFFF">
		 <tr><td>Token</td><td>Tag</td></tr>
		 <%
		  for(int k=0;k<d.getTokens().length;k++){%>
		  <tr><td><%=d.getTokens()[k] %></td><td><%=d.getTags()[k] %></td></tr>
          <% }
		 %>
		 </table>
		  </br>
		 <%
		 }
	 }	 
	 else if(request.getParameter("op").equals("entity")){
		 
		 List sen=(List)request.getAttribute("namelist");
		 for(int i=0;i<sen.size();++i){
		%>
		</br>
		<%=i+1%>:<%=sen.get(i) %></br>
		<%
			 }
		 }
 }//if
 	%>
 

 
 </div>
</body>
</html>