export const setField = ( form, field, value, setForm ) =>
{
	setForm( {
		...form,
		[ field ]: value
	} );
};