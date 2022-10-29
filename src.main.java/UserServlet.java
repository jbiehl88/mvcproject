import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jordan.dao.UserDAO;
import com.jordan.model.Employee;

/**
 * Servlet implementation class Login
 */
@WebServlet("/")
public class UserServlet extends HttpServlet {
	private UserDAO userDAO;

	public void init() {
		userDAO = new UserDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertUser(request, response);
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			default:
				listUser(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List <Employee> listUser = userDAO.selectAllEmployees();
		System.out.println(listUser);
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("success.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Employee existingEmployee = userDAO.selectEmployee(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		request.setAttribute("user", existingEmployee);
		dispatcher.forward(request, response);

	}

	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		Employee newEmployee = new Employee(id, name, password);
		userDAO.insertEmployee(newEmployee);
		response.sendRedirect("list");
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");

		Employee book = new Employee(id, name, password);
		userDAO.updateEmployee(book);
		response.sendRedirect("list");
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		userDAO.deleteEmployee(id);
		response.sendRedirect("list");

	}
}