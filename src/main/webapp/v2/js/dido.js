var devdom = null;

devdom = (function( $, undefined ){

	var dido = {};
	dido.menu = {
		hide : function(){
			$('#dropdown-main').css('display','none');
		},
		show : function(){
			$('#dropdown-main').css('display','block');
		}
	}

}(jQuery));

jQuery.support.cors = true;