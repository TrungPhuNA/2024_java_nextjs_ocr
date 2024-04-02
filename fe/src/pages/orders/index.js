'use client';
import React, { useContext, useEffect, useState } from 'react';
import "../../app/globals.css";
import { setField } from '@/helpers/commonFnc';

export default function OrdersPage (  )
{

	return (
		<div className="bg-gray-100 w-screen min-h-screen flex-col content-center">
			<div className="py-16 ">
				<div className="flex bg-white rounded-lg shadow-lg overflow-hidden mx-auto max-w-sm lg:max-w-4xl">
					<div className="hidden lg:block lg:w-1/2 bg-cover lg:flex">
						<img src="https://images.unsplash.com/photo-1546514714-df0ccc50d7bf?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=667&q=80" alt='https://images.unsplash.com/photo-1546514714-df0ccc50d7bf?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=667&q=80' />
					</div>
				</div>
			</div>
		</div>
	);
}
