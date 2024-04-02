'use client';
import React, { useContext, useEffect, useState } from 'react';
import "../../app/globals.css";
import { setField } from '@/helpers/commonFnc';

export default function Login ( { children } )
{

	const [ form, setForm ] = useState( {
		email: '',
		password: '',
	} );

	const [ loading, setLoading ] = useState( false );

	const [ error, setError ] = useState( {
		email: '',
		password: '',
	} );

	const onSubmit = ( e ) =>
	{
		e.preventDefault();
		let count = 0;
		let objError = {
			email: '',
			password: ''
		}
		if ( !form.email || form.email == '' )
		{
			objError.email = 'Email không được để trống.'
			count++;
		}

		if ( !form.password || form.password == '' )
		{
			objError.password = 'Password không được để trống.'
			count++;
		}
		if ( count > 0 )
		{
			setError( objError );
			return;
		}
		console.log( form );



	}

	return (
		<div className="w-full p-8 lg:w-1/2">
			<h2 className="text-2xl font-semibold text-gray-700 text-center">CMS</h2>
			<p className="text-xl text-gray-600 text-center">Welcome back!</p>

			<div className="mt-4 flex items-center justify-between">
				<span className="border-b w-1/5 lg:w-1/4"></span>
				<a href="#" className="text-xs text-center text-gray-500 uppercase">Login with email</a>
				<span className="border-b w-1/5 lg:w-1/4"></span>
			</div>
			<form>
				<div className="mt-4">
					<label className="block text-gray-700 text-sm font-bold mb-2">Email</label>
					<input className={ `text-gray-700 focus:outline-none
				${ error.email != '' && 'border-red-600' }
				focus:shadow-outline border border-gray-300 rounded py-2 px-4 
				block w-full appearance-none`}

						type="email"
						value={ form.email }
						onChange={ ( e ) =>
						{
							let value = e && e.target.value?.trim() || null
							setField( form, 'email', value, setForm )
						} }
					/>
					{ error.email && <p className='text-red-600 mb-0'>{ error.email }</p> }
				</div>
				<div className="mt-4">
					<label className="block text-gray-700 text-sm font-bold mb-2">Password</label>
					<input className={ ` text-gray-700 focus:outline-none 
				focus:shadow-outline border border-gray-300 rounded
				${ error.password != '' && 'border-red-600' }
				 py-2 px-4 block w-full appearance-none`} type="password"
						value={ form.password } onChange={ ( e ) =>
						{
							let value = e && e.target.value?.trim() || null
							setField( form, 'password', value, setForm )
						} }
					/>
					{ error.password && <p className='text-red-600 mb-0'>{ error.password }</p> }
				</div>
				<div className="mt-8">
					<button type='submit' onClick={ ( e ) => onSubmit( e ) } className="bg-gray-700 text-white font-bold py-2
				 px-4 w-full rounded hover:bg-gray-600">Login</button>
				</div>
			</form>

			<div className="mt-4 flex items-center justify-between">
				<span className="border-b w-1/5 md:w-1/4"></span>
				<a href="/auth/register" className="text-xs text-gray-500 uppercase">or sign up</a>
				<span className="border-b w-1/5 md:w-1/4"></span>
			</div>
		</div>
	);
}
