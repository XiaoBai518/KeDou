
function onSubmitSearch() {
	if($("input[name='search']").val()==null||$("input[name='search']").val()=="") {
		$("input[name='search']").val($("input[name='search']").attr('placeholder'));
	}
	return true;
}