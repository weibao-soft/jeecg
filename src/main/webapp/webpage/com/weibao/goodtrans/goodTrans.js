$(document).ready(function() {
	$("input[name='holderPartType']").change(function() {
		debugger;
		var value=$("input[name='holderPartType']:checked").val();
        if(value=='GR') {
        	$("[name='holderGRTr']").css("display", "table-row");
        	$("[name='holderGRTr']").css("display", "table-row");
        	$("[name='holderQYTr']").css("display", "none");
        	$("[name='holderQYTr']").css("display", "none");
        	$("#holderCertLbl").css("display", "none");
        	$("#holderCertType").css("display", "none");
        } else if(value=='QY') {
        	$("[name='holderQYTr']").css("display", "table-row");
        	$("[name='holderQYTr']").css("display", "table-row");
        	$("#holderCertLbl").css("display", "table-row");
        	$("#holderCertType").css("display", "table-row");
        	$("[name='holderGRTr']").css("display", "none");
        	$("[name='holderGRTr']").css("display", "none");
        }
	});
	$("input[name='insuredPartType']").change(function() {
		var value=$("input[name='insuredPartType']:checked").val();
        if(value=='GR') {
        	$("[name='insuredGRTr']").css("display", "table-row");
        	$("[name='insuredGRTr']").css("display", "table-row");
        	$("[name='insuredQYTr']").css("display", "none");
        	$("[name='insuredQYTr']").css("display", "none");
        	$("#insuredCertLbl").css("display", "none");
        	$("#insuredCertType").css("display", "none");
        } else if(value=='QY') {
        	$("[name='insuredQYTr']").css("display", "table-row");
        	$("[name='insuredQYTr']").css("display", "table-row");
        	$("#insuredCertLbl").css("display", "table-row");
        	$("#insuredCertType").css("display", "table-row");
        	$("[name='insuredGRTr']").css("display", "none");
        	$("[name='insuredGRTr']").css("display", "none");
        }
	});
});

//将下拉框修改为可编辑的下拉框
function editablePolicy() {
	$("#holderCompName").editableSelect({
      effects: 'slide',
      bg_iframe: false,
      case_sensitive: false,
      items_then_scroll: 10,
      isFilter:false,
      onSelect: function(list_item) {
          var holderCode=$(list_item[0]).attr("data-code");
          $("#holderOrgCode").val(holderCode);
      }
  });
}