# About Jsp

## Scripting Tags
  
  - Scriplet Tags
  Here source code are written
  - Expression Tags
  Here things are expressed or functions are called
  - declaration Tags
  Here variables and methods are declared
 
## Implicit Objects

 - out
 - request
 - response
 - config
 - session
 - pageContext
 - page
 - exception

## JSP Directives

 - JSP page Directives 
 - JSP include Directives
 - JSP taglib directives 

## JSP page Directives

 - import
 - contentType
 - extends
 - info
 - buffer
 - language
 - isElonared
 - isThreadsafe
 - autoFlush
 - session
 - pageEncoding
 - errorPage
 - isErrorPage

## JSP include directives
 - <%@ include file="resourceName" %>
the work of this is to get content of other file

## JSP Taglib directives 
 - Defines a tag library
<%@ taglib uri="" prefix="" %>
 - <mytag:currentDate/>

## JSP exception
 - mention in jsp page or 
 - mention in xml file page that is web.xml

## Action Elements
 - jsp:forward

    <jsp:forward page="welcome.jsp/>

    forwards to another page 
 - jsp:include
   
    include other page 

    <jsp:include page="welcome.jsp/">
 - JavaBean
    
    encapsulates many objects into one object and can be assessible this object from many pages
  - jsp:useBean
  
    first use: access Calculation.java from jsp file

    second use: can be accessible by many other jsp file

    jsp:setProperty and 
    
    jsp:getProperty

    <jsp:getProperty property="name" name="u"/><br>

    <jsp:getProperty property="name" name="u" value="<%=name%>"/>

  - Display applet in JSP file

    less useful
