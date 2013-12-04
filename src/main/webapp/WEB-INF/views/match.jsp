<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="pageTitle" value="TTT - Table Tennis Tracker" scope="request"/>

<jsp:include page="./header.jsp"/>
<form:form commandName="command">

    <div class="container">
    <h2>New Match</h2>
		<form class="form-horizontal" role="form">
			<div class="form-group">
				<label for="exampleInputEmail1" class="col-sm-2 control-label">Who did you play?</label>
				<div class="col-sm-10">
					<input type="email" class="form-control" id="exampleInputEmail1" placeholder="Opponent" autofocus="">
				</div>
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1" class="col-sm-2 control-label">Games You Won</label>
				<div class="col-sm-10">
					<select class="form-control">
						<option>0</option>					
						<option>1</option>
						<option>2</option>
						<option>3</option>
						<option>4</option>
						<option>5</option>
					</select>				
				</div>
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1" class="col-sm-2 control-label">Games You Lost</label>
				<div class="col-sm-10">
					<select class="form-control" disabled>
						<option>0</option>
					</select>	
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</div>	
		</form>


    </div> <!-- /container -->
</form:form>
<jsp:include page="./footer.jsp"/>
