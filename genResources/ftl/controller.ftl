package ${pkg};

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ${serviceNameWithPkg};
import ${serviceNameImplWithPkg};

@WebServlet(name = "/${clazzName}Servlet", urlPatterns = "/servlet/${clazzName?uncap_first}Servlet")
public class ${clazzName}Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ${clazzName}Service ${clazzName?uncap_first}Service = new ${clazzName}ServiceImpl();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		switch (op) {
			case "":

				break;

			default:
				break;
		}
	}

}
