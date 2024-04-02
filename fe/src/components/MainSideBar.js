'use client';
import MainHeader from "@/components/MainHeader";
import { AiOutlineHome } from "react-icons/ai";
import { GrProjects } from "react-icons/gr";
import Link from "next/link";
import { MenuContext } from "@/context/MenuContext";
import { useContext } from "react";

export default function MainSideBar ( props )
{
	return (
		<aside className={ `bg-white h-screen rounded-lg overflow-hidden transition-all duration-100  ${ props.open ? "w-60 p-4" : "w-0" } md:w-60 md:p-4` }>
			<ul>
				<li className={ 'flex justify-start items-center hover:bg-blue-200 hover:text-blue-800 rounded-xl p-2' }>
					<AiOutlineHome className={ 'mr-2' } />
					<Link href={ '/' }  >Home</Link>
				</li>
				<li className={ 'flex justify-start items-center hover:bg-blue-200 hover:text-blue-800 rounded-xl p-2' }>
					<GrProjects className={ 'mr-2' } />
					<Link href={ '/profile' }  >Profile</Link>
				</li>
				<li className={ 'flex justify-start items-center hover:bg-blue-200 hover:text-blue-800 rounded-xl p-2' }>
					<GrProjects className={ 'mr-2' } />
					<Link href={ '/orders' }  >Order</Link>
				</li>
			</ul>
		</aside>
	)
}
