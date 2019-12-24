var imgName = "";

//关闭窗口
function closeCurrent(id){	
	if (parent.$("li.active").attr('id') == "tab_" + id) {
      parent.$("#tab_" + id).prev().addClass('active');
      parent.$("#" + id).prev().addClass('active');
  }
  //关闭TAB
  parent.$("#tab_" + id).remove();
  parent.$("#" + id).remove();		
}

var neibuClickFlag = false;
function jeecgFormFileCallBack(data){
	if (data.success == true) {
		neibuClickFlag = true; 
	} else {
		if (data.responseText == '' || data.responseText == undefined) {
			$.messager.alert('错误', data.msg);
			$.Hidemsg();
		} else {
			try {
				var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText.indexOf('错误信息'));
				$.messager.alert('错误', emsg);
				$.Hidemsg();
			} catch(ex) {
				$.messager.alert('错误', data.responseText + '');
			}
		}
		return false;
	}
	if (!neibuClickFlag) {
		var win = frameElement.api.opener;
		win.reloadTable();
	}
}
function formCallBack(data){
	return false;
}
//doSubmitForm 完成后调用
function formCallback(data){
	var win = frameElement.api.opener;
	if (data.success == true) {
	    frameElement.api.close();
	    win.reloadTable();
	    win.tip(data.msg);
	} else {
	    if (data.responseText == '' || data.responseText == undefined) {
	        $.messager.alert('错误', data.msg);
	        $.Hidemsg();
	    } else {
	        try {
	            var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText.indexOf('错误信息'));
	            $.messager.alert('错误', emsg);
	            $.Hidemsg();
	        } catch (ex) {
	            $.messager.alert('错误', data.responseText + "");
	            $.Hidemsg();
	        }
	    }
	    return false;
	}
}
//初始化下标
function resetTrNum(tableId) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id = $this.attr('id');
			if(name!=null){
				if (name.indexOf("#index#") >= 0){
					$this.attr("name",name.replace('#index#',i));
				}else{
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s+1,e);
					$this.attr("name",name.replace(new_name,i));
				}
			}
			if(id!=null){
				if (id.indexOf("#index#") >= 0){
					$this.attr("id",id.replace('#index#',i));
				}else{
					var s = id.indexOf("[");
					var e = id.indexOf("]");
					var new_id = id.substring(s+1,e);
					$this.attr("id",id.replace(new_id,i));
				}
			}
		});
	});
}



function calculateYear() {
    calculateYearByParam($("#start"), $("#end"));
}
function calculateYearByParam($startEl, $endEl) {
	var isDefault = false;
    if (!($startEl instanceof jQuery)) {
        $startEl = $($startEl);
        var $parent = $startEl.parent();
        $endEl = $parent.children('[data-field="endDate"]');
    } else {
    	isDefault = true;
    }
    if($startEl.val() == null || $startEl.val() == "") {
    	return false;
    }
    var oneDay = 1000*60*60*24;
    var oneYear = 1000*60*60*24*365;
	var year = $("#year").val();
    var start = $startEl.val();
    var time = Date.parse(start.replace(/-/g, "/"));
    time += (oneYear * year);
    var myDate = new Date(time);
    var fullYear = myDate.getFullYear();
    var month = myDate.getMonth() + 1;
    var day = myDate.getDate();
    var str = fullYear + "-";
    str = str + (month < 10 ? ("0" + month) : month) + "-";
    str = str + (day < 10 ? ("0" + day) : day);
    $endEl.val(str);
	if(isDefault) {
		$("#endDate").val(str);
	}
    //layer.msg(str);
    //console.log(myDate.toLocaleDateString());
}
function calculateMonths(obj) {
	var year = obj.value;
	var months = Number(year * 12);
	if(!isPositiveInteger(year)) {
		obj.value = 1;
		months = 12;
		layer.msg("请输入正整数！", {icon:6});
	}

	document.getElementById("month").innerText = months;
    calculateYear();
    var $starts = $('#add_policy_tabel').find('[data-field="startDate"]');
    $starts.each(function() {
    	calculateYearByParam(this);
	});
	
	//layer.msg(isRealNum(year));
    /*var $parent = $(obj).parent();
	var $monthByIdEl = $parent.children('#month');
	var $monthEl = $monthByIdEl.length > 0 ? $monthByIdEl : $parent.children('[data-field="month"]');
    $monthEl.html(months);
    if ($monthByIdEl.length > 0) {
        calculateYear();
    } else {
        calculateYearByParam($parent.children('[data-field="startDate"]')[0]);
    }*/
}

//是否为正整数
function isPositiveInteger(s){
    var re = /^[0-9]+$/ ;
    return re.test(s)
}

//刷新保单列表
function reloadPolicyList(mainTabId) {
	debugger;
	if(mainTabId == null || mainTabId == undefined || mainTabId == '') {
		return false;
	}
	var mydiv = window.parent.$("#"+mainTabId);
	var myframe = $(mydiv).children("iframe")[0];
	if(myframe == null || myframe == undefined) {
		return false;
	}
	var myWindow = myframe.contentWindow;
  myWindow.reload();
}
//打开浏览器窗口
function openWindow(payUrl) {
	payUrl = encodeURIComponent(payUrl);
  EV_modeAlert();//弹出遮罩层
    //参数： url, 名称, 窗体样式
  var child = window.open("policyMainController.do?goChild&payUrl="+payUrl, "支付", "height=666, width=1266, top=0, left=0, alwaysRaised=yes, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
	try {
		child.focus();//子窗口获取焦点
    	window.onfocus=function (){child.focus();};
    	window.onclick=function (){child.focus();};
    	window.parent.onfocus=function (){child.focus();};
    	window.parent.onclick=function (){child.focus();};
	} catch (e) { }
}
//显示弹出层
function openDiv(payUrl) {
  var frameObj=document.getElementById("payiFrame");
  frameObj.src=payUrl;
  $("#payDiv").show();
  window.Utils.showLoading();
}


//公共函数：参数 params为 Json类型，可以传空参数，如:  {}
function getCommonSelect(selectId, url, params) {
	//获取下拉框的数据
  $.ajax({
      url: url,
      type: "POST",
      data: params,
      dataType: "json",
      error: function () {
          layer.alert("服务器异常");
      },
      success: function (data) {
          //if(console) console.log("getCommonSelect == ", data);
          if (data.code == 200) {
          	//拼接下拉框的Option
              addOptions(data.value, selectId);
              return false;
          } else {
              layer.alert(data.message);
          }
      }
  });
}
function addOptions(items, selectId) {
  $.each(items,function(n,item) {
  	//拼接下拉框的Option
      var htmlContent = $('<option data-code="'+item.code+'" value="'+item.id+'">'+item.name+'</option>');
      $('#'+selectId).append(htmlContent);
  });
}
function getHolders(id) {
	//获取投保人单位名称下拉框的数据
  $.ajax({
      url: "policyMainController.do?getHolders",
      type: "POST",
      data: {},
      dataType: "json",
      error: function () {
          layer.alert("服务器异常");
      },
      success: function (data) {
          //if(console) console.log(data);
          if (data.code == 200) {
              addHldOptions(data.value,id);
              return false;
          } else {
              layer.alert(data.message);
          }
      }
  });
}
function getInsureds() {
	//获取投保人单位名称下拉框的数据
  $.ajax({
      url: "policyMainController.do?getInsureds",
      type: "POST",
      data: {},
      dataType: "json",
      error: function () {
          layer.alert("服务器异常");
      },
      success: function (data) {
          if (data.code == 200) {
          	addIurOptions(data.value);
              return false;
          } else {
              layer.alert(data.message);
          }
      }
  });
}
function getReceivers(id) {
	//获取专用发票收件人下拉框的数据
  $.ajax({
      url: "policyMainController.do?getReceivers",
      type: "POST",
      data: {},
      dataType: "json",
      error: function () {
          layer.alert("服务器异常");
      },
      success: function (data) {
          if (data.code == 200) {
          	addRceOptions(data.value,id);
              return false;
          } else {
              layer.alert(data.message);
          }
      }
  });
}
function addHldOptions(items,id) {
  $.each(items,function(n,value) {
      var htmlContent = $('<option data-code="'+value.code+'" data-id="'+value.id+'" value="'+value.name+'">'+value.name+'</option>');
      $('#'+id).append(htmlContent);
  });
}
function addIurOptions(items) {
  $.each(items,function(n,value) {
      var htmlContent = $('<option data-code="'+value.code+'" value="'+value.name+'">'+value.name+'</option>');
      $('#insuredCompName').append(htmlContent);
  });
}
function addRceOptions(items,id) {
  $.each(items,function(n,value) {
      var htmlContent = $('<option data-num="'+value.recipientsTel+'" data-text="'+value.reciAddress+'" value="'+value.recipients+'">'+value.recipients+'</option>');
      $('#'+id).append(htmlContent);
  });
}