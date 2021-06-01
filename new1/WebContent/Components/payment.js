/**
 * 
 */

$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	 {
	 	$("#alertSuccess").hide();
	 }
	 $("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
debugger
	// Clear alerts---------------------
	 $("#alertSuccess").text("");
	 $("#alertSuccess").hide();
	 $("#alertError").text("");
	 $("#alertError").hide();
	 
	// Form validation-------------------
	var status = validatePaymentForm();
	if (status != true)
	 {
	 $("#alertError").text(status);
	 $("#alertError").show();
	 return;
 }
	// If valid------------------------
	var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";
	 $.ajax({
	 url : "PaymentAPI",
	 type : type,
	 data : $("#formPayment").serialize(),
	 dataType : "text",
	 complete : function(response, status)
	 {
	  location.reload(true);
	 onPaymentSaveComplete(response.responseText, status);
	
	 }
 }); 
});

function onPaymentSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidPaymentIDSave").val("");
	$("#formPayment")[0].reset();
}
// UPDATE==========================================
$(document).on(
	"click",
	".btnUpdate",
	function(event) {
		$("#hidprodNOSave").val(
			$(this).closest("tr").find('#hidPaymentIDSave').val());
		$("#payId").val($(this).closest("tr").find('td:eq(0)').text());
		$("#nameOncard").val($(this).closest("tr").find('td:eq(1)').text());
		$("#cardNumber").val($(this).closest("tr").find('td:eq(2)').text());
		$("#expiredDate").val($(this).closest("tr").find('td:eq(3)').text());
		$("#cvv").val($(this).closest("tr").find('td:eq(4)').text());
	});


// REMOVE ====================================================


$(document).on("click", ".btnRemove", function(event)
{ 
debugger
 $.ajax(
 {
 url : "PaymentAPI",
 type : "DELETE",
 data : "payId=" + $(this).data("payid"),
 dataType : "text",
 complete : function(response, status)
 {

  location.reload(true);
 onPaymentDeleteComplete(response.responseText, status);

 }
 });

});


// CLIENT-MODEL================================================================
function validatePaymentForm()
{
if ($("#nameOnCard").val().trim() == "") 
 { 
 return "Insert Item Code."; 
 }
 if ($("#cardNumber").val().trim() == "") 
 { 
 return "Insert Item Code."; 
 }
  if ($("#expiredDate").val().trim() == "") 
 { 
 return "Insert Item Code."; 
 }
  if ($("#cvv").val().trim() == "") 
 { 
 return "Insert Item Code."; 
 }
 
 
return true;
}

function onItemSaveComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully saved.");
 $("#alertSuccess").show();
 $("#divItemsGrid").html(resultSet.data);

 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while saving.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while saving..");
 $("#alertError").show();
 } 

 $("#hidItemIDSave").val("");
 $("#formItem")[0].reset();
}

function onPaymentDeleteComplete(response, status){
	debugger
if (status == "success"){
	 var resultSet = JSON.parse(response);
	 if (resultSet.status.trim() == "success"){
		 $("#alertSuccess").text("Successfully deleted.");
		 $("#alertSuccess").show();
		 $("#divItemsGrid").html(resultSet.data);
 	} 
 	
 	else if (resultSet.status.trim() == "error"){
		 $("#alertError").text(resultSet.data);
		 $("#alertError").show();
 	}
 } 
 else if (status == "error"){
	 $("#alertError").text("Error while deleting.");
	 $("#alertError").show();
 	} 
 else{
	 $("#alertError").text("Unknown error while deleting..");
	 $("#alertError").show();
 }
}   
 