"use client";
import React, { useEffect, useState } from "react";
import Link from "next/link";
import Image from "next/image";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";

import DefaultLayout from "@/components/Layouts/DefaultLayout";

import { Product } from "@/types/product";
import { ATTENDANCE_TYPE, INIT_PAGING, WEB_VALUE } from "@/services/constant";
import { AUTH_SERVICE, COMMON_API, ORDER_SERVICE } from "@/services/api.service";
import { buildImage, formatMoney, formatTime, getItem, onErrorUser } from "@/services/helpers.service";
import { PagingPage } from "@/components/common/paging";
import Loader from "@/components/common/Loader";
import { FaTrash } from "react-icons/fa";
import { FaPencil } from "react-icons/fa6";
import ModalCheckInPage from "./ModalCheckIn";



const AttendanceList: React.FC = () => {

	const [dataList, setDataList] = useState([]);
	const [paging, setPaging] = useState(INIT_PAGING);
	const [loading, setLoading] = useState(false);
	const [user, setUser] = useState(null);
	const [open, setOpen] = useState(false);
	const [detail, setDetail] = useState(null);

	useEffect(() => {
		getDataList({ ...paging });
		getDetail()
	}, []);

	const getDetail = async () => {
		setLoading(true);
		const response: any = await AUTH_SERVICE.show();
		setLoading(false);
		if (response?.status == "success") {
			setUser(response?.data)

		}

	}

	const getDataList = async (filters: any) => {
		setLoading(true);
		const response: any = await COMMON_API.getList('attendance', filters);
		setLoading(false);
		if (response?.status == 'success') {
			let data = response?.data?.map((item: any) => {
				item.attendance = ATTENDANCE_TYPE.find((e: any) => e?.value?.toLowerCase() == item.type?.toLowerCase());
				return item;
			})
			setDataList(data || []);
			setPaging(response.meta || INIT_PAGING);
		}
	}

	const deleteData = async (idData: any) => {
		setLoading(true);
		const response: any = await COMMON_API.delete('attendance', idData);
		setLoading(false);
		if (response?.status == 'success') {
			getDataList({  ...paging, page: 1, })
		}
	}

	const updateData = (item: any) => {
		setDetail(item);
		setOpen(true);
	}

	return (
		<DefaultLayout>
			<Breadcrumb pageName="Danh sách" subName="Châm công" />
			<ModalCheckInPage open={open} setOpen={setOpen} 
			detail={detail} setDetail={setDetail} getDataList={getDataList} 
			paging={paging}/>
			<div className="flex flex-col gap-10">
				<div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
					<div className="px-4 py-6 md:px-6 xl:px-7.5 md:flex md:justify-between w-full">
						<h4 className="text-xl font-semibold text-black dark:text-white">
							Danh sách
						</h4>
						{/* <Link href={'/user/form'} className="inline-flex items-center justify-center rounded-md bg-primary px-10 py-2 text-center font-medium text-white hover:bg-opacity-90 lg:px-8 xl:px-10">Create</Link> */}
					</div>
					{loading && <Loader className={"bg-opacity-60 bg-white z-50 fixed top-0 left-0 w-full h-full"} />}
					<div className="px-4">
						<div className="max-w-full overflow-x-auto py-3">
							<table className="w-full table-auto">
								<thead>
									<tr className="bg-gray-2 text-left dark:bg-meta-4">
										<th className="min-w-[220px] py-4 px-4 font-medium text-black dark:text-white xl:pl-11">
											Mã NV
										</th>
										<th className="min-w-[220px] py-4 px-4 font-medium text-nowrap
									text-black dark:text-white xl:pl-11">
											Tên NV
										</th>
										<th className="min-w-[150px] py-4 px-4 font-medium text-black dark:text-white">
											Avatar
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Ngày chấm công
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Ca làm việc
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Ngày tạo
										</th>
										<th className="py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Thao tác
										</th>
									</tr>
								</thead>
								<tbody>
									{dataList?.length > 0 ? dataList.map((item: any, key: any) => (
										<tr key={key}>
											<td className="border-b border-[#eee] py-5 px-4 pl-9 dark:border-strokedark">
												<p className="font-medium text-black dark:text-white cursor-pointer"
												>
													{item?.user?.code}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black font-medium dark:text-white text-nowrap">
													{item?.user?.name}
												</p>
												<span className="">Email: {item?.user?.email}</span>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<img src={buildImage(item?.user?.avatar) || "/images/image_faildoad.png"}
													onError={(e) => onErrorUser(e)}
													width={80} height={80} />
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black dark:text-white">
													{formatTime(item.check_in, 'DD/MM/yyyy')}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black dark:text-white">
													{(item?.attendance?.name)}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black dark:text-white">
													{formatTime(item.created_at, 'DD/MM/yyyy')}
												</p>
											</td>

											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<div className="flex items-center space-x-3.5">
													<FaPencil onClick={() => updateData(item)}/>
													<FaTrash onClick={() => deleteData(item.id)}/>
												</div>
											</td>
										</tr>
									)) : <tr>
										<td colSpan={7} className="text-center">
											<p>Không có dữ liệu</p>
										</td>
									</tr>}
								</tbody>
							</table>
						</div>
					</div>

					<div className="mt-3 py-5">
						<PagingPage paging={paging}
							setPaging={setPaging}
							onPageChange={(e: any) => {
								getDataList({ page: e, page_size: paging.page_size })
							}} />
					</div>
				</div>
			</div>
		</DefaultLayout>
	);
};

export default AttendanceList;
