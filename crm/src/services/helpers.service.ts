import moment from "moment";

export const getItem = ( key : any) =>
{
	return localStorage.getItem( key ) || null;
}

export const setItem = ( key: any, value: any) =>
{
	localStorage.setItem( key, value );
}

export const removeItem = ( key : any) =>
{
	localStorage.removeItem( key );
}

export const buildFilter = ( values : any) =>
{

	let params = {};
	if ( values )
	{
		delete values.total;
		delete values.total_pages;
		delete values.count;
		let arrCondition = Object.entries( values );

		params = arrCondition.reduce( ( param, item ) =>
		{
			if ( item[ 1 ] != null )
			{
				param = { ...param, ...buildItemParam( item[ 0 ], item[ 1 ], param ) }
			}
			return param;
		}, {} );
	}
	return params;
}

export const buildItemParam = ( key: any, value: any, params: any ) =>
{
	if ( key == 'page' && !value )
	{
		params[ 'page' ] = value;
	} else if ( value )
	{
		params[ `${ key }` ] = value;
	}
	return params;
}

export const validateMessages = {
	required: '${label} không được để trống!',
	types: {
		email: '${label} không đúng định dạng email',
		number: '${label} không đúng định dạng số',
		regexp: '${label} không đúng định dạng!'
	},
	number: {
		range: '${label} trong khoảng ${min} - ${max}',
	},
};

export const resetForm = ( form: any ) =>
{
	form.resetFields();
}

export const onFieldsChange = ( e: any, form: any, ee = null ) =>
{
	if ( e.length > 0 )
	{
		let value = typeof e[ 0 ].value === 'string' ? e[ 0 ].value : e[ 0 ].value;
		if ( e[ 0 ].name[ 0 ] === 'name' && value != '' )
		{
			// let slug = toSlug( value );
			form.setFieldsValue( { slug: value } );
		}
		let fieldValue = {
			[ String( e[ 0 ].name[ 0 ] ) ]: value
		}
		form.setFieldsValue( fieldValue );
	}
}


export const onErrorImage = ( e: any ) =>
{
	// e.currentTarget.src = DEFAULT_IMAGE;
}

export const onErrorUser = ( e: any ) =>
{
	// e.currentTarget.src = DEFAULT_IMG;
}
// }
// export const onErrorUser = (e)=> {
// 	e.currentTarget.src = DEFAULT_USER;
// }

export const VALIDATE_FORM = {
	required: '${label} không được để trống!',
	types: {
		email: '${label} không đúng định dạng email',
		number: '${label} không đúng định dạng số',
	},
	number: {
		range: '${label} trong khoảng ${min} - ${max}',
	},
};

export const formatTime = (value: any, format: any): any =>  {
	if(value) {
		if(format) return moment(value).format(format);
		return moment(value)
	}
	
	return null;
}

export const formatMoney = (money: any) => {
	return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(money)
}