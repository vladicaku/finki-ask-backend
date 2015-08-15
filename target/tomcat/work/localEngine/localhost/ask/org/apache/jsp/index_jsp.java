package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\t<meta charset=\"utf-8\">\r\n");
      out.write("\t<script src=\"static/jquery-2.1.4.min.js\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<script>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar JSONData = {\r\n");
      out.write("\t\t\tname: \"TestName\",\r\n");
      out.write("\t\t\ttype: \"GRADEDTEST\",\r\n");
      out.write("\t\t\tdateCreated: \"10.10.15 12:30\", \r\n");
      out.write("\t\t\tcreator: 1,\r\n");
      out.write("\t\t\tstart: \"10.10.15 12:30\", \r\n");
      out.write("\t\t\tend: \"10.10.15 12:30\", \r\n");
      out.write("\t\t\tduration: 999,\r\n");
      out.write("\t\t\tpassword: \"password123\",\r\n");
      out.write("\t\t\tquestions: []\r\n");
      out.write("\t\t};\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("         $.ajax({\r\n");
      out.write("                type: \"POST\",\r\n");
      out.write("                url: 'http://localhost:8080/ask/admin/tests',\r\n");
      out.write("                contentType:\"application/json; charset=utf-8\",\r\n");
      out.write("                dataType: 'json',\r\n");
      out.write("                data:  JSONData,\r\n");
      out.write("                success: function(data){\r\n");
      out.write("                    alert(data);\r\n");
      out.write("                }\r\n");
      out.write("\t\t\t}); \r\n");
      out.write("\r\n");
      out.write("        alert(\"POST JSON ------------> \"+JSONData);\r\n");
      out.write("\t</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
