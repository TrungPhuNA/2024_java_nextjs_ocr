'use client';
import MainHeader from "@/components/MainHeader";
import { AiOutlineHome } from "react-icons/ai";
import { GrProjects } from "react-icons/gr";
import Link from "next/link";
import {MenuContext} from "@/context/MenuContext";
import {useContext} from "react";
import MainSideBar from "./MainSideBar";

export default function MainLayout({children}) {
    const { open } = useContext(MenuContext);
    return (
        <div className={'bg-gray-100 w-screen min-h-screen'}>
            <MainHeader />
            <div className={'flex justify-start items-start'}>
                {/*<aside className={'bg-white rounded-lg w-60 p-4'}>*/}
                <MainSideBar/>
                <main className={'flex-1'}>{children}</main>
            </div>
        </div>
    )
}
