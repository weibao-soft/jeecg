(function ($) {
	$.toast = {
		defaults: {
			/** VARS - OPTIONS **/
			autoHide: true,				// Notify box auto-close after 'TimeShown' ms ?
			clickOverlay: false,			// if 'clickOverlay' = false, close the notice box on the overlay click ?
			MinWidth: 200,					// min-width CSS property
			TimeShown: 1500, 				// Box shown during 'TimeShown' ms
			ShowTimeEffect: 200, 			// duration of the Show Effect
			HideTimeEffect: 200, 			// duration of the Hide effect
			LongTrip: 15,					// in pixel, length of the move effect when show and hide
			HorizontalPosition: 'right', 	// left, center, right
			VerticalPosition: 'bottom',	 // top, center, bottom
			ShowOverlay: false,				// show overlay behind the notice ?
			ColorOverlay: '#000',			// color of the overlay
			OpacityOverlay: 0.3,			// opacity of the overlay

			/** METHODS - OPTIONS **/
			onClosed: null,
			onCompleted: null
		},

		/*****************/
		/** Init Method **/
		/*****************/
		init: function (type, msg, options, id) {
			opts = $.extend({}, $.toast.defaults, options);

			/** Box **/
			if ($("#" + id).length == 0)
				$Div = $.toast._construct(type, id, msg);

			// Width of the Brower
			WidthDoc = parseInt($(window).width());
			HeightDoc = parseInt($(window).height());

			// Scroll Position
			ScrollTop = parseInt($(window).scrollTop());
			ScrollLeft = parseInt($(window).scrollLeft());

			// Position of the jNotify Box
			posTop = $.toast.vPos(opts.VerticalPosition);
			posLeft = $.toast.hPos(opts.HorizontalPosition);

			// Show the jNotify Box
			if (opts.ShowOverlay && $("#jOverlay").length == 0)
				$.toast._showOverlay($Div);

			$.toast._show(msg);
		},

		/*******************/
		/** Construct DOM **/
		/*******************/
		_construct: function (type, id, msg) {
			var classStyle = '', headStr = '', iconStyle = '';
			switch (type) {
				case "error":
					classStyle = "alert alert-danger";
					headStr = "";
					iconStyle = "ace-icon fa fa-times";
					break;
				case "warn":
					classStyle = "alert alert-warning";
					headStr = "";
					iconStyle = "ace-icon fa fa-exclamation-circle";
					break;
				case "success":
					classStyle = "alert alert-block alert-success";
					headStr = "";
					iconStyle = "ace-icon fa fa-check";
					break;
				case "info":
					classStyle = "alert alert-info";
					headStr = "";
					iconStyle = "ace-icon fa fa-info-circle";
					break;
			}

			$Div =
				$('<div id="' + id + '" class="' + classStyle + '"/>')
					.css({ opacity: 0, minWidth: opts.MinWidth })
					.appendTo('body');
			$Div.append('<button type="button" class="close" data-dismiss="alert"><i class="ace-icon fa fa-times"></i></button><strong style="margin-right:10px"><i class="' + iconStyle + '" style="margin-right:5px"></i>' + headStr + '</strong>');
			$Div.append('<div style="display:inline-block;padding-right:30px;">' + msg + '</div>');
			return $Div;
		},

		/**********************/
		/** Postions Methods **/
		/**********************/
		vPos: function (pos) {
			switch (pos) {
				case 'top':
					var vPos = ScrollTop + parseInt($Div.outerHeight(true) / 2);
					break;
				case 'center':
					var vPos = ScrollTop + (HeightDoc / 2) - (parseInt($Div.outerHeight(true)) / 2);
					break;
				case 'bottom':
					var vPos = ScrollTop + HeightDoc - parseInt($Div.outerHeight(true));
					break;
			}
			return vPos;
		},

		hPos: function (pos) {
			switch (pos) {
				case 'left':
					var hPos = ScrollLeft;
					break;
				case 'center':
					var hPos = ScrollLeft + (WidthDoc / 2) - (parseInt($Div.outerWidth(true)) / 2);
					break;
				case 'right':
					var hPos = ScrollLeft + WidthDoc - parseInt($Div.outerWidth(true));
					break;
			}
			return hPos;
		},

		/*********************/
		/** Show Div Method **/
		/*********************/
		_show: function (msg) {
			$Div
				.css({
					top: posTop,
					left: posLeft
				});
			switch (opts.VerticalPosition) {
				case 'top':
					$Div.animate({
						top: posTop + opts.LongTrip,
						opacity: 1
					}, opts.ShowTimeEffect, function () {
						if (opts.onCompleted) opts.onCompleted();
					});
					if (opts.autoHide)
						$.toast._close();
					else
						$Div.css('cursor', 'pointer').click(function (e) {
							$.toast._close();
						});
					break;
				case 'center':
					$Div.animate({
						opacity: 1
					}, opts.ShowTimeEffect, function () {
						if (opts.onCompleted) opts.onCompleted();
					});
					if (opts.autoHide)
						$.toast._close();
					else
						$Div.css('cursor', 'pointer').click(function (e) {
							$.toast._close();
						});
					break;
				case 'bottom':
					$Div.animate({
						top: posTop - opts.LongTrip,
						opacity: 1
					}, opts.ShowTimeEffect, function () {
						if (opts.onCompleted) opts.onCompleted();
					});
					if (opts.autoHide)
						$.toast._close();
					else
						$Div.css('cursor', 'pointer').click(function (e) {
							$.toast._close();
						});
					break;
			}
		},

		_showOverlay: function (el) {
			var overlay =
				$('<div id="jOverlay" />')
					.css({
						backgroundColor: opts.ColorOverlay,
						opacity: opts.OpacityOverlay
					})
					.appendTo('body')
					.show();

			if (opts.clickOverlay)
				overlay.click(function (e) {
					e.preventDefault();
					$.toast._close();
				});
		},


		_close: function () {
			switch (opts.VerticalPosition) {
				case 'top':
					if (!opts.autoHide)
						opts.TimeShown = 0;
					$Div.delay(opts.TimeShown).animate({
						top: posTop - opts.LongTrip,
						opacity: 0
					}, opts.HideTimeEffect, function () {
						$(this).remove();
						if (opts.ShowOverlay && $("#jOverlay").length > 0)
							$("#jOverlay").remove();
						if (opts.onClosed) opts.onClosed();
					});
					break;
				case 'center':
					if (!opts.autoHide)
						opts.TimeShown = 0;
					$Div.delay(opts.TimeShown).animate({
						opacity: 0
					}, opts.HideTimeEffect, function () {
						$(this).remove();
						if (opts.ShowOverlay && $("#jOverlay").length > 0)
							$("#jOverlay").remove();
						if (opts.onClosed) opts.onClosed();
					});
					break;
				case 'bottom':
					if (!opts.autoHide)
						opts.TimeShown = 0;
					$Div.delay(opts.TimeShown).animate({
						top: posTop + opts.LongTrip,
						opacity: 0
					}, opts.HideTimeEffect, function () {
						$(this).remove();
						if (opts.ShowOverlay && $("#jOverlay").length > 0)
							$("#jOverlay").remove();
						if (opts.onClosed) opts.onClosed();
					});
					break;
			}
		},

		_isReadable: function (id) {
			if ($('#' + id).length > 0)
				return false;
			else
				return true;
		}
	};

	/** Init method **/
	$.msg = function (type, msg, options) {
		if ($.toast._isReadable('toast_' + type))
			$.toast.init(type, msg, $.extend({
				VerticalPosition: 'center',
				HorizontalPosition: 'center',
				TimeShown:4000,
				//autoHide: false,
			}, options), 'toast_' + type);
	};
})(jQuery);