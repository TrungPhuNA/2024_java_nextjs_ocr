"use client";
import React, { useEffect, useState } from "react";
import Link from "next/link";
import Image from "next/image";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";

import DefaultLayout from "@/components/Layouts/DefaultLayout";

import { Product } from "@/types/product";
import { INIT_PAGING, WEB_VALUE } from "@/services/constant";
import { COMMON_API, ORDER_SERVICE } from "@/services/api.service";
import { buildImage, formatMoney, formatTime, getItem, onErrorUser } from "@/services/helpers.service";
import { PagingPage } from "@/components/common/paging";
import Loader from "@/components/common/Loader";
import { FaTrash } from "react-icons/fa";
import { FaPencil } from "react-icons/fa6";



const OrderList: React.FC = () => {

	const [dataList, setDataList] = useState([]);
	const [paging, setPaging] = useState(INIT_PAGING);
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		getDataList({ ...paging })
	}, []);

	const getDataList = async (filters: any) => {
		setLoading(true);
		const response: any = await COMMON_API.getList('user', filters);
		setLoading(false);
		if (response?.status == 'success') {
			setDataList(response.data || []);
			setPaging(response.meta || INIT_PAGING);
		}
	}

	return (
		<DefaultLayout>
			<Breadcrumb pageName="Danh sách" subName="Nhân viên" />

			<div className="flex flex-col gap-10">
				<div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
					<div className="px-4 py-6 md:px-6 xl:px-7.5 md:flex md:justify-between w-full">
						<h4 className="text-xl font-semibold text-black dark:text-white">
							Danh sách
						</h4>
						<Link href={'/user/form'} className="inline-flex items-center justify-center rounded-md bg-primary px-10 py-2 text-center font-medium text-white hover:bg-opacity-90 lg:px-8 xl:px-10">Create</Link>
					</div>
					{loading && <Loader className={"bg-opacity-60 bg-white z-50 fixed top-0 left-0 w-full h-full"} />}
					<div className="px-4">
						<div className="max-w-full overflow-x-auto">
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
											Trạng thái
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Giới tính
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Ngày sinh
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Nguyên quán
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											CCCD
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Ngày cấp
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Quốc tịch
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Thường trú
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Chức vụ
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Bằng cấp
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Phòng ban
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Loại nhân viên
										</th>

										<th className="py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Thao tác
										</th>
									</tr>
								</thead>
								<tbody>
									{dataList.map((item: any, key: any) => (
										<tr key={key}>
											<td className="border-b border-[#eee] py-5 px-4 pl-9 dark:border-strokedark">
												<p className="font-medium text-black dark:text-white cursor-pointer"
												// onClick={() => updateData(item)}
												>
													{item.code}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black font-medium dark:text-white text-nowrap">
													{item.name}
												</p>
												<span className="">Email: {item.email}</span>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<img src={buildImage(item.avatar) || "/images/image_faildoad.png"} 
												onError={(e) => onErrorUser(e)}
												width={80} height={80} />

											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className={`dark:text-white ${item.status == "ACTIVE" ? 'text-success' : 'text-red'}`}>
													{item.status == "ACTIVE" ? "Đang làm việc" : "Nghỉ"}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black dark:text-white">
													{item.gender == "MALE" ? "Nam" : (item.gender == "FEMALE" ? "Nữ" : "Khác")}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black dark:text-white">
													{formatTime(item.dob, 'DD/MM/yyyy')}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black dark:text-white">
													{(item.cccdAddress)}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black dark:text-white">
													{(item.cccd)}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black dark:text-white">
													{formatTime(item.cccdDate, 'DD/MM/yyyy')}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black dark:text-white">
													{item.region || 'Việt Nam'}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black dark:text-white">
													{item.address}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black dark:text-white">
													{item.rank?.name}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black dark:text-white">
													{item.certificate?.name}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black dark:text-white">
													{item.room?.name}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black dark:text-white">
													{item.employerType?.name}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<div className="flex items-center space-x-3.5">
													<Link href={'/user/form?id=' + item.id} className="hover:text-primary"
													>
														<FaPencil />
													</Link>
												</div>
											</td>
										</tr>
									))}
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

export default OrderList;
