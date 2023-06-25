jQuery(document).ready(function() {
	jQuery("#add-event").submit(function() {
		alert("Submitted");
		var values = {};
		$.each($("#add-event").serializeArray(), function(i, field) {
			values[field.name] = field.value;
		});
		console.log(values);
	});
});

(function () {
	"use strict";
	// ------------------------------------------------------- //
	// Calendar
	// ------------------------------------------------------ //
	jQuery(function () {
		// page is ready
		jQuery.ajax({
			url: "vacation/calendar", // AJAX 요청을 보낼 서버의 API 엔드포인트
			method: "GET", // 요청 메소드 (GET, POST 등)
			success: function (re) {
				var events = []; // 이벤트 배열 초기화

				// 서버에서 받은 데이터를 이벤트 객체로 가공하여 배열에 추가
				re.forEach(function (eventData) {
					var event = {
						title: eventData.empName,
						description: eventData.vacType,
						start: eventData.vacStartDate,
						end: eventData.vacEndDate,
					};
					events.push(event);
				});
				jQuery("#calendar").fullCalendar({
					themeSystem: "bootstrap4",
					// emphasizes business hours
					businessHours: false,
					defaultView: "month",
					// event dragging & resizing 드래그 안되게 막기
					editable: false,
					// header
					header: {
						left: "title",
						center: "month,agendaWeek,agendaDay",
						right: "today prev,next",
					},
					events: events,

					dayClick: function () {
						//jQuery("#modal-view-event-add").modal();
					},
					eventClick: function (event, jsEvent, view) {
						console.log(event);

						//jQuery(".event-icon").html("<i class='fa fa-" + event.icon + "'></i>");
						jQuery(".event-title").html(event.title);
						jQuery(".event-body").html("휴가 유형: " + event.description + "<br>");
						//jQuery(".event-body").html(event.start);
						jQuery("#modal-view-event").modal();
					},
				});
			}
		}); //아작스 끝

	}); //캘린더 끝
})(jQuery);
