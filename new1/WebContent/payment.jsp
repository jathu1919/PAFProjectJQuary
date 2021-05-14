<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@page import="model.Payment"%>
<%
//Save---------------------------------
if (request.getParameter("itemCode") != null)
{
Payment payObj = new Payment();
String stsMsg = "";
//Insert--------------------------
if (request.getParameter("hidPaymentIDSave") == "")
{
stsMsg = payObj.insertPayment(request.getParameter("nameOnCard"),
request.getParameter("cardNumber"),
request.getParameter("expiredDate"),
request.getParameter("cvv"));
}
else//Update----------------------
{
stsMsg = payObj.updatePayment(request.getParameter("hidPaymentIDSave"),
request.getParameter("nameOnCard"),
request.getParameter("cardNumber"),
request.getParameter("expiredDate"),
request.getParameter("cvv"));
}
session.setAttribute("statusMsg", stsMsg);
}
//Delete-----------------------------
if (request.getParameter("hidPaymentIDDelete") != null)
{
Payment payObj = new Payment();
String stsMsg =
payObj.deletePayment(request.getParameter("hidPaymentIDDelete"));
session.setAttribute("statusMsg", stsMsg);
}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="Components/items.js"></script>
<title>Payment Management</title>
</head>
<body>
<h1>Payment Management</h1>

<form id="formPayment" name="formPayment" method="post" action="payment.jsp">
 Card Name:
<input id="nameOnCard" name="nameOnCard" type="text"
 class="form-control form-control-sm">
<br> Card Number:
<input id="cardNumber" name="cardNumber" type="text"
 class="form-control form-control-sm">
<br> Expired Date:
<input id="expiredDate" name="expiredDate" type="text"
 class="form-control form-control-sm">
<br> CVV:
<input id="cvv" name="cvv" type="text"
 class="form-control form-control-sm">
<br>
<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
<input type="hidden" id="hidPaymentIDSave" name="hidPaymentIDSave" value="">
</form>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>

<%
 out.print(session.getAttribute("statusMsg"));
%>
<br>
<div id="divItemsGrid">
<%

Payment payObj = new Payment();
 out.print(payObj.readPayment());
%>
</div>
</body>
</html>