"use client";
import React, { useEffect, useState } from "react";
import Link from "next/link";
import Image from "next/image";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";

import DefaultLayout from "@/components/Layouts/DefaultLayout";

import { Product } from "@/types/product";
import { INIT_PAGING, WEB_VALUE } from "@/services/constant";
import { COMMON_API, ORDER_SERVICE } from "@/services/api.service";
import { buildImage, formatMoney, formatTime, getItem } from "@/services/helpers.service";
import { PagingPage } from "@/components/common/paging";
import Loader from "@/components/common/Loader";
import { FaTrash } from "react-icons/fa";
import { FaPencil } from "react-icons/fa6";




const OrderList: React.FC = () => {

	const [dataList, setDataList] = useState([]);
	const [paging, setPaging] = useState(INIT_PAGING);
	const [loading, setLoading] = useState(false);
	const [user, setUser] = useState(getItem('user'));


	useEffect(() => {
		getDataList({ ...paging })
	}, []);

	const getDataList = async (filters: any) => {
		setLoading(true);
		if(user?.userType == 'USER') {
			filters.user_id = user.id
		}
		const response: any = await COMMON_API.getList('bonus', filters);
		setLoading(false);
		if (response?.status == 'success') {
			setDataList(response.data || []);
			setPaging(response.meta || INIT_PAGING);
		}
	}

	const deleteData = async (idData: any) => {
		setLoading(true);
		const response: any = await COMMON_API.delete('bonus', idData);
		setLoading(false);
		if (response?.status == 'success') {
			getDataList({ ...paging, page: 1  })
		}
	}

	return (
		<DefaultLayout>
			<Breadcrumb subName="Thưởng - Kỷ luật" pageName="Danh sách"  />

			<div className="flex flex-col gap-10">
				<div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
					<div className="px-4 py-6 md:px-6 xl:px-7.5 md:flex md:justify-between w-full">
						<h4 className="text-xl font-semibold text-black dark:text-white">
							Danh sách
						</h4>
						{user?.userType == 'ADMIN' && <Link href={'/bonus/form'} className="inline-flex items-center justify-center rounded-md bg-primary px-10 py-2 text-center font-medium 
						text-white hover:bg-opacity-90 lg:px-8 xl:px-10">Create</Link>}
					</div>
					{loading && <Loader className={"bg-opacity-60 bg-white z-50 fixed top-0 left-0 w-full h-full"} />}
					<div className="px-4">
						<div className="max-w-full overflow-x-auto">
							<table className="w-full table-auto">
								<thead>
									<tr className="bg-gray-2 text-left dark:bg-meta-4">
										<th className=" py-4 px-4 font-medium text-black dark:text-white xl:pl-11">
											#
										</th>
										<th className="py-4 px-4 font-medium text-black dark:text-white xl:pl-11">
											Tiêu đề
										</th>
										<th className=" py-4 px-4 font-medium text-nowrap
									text-black dark:text-white xl:pl-11">
											Mô tả
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Giá trị
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Nhân viên
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Loại
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Trạng thái
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Người tạo
										</th>
										<th className=" py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Ngày tạo
										</th>
										{user?.userType == 'ADMIN' && <th className="py-4 px-4 font-medium text-black dark:text-white text-nowrap">
											Thao tác
										</th>}
									</tr>
								</thead>
								<tbody>
									{dataList?.length > 0 ? dataList.map((item: any, key: any) => (
										<tr key={key}>
											<td className="border-b border-[#eee] py-5 px-4 pl-9 dark:border-strokedark">
												<p className="font-medium text-black dark:text-white cursor-pointer"
												// onClick={() => updateData(item)}
												>
													{item.id}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className="text-black min-w-[120px] text-break font-medium dark:text-white text-nowrap">
													{item.name}
												</p>
											</td>
											<td className="border-b border-[#eee]  py-5 px-4 dark:border-strokedark">
												<div style={{ wordBreak: 'break-word' }} className="text-break"
													dangerouslySetInnerHTML={{ __html: item.content }}>
												</div>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												{formatMoney(item.data_value || 0)}
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												{item.user?.name}
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className={`dark:text-white ${item.status == "APPROVED" ? 'text-success' : 'text-red'}`}>
													{item.status == "PENDING" ? "Chờ duyệt" : "Đã duyệt"}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<p className={`dark:text-white ${item.status == "BONUS" ? 'text-sky' : 'text-warning'}`}>
													{item.type == "BONUS" ? "Thưởng" : "Kỷ luật"}
												</p>
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												{item.updated_by?.name}
											</td>
											<td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												{formatTime(item.created_at, 'DD/MM/yyyy')}
											</td>

											{user?.userType == "ADMIN" && <td className="border-b border-[#eee] py-5 px-4 dark:border-strokedark">
												<div className="flex items-center space-x-3.5">
													<button className="hover:text-primary"
														onClick={() => deleteData(item.id)}
													>
														<FaTrash />
													</button>
													<Link href={'/bonus/form?id=' + item.id} className="hover:text-primary"
													>
														<FaPencil />
													</Link>
												</div>
											</td>}
										</tr>
									)) :

										<tr>
											<td colSpan={8} className="text-center mt-2">
												<p className="mt-5">Không có dữ liệu</p>
											</td>
										</tr>
									}


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
