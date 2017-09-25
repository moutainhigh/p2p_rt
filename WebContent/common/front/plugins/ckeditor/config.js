/**
 * @license Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'zh';
	 config.uiColor = '#EBEBEB';
	 config.toolbar = [
		[ 'Bold', 'Italic','Underline','Strike' ],
		['NumberedList', 'BulletedList'],
		['Smiley','SpecialChar','-','Link', 'Unlink']
	 ];
	 config.width = "690"; 
     config.height = "180";
	 /* config.toolbar = [
		[ 'Bold', 'Italic','Underline','Strike', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink' ],
		['Styles','Format','Font','FontSize', '-', 'TextColor','BGColor'],
		['Table','Image','Smiley','SpecialChar']
	 ];*/
};
