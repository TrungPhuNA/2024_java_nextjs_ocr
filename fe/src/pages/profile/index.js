'use client';
import React, { useContext, useEffect, useState } from 'react';
import "../../app/globals.css";
import { setField } from '@/helpers/commonFnc';

export default function Profile ( { children } )
{

	

	return (
		<div className="bg-gray-100 w-screen min-h-screen flex-col content-center">
			<div className="py-16 ">
				<div className="flex bg-white rounded-lg shadow-lg overflow-hidden mx-auto max-w-sm lg:max-w-4xl">
					<div className="hidden lg:block lg:w-1/2 bg-cover lg:flex">
						<img src="https://images.unsplash.com/photo-1546514714-df0ccc50d7bf?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=667&q=80" alt='https://images.unsplash.com/photo-1546514714-df0ccc50d7bf?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=667&q=80' />
					</div>
					<div className="w-full p-8 lg:w-1/2">
						<h2 className="text-2xl font-semibold text-gray-700 text-center">CMS</h2>
						<p className="text-xl text-gray-600 text-center">Welcome back!</p>

						<div className="mt-4 flex items-center justify-between">
							<span className="border-b w-1/5 lg:w-1/4"></span>
							<a href="#" className="text-xs text-center text-gray-500 uppercase">Sign Up</a>
							<span className="border-b w-1/5 lg:w-1/4"></span>
						</div>
						

						<div className="mt-4 flex items-center justify-between">
							<span className="border-b w-1/5 md:w-1/4"></span>
							<a href="/auth/login" className="text-xs text-gray-500 uppercase">or sign in</a>
							<span className="border-b w-1/5 md:w-1/4"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}
