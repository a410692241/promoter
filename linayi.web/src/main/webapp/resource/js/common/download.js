jQuery.download = function(url, data, method) {
	if (url && data) {
		var inputs = '';
		for ( var property in data) {
			inputs += '<input type="hidden" name="' + property + '" value="'
					+ data[property] + '" />';
		}
		var $from = jQuery(
				'<form action="' + url + '" method="' + (method || 'post')
						+ '">' + inputs + '</form>').appendTo('body');
		
		$from.form("submit",{
			success:function( data ){
				
			}
		});
		$from.remove();
	}
};