<%@ page import="org.json.*"%>
<%@ page import="java.util.*"%>
<%
	 	/* try{
			Random random = new Random();
			int min = 2000;
			int max = 2500;
			int randomNumber = random.nextInt(max + 1 - min) + min;
			out.println(randomNumber);
			 try{
				throw new Exception();
			}catch(Exception ee){
				ee.printStackTrace();
			} 
			 Thread.sleep(randomNumber);
		}
		catch(Exception ex){

		} 
*/
		JSONObject obj =(JSONObject)request.getSession().getAttribute("CustomerObj");

		String firstName = "Sign Up | Sign In"; 
		
		if(obj!=null){
			try{
				firstName = obj.getString("firstName");
			}catch(Exception e){}
			
		}
		//out.println()
        String email = (String) session.getAttribute("email");
		// out.println(email);
		if(email == null){
			// out.println("Inside ====>>  ");
			response.sendRedirect("home.jsp");
		}


		
		
 %>
 		<div class="col-md-12 header">
			<h1 class="text-muted"><a href="home.jsp"><img src="image/bigstore-logo.png" alt="bigstore"/><span style="display:none">WebStore</span></a></h1>
			<div ng-controller="navigationController" class="ng-scope">
				<ul class="nav nav-pills pull-left">
					<li><a href="home.jsp">Home</a></li>
					<li><a href="viewcart.jsp">Cart</a></li>
					<li class="dropdown-mainmenu">
						<a href="category.jsp" >Categories<span class="caret"></span></a>
			    		<ul class="dropdown-menu multi-level ng-binding" role="menu" aria-labelledby="dropdownMenu" ng-bind-html="htmlString">
							<li class="dropdown-submenu">
								<a href="home.jsp?cat=1">Books</a>
								<ul class="dropdown-menu">
									<li><a href="home.jsp?cat=2">Fiction</a></li>
									<li><a href="home.jsp?cat=3">Non-fiction</a></li>
								</ul>
							</li>
							<li class="dropdown-submenu">
								<a href="home.jsp?cat=4">Electronics</a>
								<ul class="dropdown-menu"><li><a href="home.jsp?cat=5">Mobile</a></li>
									<li><a href="home.jsp?cat=6">Mobile Accessories</a></li>
								</ul>
							</li>							
							<li class="dropdown-submenu">
								<a href="home.jsp?cat=19">Baby &amp; Kids</a>
								<ul class="dropdown-menu">
									<li><a href="home.jsp?cat=20">Kids Footwear</a></li>
									<li><a href="home.jsp?cat=21">Kids Clothing</a></li>
									<li><a href="home.jsp?cat=22">Toys</a></li>
									<li><a href="home.jsp?cat=23">Baby Care</a></li>
								</ul>
							</li>
							<li class="dropdown-submenu">
								<a href="home.jsp?cat=24">Problem Scenarios</a>
								<ul class="dropdown-menu">
									<li><a href="cpuUtilization.jsp?cat=25">High CPU</a></li>
									<li><a href="frontendslow.jsp?cat=26">Frontend Slow</a></li>
								</ul>
							</li>
						</ul>
					</li>
					
				<!--	<%if(firstName.equals("Sign In") || firstName.length() == 0){%>
						  <li ng-hide="logined"><a href="signin.jsp" ng-click="signInButton()"><%= firstName %></a></li> 
						  <li ng-hide="logined"><a href="NewUser.jsp" ng-click="signInButton()">SignUp</a></li> 
						  
					<%}
					else {%>
						<li ng-hide="logined"><a href=""> Hi <span id="username"> <%= firstName %></span></a></li>
						<li><a href="logout.jsp">Logout</a></li>
					<% } %>
					-->	
					
				</ul>
				<div style="float:right" class="userblock">
					<%if(firstName.equals("Sign Up | Sign In") || firstName.length() == 0){%>
						  <div ng-hide="logined"><a href="NewUser.jsp" ng-click="signInButton()">Sign Up</a> <span style="color:#fff;">|</span> <a href="signin.jsp" ng-click="signInButton()">Sign In</a></div>  
					<%}
					else {%>
						<div ng-hide="logined" style="color:#fff;"><span style="color:#fff;padding:5px;">Hi</span><a  id="username" href=""><%= firstName %></a> | <a href="logout.jsp">Logout</a></div>
					<% } %>
				
				</div>
				<div style="clear:both"></div>
				<div class="modal fade ng-scope" title="WebStore Sign In" visible="showModal"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button><h4 class="modal-title ng-binding">WebStore Login</h4></div><div class="modal-body" ng-transclude="">
				    <form role="form" class="ng-pristine ng-valid ng-scope">
						<div class="form-group">
							<label for="email">Email address</label>
							<input type="email" class="form-control" id="email" placeholder="Enter email">
						</div>
						<div class="form-group">
						  	<label for="password">Password</label>
						  	<input type="password" class="form-control" id="password" placeholder="Password">
						</div>
						<div class="form-group">
						  	<a ng-click="forgetPassWordAction()">Forget password?</a>
						</div>
						<button type="submit" class="btn btn-default" ng-click="submitAction()">Submit</button>
						<button type="submit" class="btn btn-default pull-right" ng-click="signupAction()">Sign Up</button>
				    </form>
				</div></div></div></div>
				
				<div class="modal fade ng-scope" title="Reset password" visible="showModalpass"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button><h4 class="modal-title ng-binding">Reset password</h4></div><div class="modal-body" ng-transclude="">
				    <form role="form" class="ng-pristine ng-valid ng-scope">
						<div class="form-group">
							<label for="email">Email address</label>
							<input type="email" class="form-control" id="emailpass" placeholder="Enter email">
						</div>
		
						<button type="submit" class="btn btn-default" ng-click="submitEmailAction()">Submit</button>
				    </form>
				</div></div></div></div>
			</div>

		</div>