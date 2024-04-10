"use client";
import React, { useEffect, useState } from "react";
import Link from "next/link";
import Image from "next/image";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";

import DefaultLayout from "@/components/Layouts/DefaultLayout";

import { Product } from "@/types/product";
import { INIT_PAGING, WEB_VALUE } from "@/services/constant";
import { ORDER_SERVICE } from "@/services/api.service";
import { formatMoney, formatTime } from "@/services/helpers.service";
import { PagingPage } from "@/components/common/paging";
import Loader from "@/components/common/Loader";




const OrderList: React.FC = () => {

	const [dataList, setDataList] = useState([]);
	const [paging, setPaging] = useState(INIT_PAGING);
	const [loading, setLoading] = useState(false);



	useEffect(() => {
		getDataList({ ...paging })
	}, []);

	const getDataList = async (filters: any) => {
		setLoading(true);
		const response: any = await ORDER_SERVICE.getList(filters);
		setLoading(false);
		if (response?.status == 'success') {
			setDataList(response.data || []);
			setPaging(response.meta || INIT_PAGING);
		}
	}

	return (
		<DefaultLayout>
			<Breadcrumb pageName="Hóa đơn" />

			<div className="flex flex-col gap-10">
				<div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
					<div className="px-4 py-6 md:px-6 xl:px-7.5 md:flex md:justify-between w-full">
						<h4 className="text-xl font-semibold text-black dark:text-white">
							Danh sách
						</h4>
						<Link href={'/order/form'} className="inline-flex items-center justify-center rounded-md bg-primary px-10 py-2 text-center font-medium text-white hover:bg-opacity-90 lg:px-8 xl:px-10">Create</Link>
					</div>
					{loading && <Loader className={"bg-opacity-60 bg-white z-50 fixed top-0 left-0 w-full h-full"} />}
					<div className="grid grid-cols-6 border-t border-stroke px-4 py-4.5 dark:border-strokedark sm:grid-cols-8 md:px-6 2xl:px-7.5">
						<div className="col-span-2 flex items-center">
							<p className="font-medium">Code</p>
						</div>
						<div className="col-span-2 hidden items-center sm:flex">
							<p className="font-medium">Tổng giá</p>
						</div>
						<div className="col-span-2 flex items-center">
							<p className="font-medium">Trạng thái</p>
						</div>
						<div className="col-span-1 flex items-center">
							<p className="font-medium">Ngày tạo</p>
						</div>
						<div className="col-span-1 flex items-center">
							<p className="font-medium">Action</p>
						</div>
					</div>

					{dataList?.length > 0 && dataList.map((item: any, key) => {
						return (
							<div key={key}
								className="grid grid-cols-6 border-t 
							border-stroke px-4 py-4.5 dark:border-strokedark 
							sm:grid-cols-8 md:px-6 2xl:px-7.5"
							>

								<div className="col-span-2 flex items-center">
									<div className="flex flex-col gap-4 sm:flex-row sm:items-center">
										<p className="text-sm text-black dark:text-white text-center">
											{item.code}
										</p>
									</div>
								</div>
								<div className="col-span-2 hidden items-center sm:flex">
									<p className="text-sm text-black dark:text-white">
										{formatMoney(item.total_price)}
									</p>
								</div>
								<div className="col-span-2 flex items-center">
									<p className="text-sm text-black dark:text-white">
										{item.status > 1 ? 'Đã thanh toán' : 'Chưa thanh toán'}
									</p>
								</div>
								<div className="col-span-1 flex items-center">
									<p className="text-sm text-black dark:text-white">{formatTime(item.created_at, 'DD/MM/yyyy HH:mm')}</p>
								</div>
								<div className="col-span-1 flex items-center">
									<Link href={'/order/form?id=' + item.id} className="inline-flex items-center justify-center 
								rounded-md bg-cyan-500 px-5 py-2 text-center 
								font-medium text-white hover:bg-opacity-90">Edit</Link>
								</div>
							</div>
						)
					}

					)}
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
