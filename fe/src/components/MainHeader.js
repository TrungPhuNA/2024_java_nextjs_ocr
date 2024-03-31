"use client";
import { FaBars } from "react-icons/fa";
import {useContext} from "react";
import {MenuContext} from "@/context/MenuContext";
export default function MainHeader() {
    const {toggle} = useContext(MenuContext);
    return (
        <div className={'bg-white flex justify-between items-center px-4 h-12 mb-4'}>
            <h2>ADM</h2>
            <div className={'flex justify-center items-center gap-4'}>
                <div onClick={toggle} className={'lg:hidden'}>
                    <FaBars className={'cursor-pointer'} />
                </div>
                <div>Hi Phú</div>
            </div>
        </div>
    )
}
