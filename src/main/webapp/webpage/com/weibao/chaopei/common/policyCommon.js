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
      var htmlContent = $('<option data-id="'+value.id+'" value="'+value.name+'">'+value.name+'</option>');
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
function getHolderById(holderId) {
	//根据id获取投保人，带出组织机构代码、单位性质、行业类别等
  $.ajax({
      url: "policyMainController.do?getHolderById",
      type: "POST",
      data: {id: holderId},
      dataType: "json",
      error: function () {
          layer.alert("服务器异常");
      },
      success: function (data) {
          //if(console) console.log(data);
          if (data.code == 200) {
          	addHolder(data.value);
      		//tip($.i18n.prop('common.opt.success'));
              return false;
          } else {
              layer.alert(data.message);
          }
      }
  });
}
function addHolder(item) {
	//tip(item.taxpayerNo);
	document.getElementById("holderOrgCode").value = item.holderOrgCode;
	document.getElementById("holderCompNature").value = item.holderCompNature;
	document.getElementById("industryType").value = item.industryType;
	document.getElementById("taxpayerNo2").value = item.taxpayerNo;
	document.getElementById("receiverMobile").value = item.receiverMobile;
	document.getElementById("taxpayerNop").value = item.taxpayerNo;
	document.getElementById("compNamep").value = item.compName;
	document.getElementById("compAddressp").value = item.compAddress;
	document.getElementById("compPhonep").value = item.compPhone;
	document.getElementById("depositBankp").value = item.depositBank;
	document.getElementById("bankAccountp").value = item.bankAccount;

}