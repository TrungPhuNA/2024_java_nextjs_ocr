"use client";
import React, { useState, ReactNode, useEffect } from "react";
import Sidebar from "@/components/Sidebar";
import Header from "@/components/Header";
import { getItem } from "@/services/helpers.service";

export default function DefaultLayout({
	children,
}: {
	children: React.ReactNode;
}) {
	const [sidebarOpen, setSidebarOpen] = useState(false);
	const [auth, setAuth] = useState(getItem('access_token') ?? false);

	useEffect(() => {
		if(!auth && !window.location.pathname.includes('auth')) {
			window.location.href='/auth/signin'
		}
	}, [])
	
	return (
		<>
			{/* <!-- ===== Page Wrapper Start ===== --> */}
			<div className="flex h-screen overflow-hidden">
				{auth && <Sidebar sidebarOpen={sidebarOpen} setSidebarOpen={setSidebarOpen} />}
				<div className="relative flex flex-1 flex-col overflow-y-auto overflow-x-hidden">
					{auth && <Header sidebarOpen={sidebarOpen} setSidebarOpen={setSidebarOpen} />}

					<main>
						<div className="mx-auto max-w-screen-2xl p-4 md:p-6 2xl:p-10">
							{children}
						</div>
					</main>
				</div>
			</div>
		</>
	);
}
